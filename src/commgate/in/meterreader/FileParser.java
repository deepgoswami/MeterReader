package commgate.in.meterreader;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import android.database.SQLException;
import android.database.sqlite.*;
import android.os.Environment;
import android.util.Log;

import android.content.ContentValues;
import android.content.Context;
import au.com.bytecode.opencsv.CSVReader;


public class FileParser 
{
	Context theContext;
	SQLiteDatabase theDb;
	
	public FileParser(Context ctx)
	{
		theContext = ctx;
	}
	
	
	
	public void readFile()
	{
		String fileName = getFileName();
		CSVReader theReader = null;
		String[] csvLine = null;
		try
		{
			theReader = new CSVReader(new FileReader(fileName));
			while ((csvLine = theReader.readNext()) != null)
			{
				loadIntoDB(csvLine);
			}
		}
		catch (IOException ie)
		{
			Log.e("DEEPGOSWAMI", "Error Opening File: " + ie.toString());
		}
		finally
		{
			try {
				theReader.close();
			} 
			catch (IOException e) {
				Log.e("DEEPGOSWAMI", "Error Closing Input File: " + e.toString());
			}
		}
		
		
			
		
		
	}



	private String getFileName() 
	{
		String fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		fileName = fileName + "/commgate/input.txt";
		Log.i("DEEPGOSWAMI", "filename = " + fileName);
		return fileName;
	}



	



	private void loadIntoDB(String[] csvLine) 
	{
		DbHelper dbHelper = new DbHelper(theContext);
		theDb = dbHelper.getWritableDatabase();
		
			
		
		try {
			
			
			
			theDb = dbHelper.getWritableDatabase();
			ContentValues theValues = new ContentValues();
			theValues.clear();
			
			
			theValues.put("CONS_REF", csvLine[0]);
			theValues.put("BILL_MTH", csvLine[1]);
			theValues.put("SDO_CD",csvLine[2]);
			theValues.put("BINDER", csvLine[3]);
			theValues.put("ACC_NO", csvLine[4]);
			
			theValues.put("OLD_ACC_NO", csvLine[5]);
			theValues.put("NAME", csvLine[6	]);
			theValues.put("ADDR1", csvLine[7]);
			theValues.put("ADDR2", csvLine[8]);
			theValues.put("ADDR3", csvLine[9]);
			
			theValues.put("CONN_LOAD", csvLine[10]);
			theValues.put("TRF_CD", csvLine[11]);
			theValues.put("CSTS_CD", csvLine[12]);
			theValues.put("METER_NO", csvLine[13]);
			theValues.put("MTR_RENT", csvLine[14]);
			
			theValues.put("MF", csvLine[15]);
			theValues.put("OPNRDG", csvLine[16]);
			theValues.put("OPNRDGDT", csvLine[17]);
			theValues.put("OPNRDGSTS", csvLine[18]);
			theValues.put("ILRDG", csvLine[19]);
			
			
			theValues.put("FLRDG", csvLine[20]);
			theValues.put("CUR_SUR_CHG", csvLine[21]);
			theValues.put("ADJUSTABLE_BD", csvLine[22]);
			theValues.put("ADJUSTABLE_ED", csvLine[23]);
			theValues.put("ADJUSTABLE_DPS", csvLine[24]);
			
			theValues.put("SECURITY_AMT", csvLine[25]);
			theValues.put("PAY_DT1", csvLine[26]);
			theValues.put("BOOK_NO1", csvLine[27]);
			theValues.put("RECPT_NO1", csvLine[28]);
			theValues.put("RECPT_AMT1",csvLine[29]);
			/*
			theValues.put("CUR_SUND_BD", Integer.getInteger(csvLine[30]));
			theValues.put("CUR_SUND_ED", Integer.getInteger(csvLine[31]));
			theValues.put("CUR_SUND_OTHR_CHG", Integer.getInteger(csvLine[32]));
			theValues.put("CUR_SUND_SUR_CHG", Integer.getInteger(csvLine[33]));*/
			
			theDb.insertOrThrow("METER_TABLE", null, theValues);
			
		}
		catch (SQLException se)
		{
			Log.d("DEEPGOSWAMI", "SQLException se: " + se.toString());
			theDb.close();
		}
		finally
		{
			theDb.close();
		}
		
		// store the Subdivision and Binder numbers in a file
		String internalFileName = "meterReader.cfg";
		FileOutputStream fos;
		
		try {
			fos = theContext.openFileOutput(internalFileName, Context.MODE_PRIVATE);
			fos.write(csvLine[2].getBytes());
			fos.write(",".getBytes());
			fos.write(csvLine[3].getBytes());
			fos.write(",".getBytes());
		}
		catch (IOException ie)
		{
		
			Log.d("DEEPGOSWAMI", "Error writing temp values: " + ie.toString());
		}
	}
}
