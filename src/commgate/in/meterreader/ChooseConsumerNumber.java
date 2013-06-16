package commgate.in.meterreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String SDO_CD = null;
		String BINDER = null;
		String[] temp = new String[2];
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_consumer_number);
		
		/*Spinner spnrSubdivsn = (Spinner) findViewById(R.id.spinner1);
		Spinner spnrSection = (Spinner) findViewById(R.id.spinner01);*/
		temp = getValuesFromFile();
		
		TextView tv = (TextView) findViewById(R.id.subDivisionTxt);
		tv.setText(temp[0]);
		tv = (TextView) findViewById(R.id.binderTxt);
		tv.setText(temp[1]);
		
		Button btnNext = (Button) findViewById(R.id.btnNext);
		
		/*List<String> subDvsnList = new ArrayList<String>();
		subDvsnList.add("3414");
		subDvsnList.add("3415");
		ArrayAdapter<String> subDvsnArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subDvsnList);
		spnrSubdivsn.setAdapter(subDvsnArrayAdapter);
		
		
		List<String> sectionList = new ArrayList<String>();
		sectionList.add("0126");
		sectionList.add("0127");
		
		ArrayAdapter<String> sectionArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sectionList);
		spnrSection.setAdapter(sectionArrayAdapter);*/
		
		
		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MeterReading.class);
				TextView theTV = (TextView) findViewById(R.id.subDivisionTxt);
				String value = theTV.getText().toString();
				intent.putExtra("Subdivision", value);
				theTV = (TextView) findViewById(R.id.binderTxt);
				value = theTV.getText().toString();
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

	private String[] getValuesFromFile() 
	{
		String temp;
		String[] temp2 = new String[20]; 
		String[] theString = new String[2];
		
		try {
			byte[] buff = new byte[100];
			FileInputStream fis = openFileInput("meterReader.cfg");
			fis.read(buff);
			temp = new String(buff);
			
			StringTokenizer st = new StringTokenizer(temp, ",\n");
			int i = 0;
			
			while (st.hasMoreElements())
			{
				temp2[i] = st.nextToken();
				
				i++;
			}
			
			theString[0] = temp2[0];
			theString[1] = temp2[1];
			
		}
		catch (IOException e)
		{
			Log.d("DEEPGOSWAMI", "Error Reading Internal File in CohhoseConsumer: " + e.toString());
			return null;
		}
		return theString;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_consumer_number, menu);
		return true;
	}

}
