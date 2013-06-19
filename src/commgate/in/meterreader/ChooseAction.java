package commgate.in.meterreader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseAction extends Activity {

	String[] binderList = new String[100];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_action);
		
		Button btnMeterReader = (Button) findViewById(R.id.btnReadMeter);
		
		btnMeterReader.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), ChooseConsumerNumber.class);
				startActivity(intent);
				
			}
		}
		);
		
		
		Button btnDownLoad = (Button) findViewById(R.id.btnDownload);

		
		btnDownLoad.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d("DEEPGOSWAMI", "before calling FileDownload");
				/*Intent intent = new Intent(getApplicationContext(), FileDownload.class);
				startActivity(intent);*/
				FileParser fp = new FileParser(getApplicationContext());
				fp.readFile(binderList);
				
			}
		}
		);
		
		
		Button btnUpload = (Button) findViewById(R.id.btnUpload);

		
		btnUpload.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				FileSaver fileSaver = new FileSaver(getApplicationContext());
				fileSaver.saveFile();
			}
		}
		);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_action, menu);
		return true;
	}

}
