package commgate.in.meterreader;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
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

public class ShowBill extends Activity 
{
	private static String TAG = "DEEPGOSWAMI";
	SQLiteDatabase theDb = null;
	DecimalFormat df = new DecimalFormat("#.##");
	
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
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		String AccountNum;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_bill);

		DbHelper dbHelper = new DbHelper(getApplicationContext());
		theDb = dbHelper.getReadableDatabase();
		
		Intent fromPrevious = getIntent();
		AccountNum = fromPrevious.getStringExtra("AccountNumber");
		Log.d(TAG, "AccountNumber = " + AccountNum);
		
		String query = "ACC_NO =" + "\'" + AccountNum + "\'";
		Log.d(TAG, "query is: " + query);
		//String query = "CONS_REF = \'2112016754\'";
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
				Log.d(TAG,"String value was: " + temp);
			}
			temp = theCursor.getString(16);
			prevRdng = Integer.parseInt(temp);
			prevRdngDate = theCursor.getString(17);
			prevRdngStatus = theCursor.getString(18);
			temp = theCursor.getString(19);
			ilrdg = Integer.parseInt(temp);
			
			temp = theCursor.getString(20);
			flrdg = Integer.parseInt(temp);
			temp = theCursor.getString(21);
			currentSurcharge = Double.valueOf(temp);
			temp = theCursor.getString(22);
			adjustable_bd = Double.valueOf(temp);
			temp = theCursor.getString(23);
			adjustable_ed = Double.valueOf(temp);
			temp = theCursor.getString(24);
			adjustable_dps = Double.valueOf(temp);
			
			temp = theCursor.getString(25);
			securityAmt = Double.valueOf(temp);
			
			payDt1 = theCursor.getString(26);
			rcptBookNo1 = theCursor.getString(27);
			
			temp = theCursor.getString(28);
			try {
				rcptNo1 = Double.valueOf(temp);
			}
			catch (NumberFormatException ne)
			{
				Log.d(TAG, "rcptNo1 " + ne.toString());
				
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
		
		currRdng = Integer.parseInt(strCurrRdng);
		
		TextView theTextView = (TextView) findViewById(R.id.divisionTxt);
		theTextView.setText("BEHRAMPUR-II");
		theTextView = (TextView) findViewById(R.id.subDivTxt);
		theTextView.setText("Bijipur");
		theTextView = (TextView) findViewById(R.id.sectionTxt);
		theTextView.setText("Bijipur");
		
		theTextView = (TextView) findViewById(R.id.newACTxt);
		theTextView.setText(new_ac_no);
		
		theTextView = (TextView) findViewById(R.id.oldAcNumTxt);
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
				currRdng, trf_cd,
				getApplicationContext());
		
		energyCharge = ct.calculateEnergyCharge();
		
		theTextView = (TextView) findViewById(R.id.energyChgText);
		theTextView.setText(String.valueOf(df.format(energyCharge)));
		
		
		billUnits = currRdng - prevRdng;
		
		theTextView = (TextView) findViewById(R.id.billUnitsTxt);
		theTextView.setText(String.valueOf(billUnits));
		
		int[] levels = new int[4];
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
		
		
		// handle the save and print button click
		Button btnSave = (Button) findViewById(R.id.btnBillSave);
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
