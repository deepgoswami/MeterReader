package commgate.in.meterreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Preferences extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		Button btnSave = (Button)findViewById(R.id.btnSave1);
		boolean isFileFound = false;
		
		
		try {
			FileInputStream fis = openFileInput("meterreader.txt");
			isFileFound = true;
			byte buffer[] = new byte[512];
			int ch = fis.read(buffer);
			String msg;
			
			if (ch != -1) {
				msg = new String(buffer, 0, ch);
				StringTokenizer st = new StringTokenizer(msg, ",\n\t ");
				String theString[] = new String[3];
				int i = 0;
				while (st.hasMoreTokens())
				{
					theString[i] = st.nextToken();
					i++;
				}
				
				
				TextView theTextView;
				theTextView = (TextView)findViewById (R.id.hostName);
				theTextView.setText(theString[0]);
				theTextView = (TextView)findViewById (R.id.editText1);
				theTextView.setText(theString[1]);
				theTextView = (TextView)findViewById (R.id.password);
				theTextView.setText(theString[2]);
				
				
			}
		}
		catch (FileNotFoundException fn)
		{
			isFileFound = false;
		}
		catch (IOException ie)
		{
			Toast.makeText(getApplicationContext(), "IO Error " + ie.toString(), Toast.LENGTH_SHORT).show();
		}
		
		
		
		btnSave.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TextView txtServer = (TextView) findViewById(R.id.hostName);
				TextView txtUsername = (TextView) findViewById(R.id.editText1);
				TextView txtPassword = (TextView) findViewById(R.id.password);
				String configLine = new String();
				configLine += txtServer.getText();
				configLine += ",";
				configLine += txtUsername.getText();
				configLine += ",";
				configLine += txtPassword.getText();
				try {
					FileOutputStream fos = openFileOutput("meterreader.txt", Context.MODE_PRIVATE);
					fos.write(configLine.getBytes());
					fos.close();
				}
				catch (FileNotFoundException fe)
				{
					Toast.makeText(getApplicationContext(), "File not Found " + fe.toString(), Toast.LENGTH_SHORT).show();
				}
				catch (IOException ie)
				{
					Toast.makeText(getApplicationContext(), "IO Error " + ie.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}

			
		}
		);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}

}
