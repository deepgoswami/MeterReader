package commgate.in.meterreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.StringTokenizer;

public class ChooseConsumerNumber extends Activity {
	String TAG = "DEEPGOSWAMI";
	int PASS_VALUE = 87;
	
	String subDivision;
	List<String> binder = new ArrayList<String>();
	List<String> sdoList = new ArrayList<String>();
	String subdivision;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_consumer_number);
		
		/*Spinner spnrSubdivsn = (Spinner) findViewById(R.id.spinner1);
		Spinner spnrSection = (Spinner) findViewById(R.id.spinner01);*/
		getValuesFromDb();
		
		TextView tv = (TextView) findViewById(R.id.subDivisionTxt);
		tv.setText(sdoList.get(0));
		Spinner spnrSubdivsn = (Spinner) findViewById(R.id.spinner1);
		
		
		Button btnNext = (Button) findViewById(R.id.btnNext);
		
		
		
		ArrayAdapter<String> binderArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, 
				binder);
		spnrSubdivsn.setAdapter(binderArrayAdapter);
		
	
		
		
		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MeterReading.class);
				TextView theTV = (TextView) findViewById(R.id.subDivisionTxt);
				String value = theTV.getText().toString();
				intent.putExtra("Subdivision", value);
				Spinner theSpinner = (Spinner) findViewById(R.id.spinner1);
				value = theSpinner.getSelectedItem().toString();
				intent.putExtra("Binder", value);
				theTV = (TextView) findViewById(R.id.accountNumtxt);
				value = theTV.getText().toString();
				String newString = removeLeadingZeros(value);
				intent.putExtra("AccountNumber", newString);
				startActivityForResult(intent, PASS_VALUE);
				
			}
			
			
			

			private String removeLeadingZeros(String value) 
			{
				String newString = value.replaceFirst("^0+(?!$)", "");
				
				return newString;
			}
		}
		);
		
	}

	private void getValuesFromDb() 
	{
		DbHelper dbHelper = new DbHelper(getApplicationContext());
		String[] columnList = {"BINDER"};
		
		
		SQLiteDatabase theDb = dbHelper.getReadableDatabase();
		Cursor theCursor = theDb.query(true, "METER_TABLE", columnList, null, null, null, null, null, null);
		
		//theCursor.moveToFirst();
		int i = 0;
		while (theCursor.moveToNext())
		{
			binder.add(theCursor.getString(0));
			Log.d(TAG, "binder[" +i +"] = " + binder.toArray()[i] );
			i++;
		}
		
		columnList[0] = "SDO_CD";
		theCursor = theDb.query(true, "METER_TABLE", columnList, null, null, null, null, null, null);
		
		//theCursor.moveToFirst();
		i = 0;
		while (theCursor.moveToNext())
		{
			sdoList.add(theCursor.getString(0));
			Log.d(TAG, "sdoList[" +i +"] = " + sdoList.toArray()[i]  );
			i++;
		}
		theCursor.close();
		theDb.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_consumer_number, menu);
		return true;
	}

}
