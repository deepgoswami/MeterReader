package commgate.in.meterreader;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import commgate.in.meterreader.DbHelper;

@SuppressLint("SimpleDateFormat")
public class ShowBill extends Activity 
{
	private static String TAG = "DEEPGOSWAMI";
	SQLiteDatabase theDb = null;
	DecimalFormat df = new DecimalFormat("#.##");
	OutputDataBean odb = null;
	private static BluetoothSocket mbtSocket;
	private static OutputStream mbtOutputStream;
	private static Activity theActivity;
	
	String consRef = null;
	int billMonth = 0;
	String sdoCd = null;
	String binder = null;
	String acc_no = null;
	
	String meterNo = null;
	String meterChangeDate = null;
	double ilrdg = 0.0;
	double flrdg = 0.0;
	String cstscd = null;
	
	String new_ac_no = null;
	String old_ac_no = null;
	String billDate = null;
	String name = null;
	String addr1 = null;
	
	String addr2 = null;
	String addr3 = null;
	int adjBd = 0;
	double securityAmt = 0.0;
	String consumerStatus = null;
	
	String category = null;
	int mf = 0;
	int prevRdng = 0;
	String prevRdngDate = null;
	String prevRdngStatus = null;
	
	String payDt1;
	
	Double connectionLoad = 0.0;
	String trf_cd;
	String csts_cd;
	Double meterRent = 0.0;
	Double currentSurcharge = 0.0;
	double energyCharge = 0.0;
	int currRdng = 0;
	
	int billUnits = 0;
	double fixChg = 0.0;
	double elecDuty = 0.0;
	double presentBillAmt = 0.0;
	double adjAmount = 0.0;
	
	double adjustable_bd = 0.0;
	double adjustable_ed = 0.0;
	double adjustable_dps = 0.0;
	double sundryBd = 0.0;
	double sundryEd = 0.0;
	
	double sundryOther = 0.0;
	double sundrySurchg = 0.0;
	double sundryAdj = 0.0;
	String strCurrRdng = null;
	double afterDueAmt = 0.0;

	double byDueAmt = 0.0;
	double rebate = 0.0;
	
	String rcptBookNo1 = null;
	double rcptNo1 = 0;
	String rcptDate = null;
	double rcptAmt1 = 0.0;
	
	String photoPath = null;
	int reasonCode;
	int newCurrReading = 0;
	int newPrevReading = 0;
	
