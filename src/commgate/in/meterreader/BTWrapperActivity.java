package commgate.in.meterreader;

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

// =============== SOM, This class is meant for wrapping the Bluetooth connecting interface provided by android devices.......
// =============== Basically is a state machine, and the communications will happen in a separate thread so as not to obstruct the main UI

public class BTWrapperActivity extends ListActivity {

	static public final int REQUEST_CONNECT_BT = 0x2300;

	static private final int REQUEST_ENABLE_BT = 0x1000;

	static private BluetoothAdapter mBluetoothAdapter = null;

	static private ArrayAdapter<String> mArrayAdapter = null;

	// static private Set<BluetoothDevice> btDevices = null;
	static private ArrayAdapter<BluetoothDevice> btDevices = null;// BluetoothDevice[]
																	// btDevices
																	// = null;

	// Unique UUID for this application, Basically the SPP Profile
	private static final UUID SPP_UUID =
	// UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	static private BluetoothSocket mbtSocket = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Change the title of the activity
		setTitle("Bluetooth Devices");

		// Get the List of paired and available devices
		try {
			if (initDevicesList() != 0) {
				this.finish();
				return;
			}

		} catch (Exception ex) {
			this.finish();
			return;
		}

		// Register the Broadcast receiver for handling new BT device discovery
		IntentFilter btIntentFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		registerReceiver(mBTReceiver, btIntentFilter);
	}

	public static BluetoothSocket getSocket() {
		return mbtSocket;
	}

	private void flushData() {
		try {
			if (mbtSocket != null) {
				mbtSocket.close();
				mbtSocket = null;
			}

			if (mBluetoothAdapter != null) {
				mBluetoothAdapter.cancelDiscovery();
			}

			if (btDevices != null) {
				btDevices.clear();
				btDevices = null;
			}

			if (mArrayAdapter != null) {
				mArrayAdapter.clear();
				mArrayAdapter.notifyDataSetChanged();
				mArrayAdapter.notifyDataSetInvalidated();
				mArrayAdapter = null;
			}

			finalize();

		} catch (Exception ex) {
		} catch (Throwable e) {
		}

	}

	// Som ==== This method will Connect to our SPP Bluetooth Device after
	// discovering and pairing if required
	// Do not forget to add the permission for Bluetooth to use this method
	// Also this method is very tightly coupled with the above method, for
	// getting the status of bt connection

	private int initDevicesList() {

		// Flush any Pending Data
		flushData();

		// Get the Bluetooth Adaptor of the device
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			// Weird Condition ==== Implies that the device does not have a
			// bluetooth module ?????
			Toast.makeText(getApplicationContext(),
					"Bluetooth not supported!!", Toast.LENGTH_LONG).show();
			return -1;
		}

		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}

		mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.black_text);
		
		setListAdapter(mArrayAdapter);

		// get the list of devices already paired
		Intent enableBtIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_ENABLE);
		try {
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} catch (Exception ex) {
			// ex.getStackTrace();
			return -2;
		}

		Toast.makeText(getApplicationContext(),
				"Getting all available Bluetooth Devices", Toast.LENGTH_SHORT)
				.show();

		return 0;

	} // End getDeviceList

	@Override
	protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
		super.onActivityResult(reqCode, resultCode, intent);

		switch (reqCode) {
		case REQUEST_ENABLE_BT:

			if (resultCode == RESULT_OK) {
				// Start getting the paired devices list
				Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
						.getBondedDevices();
				// If there are paired devices
				try {
					if (btDeviceList.size() > 0) {

						// Loop through paired devices
						for (BluetoothDevice device : btDeviceList) {
							if (btDeviceList.contains(device) == false) {

								btDevices.add(device); // Add the device to the
														// device list

								// Add the name and address to an array adapter
								// to show in a ListView
								mArrayAdapter.add(device.getName() + "\n"
										+ device.getAddress() /*
															 * + "\n" +
															 * "Status : Paired"
															 */);
								// mArrayAdapter.notifyDataSetChanged();
								mArrayAdapter.notifyDataSetInvalidated();
							}
						}
					}
				} catch (Exception ex) {
				}
			}

			break;
		}

		// Also register for new devices which are discovered
		mBluetoothAdapter.startDiscovery();

	} // End onActivityResult

	private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				try {

					// No paired device found
					if (btDevices == null) {
						btDevices = new ArrayAdapter<BluetoothDevice>(
								getApplicationContext(), android.R.id.text1);
					}

					// Ensure non repetability
					if (btDevices.getPosition(device) < 0) {
						btDevices.add(device);
						// Add the name and address to an array adapter to show
						// in a ListView
						mArrayAdapter.add(device.getName() + "\n"
								+ device.getAddress() + "\n" /*
															 * +
															 * "Status : Unpaired"
															 */);
						// mArrayAdapter.notifyDataSetChanged();
						mArrayAdapter.notifyDataSetInvalidated();
					}
				} catch (Exception ex) {
					// ex.fillInStackTrace();
				}
			}
		}
	};

	@Override
	protected void onListItemClick(ListView l, View v, final int position,
			long id) {
		super.onListItemClick(l, v, position, id);

		// Dont proceed if the bluetooth adapter is not valid
		if (mBluetoothAdapter == null) {
			return;
		}

		// Cancel the dicovery if still going on
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}

		// Try to connect with the selected device,
		Toast.makeText(
				getApplicationContext(),
				"Connecting to " + btDevices.getItem(position).getName() + ","
						+ btDevices.getItem(position).getAddress(),
				Toast.LENGTH_SHORT).show();

		// made the thread different as the connecting proceedure might break
		// down the system
		Thread connectThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					try {
						Method m = btDevices
								.getItem(position)
								.getClass()
								.getMethod("createInsecureRfcommSocket",
										new Class[] { int.class });
						try {
							mbtSocket = (BluetoothSocket) m.invoke(
									btDevices.getItem(position), 1);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					// mbtSocket =
					// btDevices.getItem(position).createRfcommSocketToServiceRecord(SPP_UUID);

					mbtSocket.connect();
				} catch (IOException ex) {
					// Toast.makeText(getApplicationContext(),
					// "Unable to connect to " +
					// btDevices.getItem(position).getName(),
					// Toast.LENGTH_LONG).show();
					runOnUiThread(socketErrorRunnable);
					// Handler myHandler = new Handler();
					// myHandler.post(socketErrorRunnable);
					try {
						mbtSocket.close();
					} catch (IOException e) {
						// e.printStackTrace();
					}
					mbtSocket = null;
					return;
				} finally {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// flushData();
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

