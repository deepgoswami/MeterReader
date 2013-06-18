package commgate.in.meterreader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import au.com.bytecode.opencsv.CSVWriter;

public class FileSaver 
{
	private String TAG = "DEEPGOSWAMI";
	private String fileName;
	private Context context;
	
	
	public FileSaver(Context ctx)
	{
		context = ctx;
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE) ;
		String phoneId = 	tm.getDeviceId();
		String date = String.valueOf(System.currentTimeMillis()).substring(8);
		
		fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + phoneId + date + ".out";
	}

	
	public void saveFile()
	{
		DbHelper dbHelper = new DbHelper(context);
		SQLiteDatabase theDb = dbHelper.getReadableDatabase();
		CSVWriter theCsv = null;
		
		try {
			theCsv = new CSVWriter(new FileWriter(fileName), ',');
			
		} 
		catch (IOException e) {
			Log.d(TAG, "Error creating output file" + e.toString());
		}
		
		Cursor theCursor = theDb.query("METER_OUTPUT_TABLE", null, null, null, null, null, null);
		
		int numColumns = theCursor.getColumnCount();
		
		List<String[]> theList = new ArrayList<String[]>();
		
		String[] row = new String[numColumns];
		
		if (theCursor.moveToFirst())
		{
			while (theCursor.moveToNext())
			{
				for (int i = 0; i < numColumns; i++)
				{
					row[i] = theCursor.getString(i);
					
				}
				theList.add(row);
			}
			theCsv.writeAll(theList);
		}
		else
		{
			Log.d("TAG", "File Saver: Cursor is empty");
		}
		
		try {
			theCsv.close();
		} 
		catch (IOException e) {
			
			Log.d(TAG, "Error closing output file" + e.toString());
		}
		theCursor.close();
		dbHelper.close();
		
	}
}
