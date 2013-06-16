package commgate.in.meterreader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.util.Log;



public class DataWriter 
{
	Context ctx;
	
	public DataWriter(Context context)
	{
		Log.d("DEEPGOSWAMI", "In DataWriter Constructor");
		ctx = context;
	}
	
	
	
	
	public void write(Map<String, String> map)
	{
		String name = "meterReader.txt";
		File file = getPublicStorageDirectory(name);
		Log.d("DEEPGOSWAMI", "File path: " + file.getAbsolutePath());
		try {
			FileWriter fw = new FileWriter(file);
			StringBuffer sb = new StringBuffer();
			
			getFileData(map, sb);
			fw.write(sb.toString());
			fw.close();
			
		}
		catch (IOException ie)
		{
			Log.d("DEEPGOSWAMI", ie.toString());
		}
		
	}
	
	
	
	

	




	private StringBuffer getFileData(Map<String, String> map, StringBuffer sb) 
	{
		
		
		
		String str = map.get("CONS_REF");
		
		sb.append(map.get("CONS_REF"));
		sb.append(",");
		
		sb.append(map.get("SDO_CD"));
		sb.append(",");
		sb.append(map.get("BINDER"));
		sb.append(",");
		sb.append(map.get("ACC_NO"));
		sb.append(",");
		sb.append(map.get("METER_NO"));
		sb.append(",");
		sb.append(map.get("MC_DATE"));
		sb.append(",");
		sb.append(map.get("MF"));
		sb.append(",");
		sb.append(map.get("ILRDG"));
		sb.append(",");
		sb.append(map.get("FLRDG"));
		sb.append(",");
		sb.append(map.get("CSTS_CD"));
		sb.append(",");
		sb.append(map.get("CUR_METER_STAT"));
		sb.append(",");
		sb.append(map.get("CURRRDG"));
		sb.append(",");
		sb.append(map.get("CUR_RED_DT"));
		sb.append(",");
		sb.append(map.get("BILLING_DEMAND"));
		sb.append(",");
		sb.append(map.get("NEW_TRF_CD"));
		sb.append(",");
		sb.append(map.get("BILL_UNITS"));
		sb.append(",");
		sb.append(map.get("ENGCHG"));
		sb.append(",");
		sb.append(map.get("FIXCHG"));
		sb.append(",");
		sb.append(map.get("METERRENT"));
		sb.append(",");
		sb.append(map.get("ED"));
		sb.append(",");
		sb.append(map.get("NEWBD"));
		sb.append(",");
		sb.append(map.get("NEWED"));
		sb.append(",");
		sb.append(map.get("NEWDPS"));
		sb.append(",");
		sb.append(map.get("NEWOTH"));
		sb.append(",");
		sb.append(map.get("REFBLUNITS"));
		sb.append(",");
		sb.append(map.get("REFBLBD"));
		sb.append(",");
		sb.append(map.get("REFBLED"));
		sb.append(",");
		sb.append(map.get("REFBLDPS"));
		sb.append(",");
		sb.append(map.get("REFDEDUNITS"));
		sb.append(",");
		sb.append(map.get("REFDEDBD"));
		sb.append(",");
		sb.append(map.get("REFDEDED"));
		sb.append(",");
		sb.append(map.get("REFDEDDPS"));
		sb.append(",");
		sb.append(map.get("REFBLDPS"));
		sb.append(",");
		sb.append(map.get("REB_OFF"));
		sb.append(",");
		sb.append(map.get("NETBEFDUEDT"));
		sb.append(",");
		sb.append(map.get("NETAFTDUEDT"));
		sb.append(",");
		sb.append(map.get("BILLBASIS"));
		sb.append(",");
		sb.append(map.get("NOOFMONTHS"));
		sb.append(",");
		sb.append(map.get("REB_DT"));
		sb.append(",");
		sb.append(map.get("DUE_DT"));
		sb.append(",");
		sb.append(map.get("ISSUE_DT"));
		sb.append(",");
		sb.append(map.get("BILLPERIOD"));
		sb.append(",");
		sb.append(map.get("BILLSERIALNO"));
		sb.append(",");
		sb.append(map.get("OLDCSTS_CD"));
		sb.append(",");
		sb.append(map.get("BILL_MTH"));
		sb.append(",");
		sb.append(map.get("REMARKS"));
		sb.append(",");
		sb.append(map.get("MACHINE_SRL_NO"));
		sb.append(",");
		sb.append(map.get("OLDCSTS_CD"));
		sb.append(",");
		sb.append(map.get("MTR_READER_ID"));
		sb.append(",");
		sb.append(map.get("MTR_READER_NAME"));
		sb.append(",");
		sb.append(map.get("REB_OYT"));
		sb.append(",");
		sb.append(map.get("REB_RTSWHT"));
		sb.append(",");
		sb.append(map.get("DOM_SPLREB"));
		sb.append(",");
		sb.append(map.get("ENGCHG_OLDTRF"));
		sb.append(",");
		sb.append(map.get("FIXCHG_OLDTRF"));
		sb.append(",");
		sb.append(map.get("ENGCHG_OLDTRF"));
		sb.append(",");
		sb.append(map.get("ED_OLDTRF"));
		sb.append(",");
		sb.append(map.get("NEWBD_OLDTRF"));
		sb.append(",");
		sb.append(map.get("NEWED_OLDTRF"));
		sb.append(",");
		sb.append(map.get("NEWDPS_OLDTRF"));
		sb.append(",");
		sb.append(map.get("NEWOTH_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REFBLBD_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REFBLED_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REFBLDPS_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REFDEDBD_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REFDEDED_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REB_OFF_OLDTRF"));
		sb.append(",");
		sb.append(map.get("NETBEFDUEDT_OLDTRF"));
		sb.append(",");
		sb.append(map.get("NETAFTDUEDT_OLDTRF"));
		sb.append(",");
		sb.append(map.get("REB_HOSTEL"));
		sb.append(",");
		sb.append(map.get("MAX_DEMD"));
		sb.append(",");
		sb.append(map.get("RECONN_CHARGE"));
		sb.append(",");
		
		
		
		return sb;
	}




	private File getPublicStorageDirectory(String name) 
	{
		File file = new File(ctx.getExternalFilesDir(null), name);
		return file;
	}
}
