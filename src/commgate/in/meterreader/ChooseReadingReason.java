package commgate.in.meterreader;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChooseReadingReason extends Activity 
{
	private RadioGroup theRadioGroup;
	private RadioButton theReason;	
	private Button btnOK;
	
	private int reasonCode = -1;
	private int SHOW_BILL = 76;
	private int REENTER_RDNG;
	
	String subdivision;
	String binder;
	String accountNumber;
	String currReading;
	String pictureFileName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_reading_reason);
		
		Intent fromPrevious = getIntent();
		subdivision = fromPrevious.getStringExtra("Subdivision");
		binder = fromPrevious.getStringExtra("Binder");
		accountNumber = fromPrevious.getStringExtra("AccountNumber");
		currReading = fromPrevious.getStringExtra("currentreading");
		pictureFileName = fromPrevious.getStringExtra("PicturePath");
		
		theRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		btnOK = (Button) findViewById(R.id.btnOK);
		
		btnOK.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int selectedId = theRadioGroup.getCheckedRadioButtonId();
				theReason = (RadioButton) findViewById(selectedId);
				String reason = theReason.getText().toString();
				if (reason.equals("Meter Faulty"))
					reasonCode = 5;
				if (reason.equals("Round Complete"))
					reasonCode = 6;
				if (reason.equals("Meter Change"))
					reasonCode = 7;
				if (reason.equals("Re-enter Reading"))
					reasonCode = 1;
				
				if (reasonCode == 1)
				{
					Intent returnIntent = new Intent();
					returnIntent.putExtra("ReasonCode", reasonCode);
					finish();
				}
				else
				{
					Intent intent = new Intent(getApplicationContext(), ShowBill.class);
					intent.putExtra("Subdivision", subdivision);
					intent.putExtra("Binder", binder);
					intent.putExtra("AccountNumber", accountNumber);
					intent.putExtra("currentreading", currReading);
					intent.putExtra("PicturePath", pictureFileName);
					intent.putExtra("ReasonCode", reasonCode);
					startActivityForResult(intent, SHOW_BILL);
				}
					
			}
		}
		);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_reading_reason, menu);
		return true;
	}

}
