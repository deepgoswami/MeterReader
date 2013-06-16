package commgate.in.meterreader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MeterReading extends Activity {
	private int TEST_INTENT = 42;
	String subdivision;
	String binder;
	String accountNumber;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meter_reading);
		
		Intent fromPrevious = getIntent();
		subdivision = fromPrevious.getStringExtra("Subdivision");
		binder = fromPrevious.getStringExtra("Binder");
		accountNumber = fromPrevious.getStringExtra("AccountNumber");
		
		
		//fill in the customer details
		/*TextView txtTemp = (TextView) findViewById(R.id.consumerNameText);
		txtTemp.setText("SMT GOLPA PALO");
		txtTemp = (TextView) findViewById(R.id.ConsumerNumText);
		txtTemp.setText("342101260379");
		txtTemp = (TextView) findViewById(R.id.address1Text);
		txtTemp.setText("Hari Hara Nagar");
		txtTemp = (TextView) findViewById(R.id.address2Text);
		txtTemp.setText("Aska");*/
		
		Button btnNext = (Button) findViewById(R.id.button3);
		
		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), ShowBill.class);
				TextView editTxt = (TextView) findViewById(R.id.editText1);
				intent.putExtra("currentreading", editTxt.getText().toString());
				intent.putExtra("Subdivision", subdivision);
				intent.putExtra("Binder", binder);
				intent.putExtra("AccountNumber", accountNumber);
				startActivityForResult(intent, TEST_INTENT);
				
			}
		}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meter_reading, menu);
		return true;
	}

}
