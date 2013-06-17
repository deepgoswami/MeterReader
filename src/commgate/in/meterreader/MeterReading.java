package commgate.in.meterreader;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MeterReading extends Activity {
	private int SHOW_BILL = 42;
	private int TAKE_PICTURE = 345;
	private int REASON_WHY = 4123;
	private String pictureFileName = null;
	
	private static boolean isPictureTaken = false;
	String subdivision;
	String binder;
	String accountNumber;
	private int iOldReading;
	
	private static final int DIALOG_RESOLVE_ISSUE = 421; 
	private int reasonWhy = 0;
	private String reasonCode;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meter_reading);
		
		Intent fromPrevious = getIntent();
		subdivision = fromPrevious.getStringExtra("Subdivision");
		binder = fromPrevious.getStringExtra("Binder");
		accountNumber = fromPrevious.getStringExtra("AccountNumber");
		
		String strOldReading = null;
		iOldReading = 0;
		isPictureTaken = false;
		String[] displayDetails = new String[5];
		displayDetails = getDetailsFromDB();
		
		
		//fill in the customer details
		TextView txtTemp = (TextView) findViewById(R.id.consumerNameText);
		txtTemp.setText(displayDetails[0]);
		txtTemp = (TextView) findViewById(R.id.ConsumerNumText);
		txtTemp.setText(displayDetails[1]);
		txtTemp = (TextView) findViewById(R.id.address1Text);
		txtTemp.setText(displayDetails[2]);
		txtTemp = (TextView) findViewById(R.id.address2Text);
		txtTemp.setText(displayDetails[3]);
		strOldReading = displayDetails[4];
		iOldReading = Integer.parseInt(strOldReading);
		
		Button btnNext = (Button) findViewById(R.id.button3);
		
		btnNext.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				if (!isPictureTaken)
				{
					Toast.makeText(getApplicationContext(), "You must take a picture of the meter", Toast.LENGTH_LONG).show();
					return;
				}
				
				
				TextView editTxt = (TextView) findViewById(R.id.editText1);
				
				int icurrRdng = Integer.parseInt(editTxt.getText().toString());
				
				if (icurrRdng <= iOldReading)
				{
					
					Intent reasonIntent = new Intent(getApplicationContext(),ChooseReadingReason.class);
					reasonIntent.putExtra("Subdivision", subdivision);
					reasonIntent.putExtra("Binder", binder);
					reasonIntent.putExtra("AccountNumber", accountNumber);
					reasonIntent.putExtra("currentreading", editTxt.getText().toString());
					reasonIntent.putExtra("AccountNumber", accountNumber);
					reasonIntent.putExtra("PicturePath", pictureFileName);
					startActivityForResult(reasonIntent, REASON_WHY);
				}
				
				//Log.d("DEEPGOSWAMI", "old reading = " + iOldReading + " new Reading = " + icurrRdng);
				else
				{
					Intent intent = new Intent(getApplicationContext(), ShowBill.class);
					intent.putExtra("currentreading", editTxt.getText().toString());
					intent.putExtra("Subdivision", subdivision);
					intent.putExtra("Binder", binder);
					if (isPictureTaken)
						intent.putExtra("PicturePath", pictureFileName);
					intent.putExtra("AccountNumber", accountNumber);
					intent.putExtra("ReasonCode", reasonCode);
					startActivityForResult(intent, SHOW_BILL);
				}
				
				
			}
		}
		);
		
		
		Button btnScan = (Button) findViewById(R.id.scanBtn);
		btnScan.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    		pictureFileName = "Picture_" + String.valueOf(System.currentTimeMillis() + ".jpg");
	    		
	    		Uri mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), pictureFileName));
	    		intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
	    		startActivityForResult(intent, TAKE_PICTURE);
				isPictureTaken = true;
			}
		}
		);
		
	}

	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == TAKE_PICTURE)
			if (resultCode == RESULT_OK)
				isPictureTaken = true;
		if (requestCode == REASON_WHY)
			if (resultCode == RESULT_OK)
			{
				reasonWhy = data.getIntExtra("ReasonCode", 0);
				isPictureTaken = false;
			}
				
			
	}
	
	
	
	private String[] getDetailsFromDB() 
	{
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		String[] columnList = {"NAME", "ADDR1", "ADDR2", "ADDR3", "OPNRDG"};
		String query = "BINDER =" + "\'" + binder +"\'" + "AND ACC_NO =" + "\'" + accountNumber + "\'";
		String[] displayDetails = new String[5];
		
		SQLiteDatabase theDb = dbHelper.getReadableDatabase();
		Cursor theCursor = theDb.query("METER_TABLE", columnList, query, null, null, null, null);
		
		if (theCursor.moveToFirst())
		{
			displayDetails[0] = theCursor.getString(0);
			displayDetails[1] = theCursor.getString(1);
			displayDetails[2] = theCursor.getString(2);
			displayDetails[3] = theCursor.getString(3);
			displayDetails[4] = theCursor.getString(4);
			theCursor.close();
			dbHelper.close();
			return displayDetails;
		}
		else
			Log.d("DEEPGOSWAMI", "MeterReading: getDetailsFromDB : cursor is empty");
		
		theCursor.close();
		dbHelper.close();
		return null;
	}
	
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meter_reading, menu);
		return true;
	}

}
