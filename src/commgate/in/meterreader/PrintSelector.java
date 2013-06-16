package commgate.in.meterreader;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PrintSelector extends ListActivity {

	static public final int REQUEST_CONNECT_BT = 0x2300;
	static private final int REQUEST_ENABLE_BT = 0x1000;
	static private BluetoothAdapter mBluetoothAdapter = null;
	static private ArrayAdapter<String> mArrayAdapter = null;
	static private ArrayAdapter<BluetoothDevice> btDevices = null;

	// Unique UUID for this application, Basically the SPP Profile
	private static final UUID SPP_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	static private BluetoothSocket mbtSocket = null;
	private static final String TAG = "DEEPSTUFF";
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Change the title of the activity
		setTitle("Bluetooth Devices");

		// Get the List of paired and available devices
		try 
		{
			if (initDevicesList() != 0) {
				this.finish();
				return;
			}

		} 
		catch (Exception ex) {
			this.finish();
			return;
		}

		// Register the Broadcast receiver for handling new BT device discovery
		IntentFilter btIntentFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		registerReceiver(mBTReceiver, btIntentFilter);
	}

	
	
	
	public static BluetoothSocket getSocket() 
	{
		return mbtSocket;
	}
	
	
	
	

	private void flushData() 
	{
		try 
		{
			if (mbtSocket != null) 
			{
				mbtSocket.close();
				mbtSocket = null;
			}

			if (mBluetoothAdapter != null) 
			{
				mBluetoothAdapter.cancelDiscovery();
			}

			if (btDevices != null) 
			{
				btDevices.clear();
				btDevices = null;
			}

			if (mArrayAdapter != null) 
			{
				mArrayAdapter.clear();
				
				mArrayAdapter.notifyDataSetInvalidated();
				mArrayAdapter.notifyDataSetChanged();
				mArrayAdapter = null;
			}

			finalize();

		} 
		catch (Exception ex) 
		{
			
		} 
		catch (Throwable e) 
		{
			
		}

	}

	
	
	
	

	private int initDevicesList() {

		// Flush any Pending Data
		flushData();

		// Get the Bluetooth Adaptor of the device
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{
			
			Toast.makeText(getApplicationContext(),"Bluetooth not supported!!", Toast.LENGTH_LONG).show();
			return -1;
		}

		if (mBluetoothAdapter.isDiscovering()) 
		{
			mBluetoothAdapter.cancelDiscovery();
		}

		mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.listbackground);

		setListAdapter(mArrayAdapter);

		// get the list of devices already paired
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		
		try 
		{
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} 
		catch (Exception ex) 
		{
			return -2;
		}

		Toast.makeText(getApplicationContext(),	"Getting all available Bluetooth Devices", Toast.LENGTH_SHORT).show();

		return 0;

	} 
	
	
	
	

	@Override
	protected void onActivityResult(int reqCode, int resultCode, Intent intent) 
	{
		super.onActivityResult(reqCode, resultCode, intent);

		switch (reqCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == RESULT_OK) 
			{
				// Start getting the paired devices list
				Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
						.getBondedDevices();
				// If there are paired devices
				try {
					if (btDeviceList.size() > 0) {
						for (BluetoothDevice device : btDeviceList) 
						{
							if (btDeviceList.contains(device) == false) 
							{

								btDevices.add(device); 
								mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
								
								mArrayAdapter.notifyDataSetInvalidated();
								mArrayAdapter.notifyDataSetChanged();
							}
						}
					}
				} 
				catch (Exception ex) 
				{
					Log.d(TAG, "Error Getting list of devices: " + ex.toString());
				}
			}

			break;
		}

		// Also register for new devices which are discovered
		mBluetoothAdapter.startDiscovery();

	} // End onActivityResult
	
	
	
	

	private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() 
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				try {

					// No paired device found
					if (btDevices == null) {
						btDevices = new ArrayAdapter<BluetoothDevice>(
								getApplicationContext(), android.R.id.text1);
					}

					
					if (btDevices.getPosition(device) < 0) {
						btDevices.add(device);
						
						mArrayAdapter.add(device.getName() + "\n" + device.getAddress() + "\n" );	
						
						mArrayAdapter.notifyDataSetInvalidated();
						mArrayAdapter.notifyDataSetChanged();
					}
				} 
				catch (Exception ex) 
				{
					Log.d(TAG, "Error adding new device: " + ex.toString());
				}
			}
		}
	};
	
	
	
	

	@Override
	protected void onListItemClick(ListView l, View v, final int position, long id) 
	{
		super.onListItemClick(l, v, position, id);

		// Don't proceed if the bluetooth adapter is not valid
		if (mBluetoothAdapter == null) 
		{
			return;
		}

		// Cancel the dicovery if still going on
		if (mBluetoothAdapter.isDiscovering()) 
		{
			mBluetoothAdapter.cancelDiscovery();
		}

		// Try to connect with the selected device,
		Toast.makeText(getApplicationContext(), "Connecting to " + btDevices.getItem(position).getName() + "," 	
					+ btDevices.getItem(position).getAddress(),	Toast.LENGTH_SHORT).show();

		// made the thread different as the connecting proceedure might break
		// down the system
		Thread connectThread = new Thread(new Runnable() 
		{

			@Override
			public void run() 
			{
				try {
					try 
					{
						Method m = btDevices.getItem(position).getClass().getMethod("createInsecureRfcommSocket",
								new Class[] { int.class });
						try 
						{
							mbtSocket = (BluetoothSocket) m.invoke(btDevices.getItem(position), 1);
						} 
						catch (IllegalArgumentException e) 
						{
							Log.d(TAG, "Error invoking connection " + e.toString());
						} 
						catch (IllegalAccessException e) 
						{
							Log.d(TAG, "Error Accessing Connection " + e.toString());
						} 
						catch (InvocationTargetException e) 
						{
							Log.d(TAG, "Error invoking Target " + e.toString());
						}
						
					} 
					catch (SecurityException e) 
					{
						Log.d(TAG, "Security Exception " + e.toString());
					} catch (NoSuchMethodException e) 
					{
						Log.d(TAG, "Error: No such method: " + e.toString());
					}
					mBluetoothAdapter.cancelDiscovery();
					mbtSocket.connect();
				} 
				catch (IOException ex) {
					runOnUiThread(socketErrorRunnable);
					try 
					{
						mbtSocket.close();
					} 
					catch (IOException e) 
					{
						Log.d(TAG, "Error closing connection " + e.toString());
					}
					mbtSocket = null;
					return;
				} 
				finally {
					runOnUiThread(new Runnable() 
					{

						@Override
						public void run() 
						{
							finish();

						}
					});
				}
			}
		});

		connectThread.start();
	}

	private Runnable socketErrorRunnable = new Runnable() {

		@Override
		public void run() {
			Toast.makeText(getApplicationContext(),
					"Cannot establish connection", Toast.LENGTH_SHORT).show();
			mBluetoothAdapter.startDiscovery();

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Add the menu options
		menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case Menu.FIRST:
			initDevicesList();
			break;
		}

		return true;
	}
} // End of class definition

