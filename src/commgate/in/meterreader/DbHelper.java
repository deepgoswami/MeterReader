package commgate.in.meterreader;


import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.*;
import android.util.Log;

public class DbHelper extends  SQLiteOpenHelper
{
	private static final String dbName = "TEST";
	private static final int dbVersion = 1;
	private Context ctx;
	
	public DbHelper(Context context)
	{
		super(context, dbName, null, dbVersion);
		ctx = context;
		Log.w("DEEPGOSWAMI","in db Helper" );
	}
	
	@Override
	public void onCreate(SQLiteDatabase theDB)
	{
		InputStream is = null;
		String sqlCreateTable = null;
		byte[] buffer = new byte[7000];
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
		
		Log.d("DEEPGOSWAMI", "SQL: " + sqlCreateTable);
		
		theDB.execSQL(sqlCreateTable);
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