	int[] levels;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		String AccountNum;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_bill);

		theActivity = this;
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		theDb = dbHelper.getReadableDatabase();
		
		Intent fromPrevious = getIntent();
		AccountNum = fromPrevious.getStringExtra("AccountNumber");
		String thebinder = fromPrevious.getStringExtra("Binder");
		photoPath = fromPrevious.getStringExtra("PicturePath");
		reasonCode = fromPrevious.getIntExtra("ReasonCode", -1);
		Log.d(TAG, "Photo Path = " + photoPath);
		
		
		
		
		
		String query = "BINDER =" + "\'" + thebinder +"\'" + "AND ACC_NO =" + "\'" + AccountNum + "\'";;
		
		
		String temp = null;
		
		// run a db query with Consumer Reference as key
		Cursor theCursor = theDb.query("METER_TABLE", null, query, null, null, null, null);
		
		if (theCursor.moveToFirst())
		{
			
			consRef = theCursor.getString(0);
			billMonth = theCursor.getInt(1);
			sdoCd = theCursor.getString(2);
			binder = theCursor.getString(3);
			acc_no = theCursor.getString(4);
			
			old_ac_no = theCursor.getString(5);
			name = theCursor.getString(6);
			addr1 = theCursor.getString(7);
			addr2 = theCursor.getString(8);
			addr3 = theCursor.getString(9);
			
			temp = theCursor.getString(10);
			connectionLoad = Double.valueOf(temp);
			
			trf_cd = theCursor.getString(11);
			csts_cd = theCursor.getString(12);
			meterNo = theCursor.getString(13);
			meterRent = theCursor.getDouble(14);
			
			temp = theCursor.getString(15);
			
			try {
				mf = Integer.parseInt(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of mf was: " + temp);
				mf = 0;
			}
			temp = theCursor.getString(16);
			try {
				prevRdng = Integer.parseInt(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of prevRdng was: " + temp);
			}
			prevRdngDate = theCursor.getString(17);
			prevRdngStatus = theCursor.getString(18);
			
			temp = theCursor.getString(19);
			try {
				ilrdg = Integer.parseInt(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of ilrdg was: " + temp);
				ilrdg = 0;
			}
			
			temp = theCursor.getString(20);
			try {
				flrdg = Integer.parseInt(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of flrdg was: " + temp);
				flrdg = 0;
			}
			
			
			temp = theCursor.getString(21);
			
			try {
				currentSurcharge = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of currentSurcharge was: " + temp);
				currentSurcharge = 0.0;
			}
			
			temp = theCursor.getString(22);
			try {
				adjustable_bd = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of adjustable_bd was: " + temp);
				adjustable_bd = 0.0;
			}
			
			temp = theCursor.getString(23);
			try {
			adjustable_ed = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of adjustable_ed was: " + temp);
				adjustable_ed = 0.0;
			}
			
			temp = theCursor.getString(24);
			try {
				adjustable_dps = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of adjustable_dps was: " + temp);
				adjustable_dps = 0.0;
			}
			
			temp = theCursor.getString(25);
			try {
				securityAmt = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, ne.toString());
				Log.d(TAG,"String value of securityAmt was: " + temp);
				securityAmt = 0.0;
			}
			payDt1 = theCursor.getString(26);
			rcptBookNo1 = theCursor.getString(27);
			
			temp = theCursor.getString(28);
			if (temp != null)
			{
				try {
					rcptNo1 = Double.valueOf(temp);
				}
				catch (NumberFormatException ne)
				{
					Log.d(TAG, ne.toString());
					Log.d(TAG,"String value of rcptNo1 was: " + temp);
					rcptNo1 = 0.0;
				}
			}
			
			temp = theCursor.getString(29);
			try {
				rcptAmt1 = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, "rcptAmt1 " + ne.toString());
				
			}
			
			
			
		}
		else
			Log.d(TAG, "EMPTY CURSOR");
		
		
		fromPrevious = getIntent();
		strCurrRdng = fromPrevious.getStringExtra("currentreading");
		
		
		
		TextView theTextView = (TextView) findViewById(R.id.divisionTxt);
		theTextView.setText("BEHRAMPUR-II");
		theTextView = (TextView) findViewById(R.id.subDivTxt);
		theTextView.setText("Bijipur");
		theTextView = (TextView) findViewById(R.id.sectionTxt);
		theTextView.setText("Bijipur");
		
		theTextView = (TextView) findViewById(R.id.newACTxt);
		if (sdoCd.length() == 3)
			sdoCd = "0" + sdoCd;
		if (sdoCd.length() == 2)
			sdoCd = "00" + sdoCd;
		if (binder.length() == 3)
			binder = "0" + binder;
		if (binder.length() == 2)
			binder = "00" + binder;
		if (acc_no.length() == 1)
			acc_no = "000" + acc_no;
		if (acc_no.length() == 3)
			acc_no = "0" + acc_no;
		if (acc_no.length() == 2)
			acc_no = "00" + acc_no;
		if (acc_no.length() == 1)
			acc_no = "000" + acc_no;
		theTextView.setText(sdoCd + binder + acc_no );
		
		
		theTextView = (TextView) findViewById(R.id.oldAcNumTxt);
		Log.d(TAG, "Old AC Num " + old_ac_no);
		theTextView.setText(old_ac_no);
		
		theTextView = (TextView) findViewById(R.id.billPeriodTxt);
		theTextView.setText(String.valueOf(billMonth));
		
		theTextView = (TextView) findViewById(R.id.Name);
		theTextView.setText(name);
		
		theTextView = (TextView) findViewById(R.id.Addr1);
		theTextView.setText(addr1);
		theTextView = (TextView) findViewById(R.id.addr2);
		
		theTextView.setText(addr2);
		theTextView = (TextView) findViewById(R.id.addr3);
		theTextView.setText(addr3);
		theTextView = (TextView) findViewById(R.id.securitydepositTxt);
		theTextView.setText(String.valueOf(securityAmt));
		theTextView = (TextView) findViewById(R.id.consumerStatusTxt);
		theTextView.setText(consumerStatus);
		theTextView = (TextView) findViewById(R.id.category);
		theTextView.setText(category);
		theTextView = (TextView) findViewById(R.id.loadText);
		theTextView.setText(connectionLoad.toString());
		theTextView = (TextView) findViewById(R.id.mf);
		
		theTextView.setText(String.valueOf(mf));

		theTextView = (TextView) findViewById(R.id.oldReadingText);
		theTextView.setText(String.valueOf(prevRdng));
		theTextView = (TextView) findViewById(R.id.oldReadingDate);
		theTextView.setText(String.valueOf(prevRdngDate));
		theTextView = (TextView) findViewById(R.id.oldReadingStatus);
		theTextView.setText(String.valueOf(prevRdngStatus));
		
		theTextView = (TextView) findViewById(R.id.presReadingText);
		theTextView.setText(strCurrRdng);
		
		currRdng = Integer.parseInt(strCurrRdng);
		
		
		CalculateTariff ct = new CalculateTariff(prevRdng,
				currRdng, trf_cd, reasonCode, flrdg, ilrdg,
				getApplicationContext());
		
		energyCharge = ct.calculateEnergyCharge();
		
		theTextView = (TextView) findViewById(R.id.energyChgText);
		theTextView.setText(String.valueOf(df.format(energyCharge)));
		
		
		billUnits = ct.unitsConsumed();
		
		theTextView = (TextView) findViewById(R.id.billUnitsTxt);
		theTextView.setText(String.valueOf(billUnits));
		
		levels = new int[4];
		levels = ct.getLevelUnits();
		theTextView = (TextView) findViewById(R.id.slab1Txt);
		theTextView.setText(String.valueOf(levels[0]));
		
		theTextView = (TextView) findViewById(R.id.slab2Txt);
		theTextView.setText(String.valueOf(levels[1]));
		
		theTextView = (TextView) findViewById(R.id.slab3Txt);
		theTextView.setText(String.valueOf(levels[2]));
		
		theTextView = (TextView) findViewById(R.id.slab4Txt);
		theTextView.setText(String.valueOf(levels[3]));
		
		theTextView = (TextView) findViewById(R.id.meterRentTxt);
		theTextView.setText(String.valueOf(df.format(meterRent)));
		fixChg = ct.getFixedCharge(connectionLoad);
		theTextView = (TextView) findViewById(R.id.fixDemChgText);
		theTextView.setText(String.valueOf(df.format(fixChg)));

		elecDuty = ct.getElecDuty((double) energyCharge);
		theTextView = (TextView) findViewById(R.id.electDutyTxt);
		theTextView.setText(String.valueOf(df.format(elecDuty)));
		theTextView = (TextView) findViewById(R.id.dpsText);
		theTextView.setText(String.valueOf(df.format(currentSurcharge)));
		
		presentBillAmt = energyCharge + fixChg + meterRent + elecDuty
				+ currentSurcharge;
		theTextView = (TextView) findViewById(R.id.presBillText);
		theTextView.setText(String.valueOf(df.format(presentBillAmt)));
		adjAmount = adjustable_ed + adjustable_bd + adjustable_dps;
		theTextView = (TextView) findViewById(R.id.adjAmountText);
		theTextView.setText(String.valueOf(df.format(adjAmount)));

		sundryAdj = sundryBd + sundryEd + sundryOther + sundrySurchg;
		theTextView = (TextView) findViewById(R.id.sundryAdjText);
		theTextView.setText(String.valueOf(df.format(sundryAdj)));

		afterDueAmt = Math.ceil(presentBillAmt);
		theTextView = (TextView) findViewById(R.id.afterDueAmountText);
		theTextView.setText(String.valueOf(df.format(afterDueAmt)));
		
		rebate = ct.getRebate(billUnits);
		byDueAmt = Math.ceil(presentBillAmt - rebate);
		theTextView = (TextView) findViewById(R.id.rebateText);
		theTextView.setText(String.valueOf(df.format(rebate)));
		theTextView = (TextView) findViewById(R.id.byDueAmountText);
		theTextView.setText(String.valueOf(df.format(byDueAmt)));
		theTextView = (TextView) findViewById(R.id.bkNoText);;
		theTextView.setText(rcptBookNo1);
		theTextView = (TextView) findViewById(R.id.rcptText);;
		theTextView.setText(String.valueOf(rcptNo1));
		theTextView = (TextView) findViewById(R.id.rcptDateText);;
		theTextView.setText(rcptDate);
		theTextView = (TextView) findViewById(R.id.rcptAmountText);
		theTextView.setText(String.valueOf(df.format(rcptAmt1)));
		
		theCursor.close();
		
		// handle the save and print button click
		Button btnSave = (Button) findViewById(R.id.btnBillSave);
		btnSave.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view) 
			{
				odb = new OutputDataBean(getApplicationContext());
				Log.d(TAG, "OnClick");
				odb.setCONS_REF(consRef);
				Log.d(TAG, "Consumer Ref = " + consRef);
				odb.setSDO_CD(sdoCd);
				odb.setBINDER(binder);
				odb.setACC_NO(acc_no);
				odb.setMETERNO(meterNo);
				
				odb.setMTRINSTL_DATE(null);
				odb.setMETERTYPE(null);
				odb.setMF(String.valueOf(mf));
				odb.setINITIAL_RED(String.valueOf(ilrdg));
				odb.setFINAL_RED_OLDMETER(String.valueOf(flrdg));
				
				odb.setOLDMETER_STAT(null);
				odb.setCSTS_CD(cstscd);
				odb.setCUR_METER_STAT("null");
				odb.setCURRRDG(String.valueOf(currRdng));
				odb.setCUR_RED_DT(getDate());
				
				odb.setBILLING_DEMAND(null);
				odb.setNEW_TRF_CD(null);
				odb.setBILL_UNITS(String.valueOf(billUnits));
				odb.setENGCHG(String.valueOf(energyCharge));
				odb.setFIXCHG(String.valueOf(df.format(fixChg)));
				
				odb.setMETERRENT(String.valueOf(df.format(meterRent)));
				odb.setED(String.valueOf(df.format(elecDuty)));
				odb.setNEWBD(String.valueOf(df.format(energyCharge + fixChg + adjustable_bd)));
				odb.setNEWED(String.valueOf(df.format(elecDuty + adjustable_ed)));
				odb.setNEWDPS(String.valueOf(df.format(currentSurcharge + adjustable_dps)));
				
				odb.setNEWOTH(null);
				odb.setREFBLUNITS(null);
				odb.setREFBLBD(null);
				odb.setREFBLED(null);
				odb.setREFBLDPS(null);
				
				odb.setREFDEDUNITS(null);
				odb.setREFDEDBD(null);
				odb.setREFDEDED(null);
				odb.setREFDEDDPS(null);
				odb.setREB_OFF(String.valueOf(df.format(rebate)));
				
				odb.setNETBEFDUEDT(String.valueOf(df.format(byDueAmt)));
				odb.setNETAFTDUEDT(String.valueOf(df.format(afterDueAmt)));
				odb.setBILLBASIS("A");
				odb.setNOOFMONTHS(null);
				odb.setREB_DT(null);
				
				odb.setDUE_DATE(null);
				odb.setISSUE_DT(getDate());
				odb.setBILLPERIOD(null);
				odb.setBILLSERIALNO(null);
				odb.setOLDCSTS_CD(null);
				
				odb.setBILL_MTH(getDate());
				odb.setREMARKS(null);
				odb.setMACHINE_SRL_NO(null);
				odb.setMTR_READER_ID(null);
				odb.setMTR_READER_NAME(null);
				
				odb.setREB_OYT(null);
				odb.setREB_RTSWHT(null);
				odb.setDOM_SPLREB(null);
				odb.setENGCHG_OLDTRF(null);
				odb.setFIXCHG_OLDTRF(null);
				
				odb.setED_OLDTRF(null);
				odb.setNEWBD_OLDTRF(null);
				odb.setNEWED_OLDTRF(null);
				odb.setNEWDPS_OLDTRF(null);
				odb.setNEWOTH_OLDTRF(null);
				
				odb.setREFBLBD_OLDTRF(null);
				odb.setREFBLED_OLDTRF(null);
				odb.setREFBLDPS_OLDTRF(null);
				odb.setREFDEDBD_OLDTRF(null);
				odb.setREFDEDED_OLDTRF(null);
				
				odb.setREFDEDDPS_OLDTRF(null);
				odb.setREB_OFF_OLDTRF(null);
				odb.setNETBEFDUEDT_OLDTRF(null);
				odb.setNETAFTDUEDT_OLDTRF(null);
				odb.setREB_HOSTEL(null);
				
				odb.setMAX_DEMD(null);
				odb.setRECONN_CHARGE(null);
				
				odb.setPathToPhoto(photoPath);
				
				
				odb.setBILL_MTH(String.valueOf(billMonth));
				
				
				odb.writeToDB();
				
				
				Intent intent = new Intent(getApplicationContext(), PrintScreen.class);
				
				intent.putExtra("NewAcNo", sdoCd + binder + acc_no);
				intent.putExtra("ConsRef", consRef);
				intent.putExtra("BillPeriod", "");
				intent.putExtra("Date", getDate());
				intent.putExtra("old_ac_no", old_ac_no);
				
				intent.putExtra("name", name);
				intent.putExtra("addr1", addr1);
				intent.putExtra("addr1", addr1);
				intent.putExtra("addr2", addr2);
				intent.putExtra("addr3", addr3);
				
				intent.putExtra("consumerStatus", prevRdngStatus);
				intent.putExtra("securityAmt", String.valueOf(securityAmt));
				intent.putExtra("category",category );
				intent.putExtra("trf_cd", trf_cd);
				intent.putExtra("connectionLoad", String.valueOf(connectionLoad));
				
				intent.putExtra("mf", String.valueOf(mf));
				intent.putExtra("prevRdng", String.valueOf(prevRdng));
				intent.putExtra("prevRdngDate", prevRdngDate);
				intent.putExtra("prevRdngStatus", prevRdngStatus);
				intent.putExtra("flrdg", String.valueOf(flrdg));
				
				intent.putExtra("ilrdg", String.valueOf(ilrdg));
				intent.putExtra("currRdng", String.valueOf(currRdng));
				intent.putExtra("currRdngDt", getDate());
				intent.putExtra("meterNo", meterNo);
				String meterOwner = " ";
				if (meterRent == 0.0)
					meterOwner = "Consumer";
				intent.putExtra("meterOwner", meterOwner);
		
				intent.putExtra("billBasis", "ACTUAL");
				intent.putExtra("billUnits", String.valueOf(df.format(billUnits)));
				intent.putExtra("slab1", String.valueOf(levels[0]));
				intent.putExtra("slab2", String.valueOf(levels[1]));
				intent.putExtra("slab3", String.valueOf(levels[2]));
				intent.putExtra("slab4", String.valueOf(levels[3]));
				
				intent.putExtra("energyCharge", String.valueOf(df.format(energyCharge)));
				intent.putExtra("fixChg", String.valueOf(df.format(fixChg)));
				intent.putExtra("meterRent", String.valueOf(df.format(meterRent)));
				intent.putExtra("elecDuty", String.valueOf(df.format(elecDuty)));
				intent.putExtra("dps", " ");
				
				intent.putExtra("presentBillAmt", String.valueOf(df.format(presentBillAmt)));
				intent.putExtra("adjAmount", String.valueOf(df.format(adjAmount)));
				intent.putExtra("sundryAdj", String.valueOf(df.format(sundryAdj)));
				intent.putExtra("afterDueAmt", String.valueOf(df.format(afterDueAmt)));
				intent.putExtra("rebate", String.valueOf(df.format(rebate)));
				intent.putExtra("byDueAmt", String.valueOf(df.format(byDueAmt)));
				
				startActivity(intent);
				
				
				
				
				/*Intent intent = new Intent(getApplicationContext(), ChooseConsumerNumber.class);
				startActivity(intent);*/
			}
			
		}
		
		);
	}
		
		

	
	protected void startBluetoothConnection() 
	{
		Log.d(TAG, "In startBluetoothConnection()");
		if(mbtSocket == null)
		{
			Intent BTIntent = new Intent(getApplicationContext(), BTWrapperActivity.class);
			Log.d(TAG, "before  startActivityForResult");
			startActivityForResult(BTIntent, BTWrapperActivity.REQUEST_CONNECT_BT);
		}
		else
		{
			
			OutputStream tmpOut = null;
			try {
				tmpOut = mbtSocket.getOutputStream();
			} 
			catch (IOException e) 
			{ 
				e.printStackTrace();
			}
			mbtOutputStream = tmpOut;
		}

	}
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "in onActivityResult");
		switch(requestCode) {

		case BTWrapperActivity.REQUEST_CONNECT_BT:

			try 
			{
				mbtSocket = BTWrapperActivity.getSocket();
				PrintData pd = new PrintData(getApplicationContext(), odb, mbtOutputStream, theActivity);
				pd.startPrinting(mbtOutputStream);
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			break;
		}

	}
	
	
	

	private int getCurrentReading() 
	{
		currRdng = Integer.parseInt(strCurrRdng);
		
		if ((reasonCode == 5)) // Meter Faulty
		{
			Log.d(TAG, "Meter Faulty");
			return currRdng;
		}
		
		if (reasonCode == 6) //Round Complete
		{
			Log.d(TAG, "Round Complete");
			return currRdng;
		}
		if (reasonCode == 7) //Meter Change
		{
			Log.d(TAG, "Meter Change");
			return (int) (flrdg + currRdng);
		}
		return currRdng;
	}

	
	private int getPreviousReading() 
	{
		
		
		if ((reasonCode == 5)) // Meter Faulty
		{
			Log.d(TAG, "Meter Faulty");
			return prevRdng;
		}
		
		if (reasonCode == 6) //Round Complete 
		{
			Log.d(TAG, "Round Complete");
			return prevRdng;
		}
		if (reasonCode == 7) //Meter Change
		{
			Log.d(TAG, "Meter Change");
			return (int) (prevRdng + ilrdg);
		}
		return prevRdng;
	}




	private String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = new Date();
		return (df.format(date));
	}
	
	
	
	
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_bill, menu);
		return true;
	}

}
