package commgate.in.meterreader;


import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;

public class DbHelper extends  SQLiteOpenHelper
{
	private static final String dbName = "TEST";
	private static final int dbVersion = 1;
	private Context ctx;
	private static final String TAG = "DEEPGOSWAMI";
	
	public DbHelper(Context context)
	{
		super(context, dbName, null, dbVersion);
		ctx = context;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase theDB)
	{
		InputStream is = null;
		String sqlCreateTable = null;
		String sqlCreateOutputTable = null;
		
		byte[] buffer = new byte[7000];
		
		
		// Run the input table creation sql
		AssetManager theAsset = ctx.getAssets();
		try {
			
			is = theAsset.open("dbCreate.txt");
			is.read(buffer);
			sqlCreateTable = new String(buffer);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//theAsset.close();
		}
		
		try {		
			theDB.execSQL(sqlCreateTable);
		}
		catch (SQLException se)
		{
			Log.d(TAG, "Could not create input table: " + se.toString());
		}
		
		
		//Create the Output Table
		
		
		try {
			
			is = theAsset.open("dbOutputTableCreate.txt");
			is.read(buffer);
			sqlCreateOutputTable = new String(buffer);
		} 
		catch (IOException e) 
		{
			Log.d(TAG, "Could not open dbOutputTableCreate.txt " + e.toString());
		}
		
		
		try {		
			theDB.execSQL(sqlCreateOutputTable);
		}
		catch (SQLException se)
		{
			Log.d(TAG, "Could not create input table: " + se.toString());
		}
		
	}

	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w("DEEPGOSWAMI","Upgrading" );
	    db.execSQL("DROP TABLE IF EXISTS " + "METERREADER_TABLE");
	    onCreate(db);
	  }
	
	public void dropTable(SQLiteDatabase db) {
	    Log.w("DEEPGOSWAMI","dropTable" );
	    db.execSQL("DROP TABLE IF EXISTS " + "METERREADER_TABLE");
	    
	  }
	
}
