package commgate.in.meterreader;

import java.io.IOException;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class PrintScreen extends Activity 
{
	private Button mPrintButton;
	private Button mDisconnectButton; 
	private static BluetoothSocket mbtSocket;
	private static String TAG = "DEEPGOSWAMI";
	Intent theIntent;

	

	private static OutputStream mbtOutputStream;
	int mPrintType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_screen);
		
		
		mPrintButton = (Button)findViewById(R.id.printButton);
		
		mDisconnectButton = (Button)findViewById(R.id.disconnectButton);
		


		Log.d(TAG, "here");
		
		
		
		
		
		mDisconnectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(mbtSocket != null){
						mbtOutputStream.close();
						mbtSocket.close();
						mbtSocket = null;
					Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT).show();
					}
					
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		
		
		
		
		
		

		mPrintButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPrintType = 0;
				StartBluetoothConnection();
			}
		});

		
		
		
	}
	
	
	
	protected void StartBluetoothConnection() {
		if(mbtSocket == null){
			Intent BTIntent = new Intent(getApplicationContext(), BTWrapperActivity.class);
			this.startActivityForResult(BTIntent, BTWrapperActivity.REQUEST_CONNECT_BT);
		}
		else{
			//mbtSocket.connect();
			OutputStream tmpOut = null;
			try {
				tmpOut = mbtSocket.getOutputStream();
			} catch (IOException e) { 
				e.printStackTrace();
			}
			mbtOutputStream = tmpOut;
			senddatatodevice();

		}

	}
	
	
	
	
	private void senddatatodevice() {
		
		theIntent = getIntent();
		try 
		{
			mbtOutputStream = mbtSocket.getOutputStream();

			switch (mPrintType) {
			case 0:
				byte[] Command = { 0x1B, 0X45, 0X01};
				mbtOutputStream.write(Command);
				String sendingmessage = "\t\t\t\t\tSouthCo";
				byte[] send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				Command[0] = 0x1B;
				Command[1] = 0X21;
				Command[2] = 0X00;
				mbtOutputStream.write(Command);
				sendingmessage = "Electricity Bill";
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				
				Command[0] = 0x1B;
				Command[1] = 0X21;
				Command[2] = 0X00;
				sendingmessage = "BEHRAMPUR-II";
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				sendingmessage = "SUB. DIVISION NO-";
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				sendingmessage = "E.S.O.NO.1";
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				String tempString = theIntent.getStringExtra("NewAcNo");
				sendingmessage = "New A/C No: \t\t" + tempString;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("old_ac_no");
				sendingmessage = "Old A/C No: \t\t" + tempString;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "Elect Address:\t\t" ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				
				sendingmessage = "Bill Period:\t\t";
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "No of Months:\t\t" ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("Date");
				sendingmessage = "Dt:\t\t" ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				
				
				sendingmessage = "Name and Address";
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				Log.d(TAG, "Here2");
				Command[0] = 0x1B;
				Command[1] = 0X2D;
				Command[2] = 0X00;
				
				tempString = theIntent.getStringExtra("name");
				sendingmessage = tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("addr1");
				sendingmessage = tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("addr2");
				sendingmessage = tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("addr3");
				sendingmessage = tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				//-------------------------------------------------consumerStatus
				tempString = theIntent.getStringExtra("consumerStatus");
				sendingmessage = "Consumer Status:\t\t" + tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				
				tempString = theIntent.getStringExtra("securityAmt");
				sendingmessage = "Security Deposit:\t\t" + tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("connectionLoad");
				sendingmessage = "Load:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				

				tempString = theIntent.getStringExtra("trf_cd");
				Log.d(TAG, "trf_cd = " + tempString);
				if (tempString.equals("1000"))
					tempString = "DOM";
				else
					tempString = "COMM";
				sendingmessage = "Category:\t\t" + tempString ;
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				//------------------------------------
				tempString = theIntent.getStringExtra("mf");
				sendingmessage = "MF:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "Previous Reading";				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("prevRdng");
				sendingmessage = "Reading:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("prevRdngDate");
				sendingmessage = "Date:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("prevRdngStatus");
				sendingmessage = "Status:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("flrdg");
				sendingmessage = "Old Mtr Fnl Rdg:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				//----------------------------------------------
				tempString = theIntent.getStringExtra("ilrdg");
				sendingmessage = "Old Mtr Fnl Rdg:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "Pres Rdg:\t\t";				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("currRdng");
				sendingmessage = "Reading:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("currRdngDt");
				sendingmessage = "Date:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("prevRdngStatus");
				sendingmessage = "Status:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				//-------------------------------------------------------------
				tempString = theIntent.getStringExtra("meterNo");
				sendingmessage = "Mtr No:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("meterOwner");
				sendingmessage = "Mtr Owner:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("billBasis");
				sendingmessage = "Bill Basis:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("billUnits");
				sendingmessage = "Bill Units:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "Bill Slabs:\t\t";				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("slab1");
				sendingmessage = "Slab 1:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("slab2");
				sendingmessage = "Slab 2:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("slab3");
				sendingmessage = "Slab 3:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("slab4");
				sendingmessage = "Slab 4:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("energyCharge");
				sendingmessage = "Energy chg:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("fixChg");
				sendingmessage = "Fix/Dem Charge:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("meterRent");
				sendingmessage = "Meter Rent:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("elecDuty");
				sendingmessage = "Elec. Duty:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("dps");
				sendingmessage = "Elec. Duty:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("presentBillAmt");
				sendingmessage = "Elec. Duty:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("presentBillAmt");
				sendingmessage = "Pres. Bill Amt.:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "Arrears/Installment:\t\t";				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("adjAmount");
				sendingmessage = "Adj Amount:\t\t" 	+ tempString ;			
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("sundryAdj");
				sendingmessage = "Sundry Adj.:\t\t" + tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "After Due Date:\t\t";				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("afterDueAmt");
				sendingmessage = "##NET AMOUNT:\t\t"+ tempString ;				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				sendingmessage = "By Due Date:\t\t";				
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("rebate");
				sendingmessage = "Rebate:\t\t" + tempString ;			
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				tempString = theIntent.getStringExtra("byDueAmt");
				sendingmessage = "##NET AMOUNT:\t\t" + tempString ;			
				send = sendingmessage.getBytes();
				mbtOutputStream.write(send);
				mbtOutputStream.write(0x0D);
				
				//----------------------------------------------------------
				
				
				mbtOutputStream.write(0x0D);
				mbtOutputStream.write(0x0D);
				mbtOutputStream.write(0x0D);
				mbtOutputStream.flush();
				
				
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if(mbtSocket!= null){
				mbtOutputStream.close();
				mbtSocket.close();
				mbtSocket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {

		case BTWrapperActivity.REQUEST_CONNECT_BT:

			try {
				mbtSocket = BTWrapperActivity.getSocket();
				if(mbtSocket != null){

					senddatatodevice();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.print_screen, menu);
		return true;
	}

}
