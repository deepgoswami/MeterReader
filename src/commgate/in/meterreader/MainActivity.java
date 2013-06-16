package commgate.in.meterreader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;





public class MainActivity extends Activity {
	
	
	
	private BluetoothSocket theSocket = null;
	private static final String TAG = "DEEPSTUFF";
	byte fontStyleVal;
	OutputStream theOutputStream;
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				boolean isPasswordValid = validatePassword();
				if (isPasswordValid)
				{
					Intent intent = new Intent(getApplicationContext(), ChooseAction.class);
					startActivity(intent);
				}
				
			}

			private boolean validatePassword() 
			{
				
				return true;
			}

			
		}
		);
		
		
		//*** Donload Button CVlicked
/*		
Button btnDownload = (Button) findViewById(R.id.btnDownload);
		
		btnDownload.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FileParser fileParser = new FileParser(getApplicationContext());
				
			}

			

			
		}
		);*/
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	    	
		 	Intent intent = new Intent(getApplicationContext(), Preferences.class);
			startActivity(intent);
	    	
			return true;
	    	
	    		
	    	
	    }

}
