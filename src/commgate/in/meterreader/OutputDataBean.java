package commgate.in.meterreader;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;
import android.widget.Toast;

public class OutputDataBean 
{
	private static String TAG = "DEEPGOSWAMI";
	private String CONS_REF;
	private String SDO_CD;
	private String BINDER;
	private String ACC_NO;
	private String METERNO;
	
	private String MTRINSTL_DATE;
	private String METERTYPE;
	private String MF;
	private String INITIAL_RED;
	private String FINAL_RED_OLDMETER;
	
	private String OLDMETER_STAT;
	private String CSTS_CD;
	private String CUR_METER_STAT;
	private String CURRRDG;
	private String CUR_RED_DT;
	
	private String BILLING_DEMAND;
	private String NEW_TRF_CD;
	private String BILL_UNITS;
	private String ENGCHG;
	private String FIXCHG;
	
	private String METERRENT;
	private String ED;
	private String NEWBD;
	private String NEWED;
	private String NEWDPS;
	
	private String NEWOTH;
	private String REFBLUNITS;
	private String REFBLBD; 
	private String REFBLED;
	private String REFBLDPS;
	
	private String REFDEDUNITS;
	private String REFDEDBD;
	private String REFDEDED;
	private String REFDEDDPS;
	private String REB_OFF;
	
	private String NETBEFDUEDT;
	private String NETAFTDUEDT;
	private String BILLBASIS;
	private String NOOFMONTHS;
	private String REB_DT;
	
	private String ISSUE_DT;
	private String BILLPERIOD;
	private String BILLSERIALNO;
	private String OLDCSTS_CD;
	private String BILL_MTH;
	
	private String REMARKS;
	private String MACHINE_SRL_NO;
	private String MTR_READER_ID;
	private String MTR_READER_NAME;
	private String REB_OYT;
	
	private String REB_RTSWHT;
	private String DOM_SPLREB;
	private String ENGCHG_OLDTRF;
	private String FIXCHG_OLDTRF;
	private String ED_OLDTRF;
	
	private String NEWBD_OLDTRF;
	private String NEWED_OLDTRF;
	private String NEWDPS_OLDTRF;
	private String NEWOTH_OLDTRF;
	private String REFBLBD_OLDTRF;
	
	private String REFBLED_OLDTRF;
	private String REFBLDPS_OLDTRF;
	private String REFDEDBD_OLDTRF;
	private String REFDEDED_OLDTRF;
	private String REFDEDDPS_OLDTRF;
	
	private String REB_OFF_OLDTRF;
	private String NETBEFDUEDT_OLDTRF;
	private String NETAFTDUEDT_OLDTRF;
	private String REB_HOSTEL;
	private String MAX_DEMD;
	
	private String RECONN_CHARGE;
	private String DUE_DATE;
	private String pathToPhoto;
	
	



	private ContentValues theValues;
	private Context theContext;
	
	
	
	public OutputDataBean(Context ctx)
	{
		theValues = new ContentValues();
		theContext = ctx;
	}


	public void writeToDB()
	{
		theValues.put("CONS_REF", CONS_REF);
		theValues.put("SDO_CD", SDO_CD);
		theValues.put("BINDER", BINDER);
		theValues.put("ACC_NO", ACC_NO);
		theValues.put("METER_NO", METERNO);
		
		theValues.put("MTRINSTL_DATE", MTRINSTL_DATE);
		theValues.put("METERTYPE", METERTYPE);
		theValues.put("MF", MF);
		theValues.put("INITIAL_RED", INITIAL_RED);
		theValues.put("FLRDG", FINAL_RED_OLDMETER);
		
		theValues.put("OLD_METER_STAT", OLDMETER_STAT);
		theValues.put("CSTS_CD", CSTS_CD);
		theValues.put("CUR_METER_STAT", CUR_METER_STAT);;
		theValues.put("CURRRDG", CURRRDG);
		theValues.put("CUR_RED_DT", CUR_RED_DT);
		
		theValues.put("BILLING_DEMAND", BILLING_DEMAND);
		theValues.put("NEW_TRF_CD", NEW_TRF_CD);
		theValues.put("BILL_UNITS", BILL_UNITS);
		theValues.put("ENGCHG", ENGCHG);
		theValues.put("FIXCHG", FIXCHG);
		
		theValues.put("MTR_RENT",METERRENT);
		theValues.put("ED",ED);
		theValues.put("NEWBD",NEWBD);
		theValues.put("NEWED",NEWED);
		theValues.put("NEWDPS",NEWDPS);
		
		theValues.put("NEWOTH",NEWOTH);
		theValues.put("REFBLUNITS",REFBLUNITS);
		theValues.put("REFBLBD",REFBLBD);
		theValues.put("REFBLED",REFBLED);
		theValues.put("REFBLDPS",REFBLDPS);
		
		theValues.put("REFDEDUNITS",REFDEDUNITS);
		theValues.put("REFDEDBD",REFDEDBD);
		theValues.put("REFDEDED",REFDEDED);
		theValues.put("REFDEDDPS",REFDEDDPS);
		theValues.put("REB_OFF",REB_OFF);
		
		theValues.put("NETBEFDUEDT",NETBEFDUEDT);
		theValues.put("NETAFTDUEDT",NETAFTDUEDT);
		theValues.put("BILLBASIS",BILLBASIS);
		theValues.put("NOOFMONTHS",NOOFMONTHS);
		theValues.put("REB_DT",REB_DT);
		
		theValues.put("ISSUE_DT",ISSUE_DT);
		theValues.put("BILLPERIOD",BILLPERIOD);
		theValues.put("BILLSERIALNO",BILLSERIALNO);
		theValues.put("OLDCSTS_CD",OLDCSTS_CD);
		theValues.put("BILL_MTH",BILL_MTH);
		
		theValues.put("REMARKS",REMARKS);
		theValues.put("MACHINE_SRL_NO",MACHINE_SRL_NO);
		theValues.put("MTR_READER_ID",MTR_READER_ID);
		theValues.put("MTR_READER_NAME",MTR_READER_NAME);
		theValues.put("REB_OYT",REB_OYT);
		
		theValues.put("REB_RTSWHT",REB_RTSWHT);
		theValues.put("DOM_SPLREB",DOM_SPLREB);
		theValues.put("ENGCHG_OLDTRF",ENGCHG_OLDTRF);
		theValues.put("FIXCHG_OLDTRF",FIXCHG_OLDTRF);
		theValues.put("ED_OLDTRF",ED_OLDTRF);
		
		theValues.put("NEWBD_OLDTRF",NEWBD_OLDTRF);
		theValues.put("NEWED_OLDTRF",NEWED_OLDTRF);
		theValues.put("NEWDPS_OLDTRF",NEWDPS_OLDTRF);
		theValues.put("NEWOTH_OLDTRF",NEWOTH_OLDTRF);
		theValues.put("REFBLBD_OLDTRF",REFBLBD_OLDTRF);
		
		theValues.put("REFBLED_OLDTRF",REFBLED_OLDTRF);
		theValues.put("REFBLDPS_OLDTRF",REFBLDPS_OLDTRF);
		theValues.put("REFDEDBD_OLDTRF",REFDEDBD_OLDTRF);
		theValues.put("REFDEDED_OLDTRF",REFDEDED_OLDTRF);
		theValues.put("REFDEDDPS_OLDTRF",REFDEDDPS_OLDTRF);
		
		theValues.put("REB_OFF_OLDTRF",REB_OFF_OLDTRF);
		theValues.put("NETBEFDUEDT_OLDTRF",NETBEFDUEDT_OLDTRF);
		theValues.put("NETAFTDUEDT_OLDTRF",NETAFTDUEDT_OLDTRF);
		theValues.put("REB_HOSTEL",REB_HOSTEL);
		theValues.put("MAX_DEMD",MAX_DEMD);
		
		theValues.put("RECONN_CHARGE",RECONN_CHARGE);
		theValues.put("PathToPhoto", pathToPhoto);
		theValues.put("DUE_DT", DUE_DATE);
		
		
		
		
		Log.d(TAG, "OutputDataBEan: Before insert: ");
		
		boolean isSuccess = insertIntoDB();
		if (isSuccess)
			Toast.makeText(theContext, "Data written successfuly", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(theContext, "Data Save FAILED: Data has already been saved", Toast.LENGTH_LONG).show();
		
		
		
		
		
			
		
	}
	
	
	
	private boolean insertIntoDB()
	{
		DbHelper dbHelper = new DbHelper(theContext);
		
		
		SQLiteDatabase theDb = dbHelper.getWritableDatabase();
		
		
		
		try {
			theDb.insertOrThrow("METER_OUTPUT_TABLE", null,theValues);
		}
		catch (Exception e)
		{
			Log.d(TAG, "Exception inserting into Database " + e.toString());
			return false;
		}
		return true;
	}
	
	
	
	
	
	public void setPathToPhoto(String pathToPhoto) {
		this.pathToPhoto = pathToPhoto;
	}


	public void setDUE_DATE(String dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}
	

	public void setCONS_REF(String cONS_REF) {
		CONS_REF = cONS_REF;
	}



	public void setSDO_CD(String sDO_CD) {
		SDO_CD = sDO_CD;
	}



	public void setBINDER(String bINDER) {
		BINDER = bINDER;
	}



	public void setACC_NO(String aCC_NO) {
		ACC_NO = aCC_NO;
	}



	public void setMETERNO(String mETERNO) {
		METERNO = mETERNO;
	}



	public void setMTRINSTL_DATE(String mTRINSTL_DATE) {
		MTRINSTL_DATE = mTRINSTL_DATE;
	}



	public void setMETERTYPE(String mETERTYPE) {
		METERTYPE = mETERTYPE;
	}



	public void setMF(String mF) {
		MF = mF;
	}



	public void setINITIAL_RED(String iNITIAL_RED) {
		INITIAL_RED = iNITIAL_RED;
	}



	public void setFINAL_RED_OLDMETER(String fINAL_RED_OLDMETER) {
		FINAL_RED_OLDMETER = fINAL_RED_OLDMETER;
	}



	public void setOLDMETER_STAT(String oLDMETER_STAT) {
		OLDMETER_STAT = oLDMETER_STAT;
	}



	public void setCSTS_CD(String cSTS_CD) {
		CSTS_CD = cSTS_CD;
	}



	public void setCUR_METER_STAT(String cUR_METER_STAT) {
		CUR_METER_STAT = cUR_METER_STAT;
	}



	public void setCURRRDG(String cURRRDG) {
		CURRRDG = cURRRDG;
	}



	public void setCUR_RED_DT(String cUR_RED_DT) {
		CUR_RED_DT = cUR_RED_DT;
	}



	public void setBILLING_DEMAND(String bILLING_DEMAND) {
		BILLING_DEMAND = bILLING_DEMAND;
	}



	public void setNEW_TRF_CD(String nEW_TRF_CD) {
		NEW_TRF_CD = nEW_TRF_CD;
	}



	public void setBILL_UNITS(String bILL_UNITS) {
		BILL_UNITS = bILL_UNITS;
	}



	public void setENGCHG(String eNGCHG) {
		ENGCHG = eNGCHG;
	}



	public void setFIXCHG(String fIXCHG) {
		FIXCHG = fIXCHG;
	}



	public void setMETERRENT(String mETERRENT) {
		METERRENT = mETERRENT;
	}



	public void setED(String eD) {
		ED = eD;
	}



	public void setNEWBD(String nEWBD) {
		NEWBD = nEWBD;
	}



	public void setNEWED(String nEWED) {
		NEWED = nEWED;
	}



	public void setNEWDPS(String nEWDPS) {
		NEWDPS = nEWDPS;
	}



	public void setNEWOTH(String nEWOTH) {
		NEWOTH = nEWOTH;
	}



	public void setREFBLUNITS(String rEFBLUNITS) {
		REFBLUNITS = rEFBLUNITS;
	}



	public void setREFBLBD(String rEFBLBD) {
		REFBLBD = rEFBLBD;
	}



	public void setREFBLED(String rEFBLED) {
		REFBLED = rEFBLED;
	}



	public void setREFBLDPS(String rEFBLDPS) {
		REFBLDPS = rEFBLDPS;
	}



	public void setREFDEDUNITS(String rEFDEDUNITS) {
		REFDEDUNITS = rEFDEDUNITS;
	}



	public void setREFDEDBD(String rEFDEDBD) {
		REFDEDBD = rEFDEDBD;
	}



	public void setREFDEDED(String rEFDEDED) {
		REFDEDED = rEFDEDED;
	}



	public void setREFDEDDPS(String rEFDEDDPS) {
		REFDEDDPS = rEFDEDDPS;
	}



	public void setREB_OFF(String rEB_OFF) {
		REB_OFF = rEB_OFF;
	}



	public void setNETBEFDUEDT(String nETBEFDUEDT) {
		NETBEFDUEDT = nETBEFDUEDT;
	}



	public void setNETAFTDUEDT(String nETAFTDUEDT) {
		NETAFTDUEDT = nETAFTDUEDT;
	}



	public void setBILLBASIS(String bILLBASIS) {
		BILLBASIS = bILLBASIS;
	}



	public void setNOOFMONTHS(String nOOFMONTHS) {
		NOOFMONTHS = nOOFMONTHS;
	}



	public void setREB_DT(String rEB_DT) {
		REB_DT = rEB_DT;
	}



	public void setISSUE_DT(String iSSUE_DT) {
		ISSUE_DT = iSSUE_DT;
	}



	public void setBILLPERIOD(String bILLPERIOD) {
		BILLPERIOD = bILLPERIOD;
	}



	public void setBILLSERIALNO(String bILLSERIALNO) {
		BILLSERIALNO = bILLSERIALNO;
	}



	public void setOLDCSTS_CD(String oLDCSTS_CD) {
		OLDCSTS_CD = oLDCSTS_CD;
	}



	public void setBILL_MTH(String bILL_MTH) {
		BILL_MTH = bILL_MTH;
	}



	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}



	public void setMACHINE_SRL_NO(String mACHINE_SRL_NO) {
		MACHINE_SRL_NO = mACHINE_SRL_NO;
	}



	public void setMTR_READER_ID(String mTR_READER_ID) {
		MTR_READER_ID = mTR_READER_ID;
	}



	public void setMTR_READER_NAME(String mTR_READER_NAME) {
		MTR_READER_NAME = mTR_READER_NAME;
	}



	public void setREB_OYT(String rEB_OYT) {
		REB_OYT = rEB_OYT;
	}



	public void setREB_RTSWHT(String rEB_RTSWHT) {
		REB_RTSWHT = rEB_RTSWHT;
	}



	public void setDOM_SPLREB(String dOM_SPLREB) {
		DOM_SPLREB = dOM_SPLREB;
	}



	public void setENGCHG_OLDTRF(String eNGCHG_OLDTRF) {
		ENGCHG_OLDTRF = eNGCHG_OLDTRF;
	}



	public void setFIXCHG_OLDTRF(String fIXCHG_OLDTRF) {
		FIXCHG_OLDTRF = fIXCHG_OLDTRF;
	}



	public void setED_OLDTRF(String eD_OLDTRF) {
		ED_OLDTRF = eD_OLDTRF;
	}



	public void setNEWBD_OLDTRF(String nEWBD_OLDTRF) {
		NEWBD_OLDTRF = nEWBD_OLDTRF;
	}



	public void setNEWED_OLDTRF(String nEWED_OLDTRF) {
		NEWED_OLDTRF = nEWED_OLDTRF;
	}



	public void setNEWDPS_OLDTRF(String nEWDPS_OLDTRF) {
		NEWDPS_OLDTRF = nEWDPS_OLDTRF;
	}



	public void setNEWOTH_OLDTRF(String nEWOTH_OLDTRF) {
		NEWOTH_OLDTRF = nEWOTH_OLDTRF;
	}



	public void setREFBLBD_OLDTRF(String rEFBLBD_OLDTRF) {
		REFBLBD_OLDTRF = rEFBLBD_OLDTRF;
	}



	public void setREFBLED_OLDTRF(String rEFBLED_OLDTRF) {
		REFBLED_OLDTRF = rEFBLED_OLDTRF;
	}



	public void setREFBLDPS_OLDTRF(String rEFBLDPS_OLDTRF) {
		REFBLDPS_OLDTRF = rEFBLDPS_OLDTRF;
	}



	public void setREFDEDBD_OLDTRF(String rEFDEDBD_OLDTRF) {
		REFDEDBD_OLDTRF = rEFDEDBD_OLDTRF;
	}



	public void setREFDEDED_OLDTRF(String rEFDEDED_OLDTRF) {
		REFDEDED_OLDTRF = rEFDEDED_OLDTRF;
	}



	public void setREFDEDDPS_OLDTRF(String rEFDEDDPS_OLDTRF) {
		REFDEDDPS_OLDTRF = rEFDEDDPS_OLDTRF;
	}



	public void setREB_OFF_OLDTRF(String rEB_OFF_OLDTRF) {
		REB_OFF_OLDTRF = rEB_OFF_OLDTRF;
	}



	public void setNETBEFDUEDT_OLDTRF(String nETBEFDUEDT_OLDTRF) {
		NETBEFDUEDT_OLDTRF = nETBEFDUEDT_OLDTRF;
	}



	public void setNETAFTDUEDT_OLDTRF(String nETAFTDUEDT_OLDTRF) {
		NETAFTDUEDT_OLDTRF = nETAFTDUEDT_OLDTRF;
	}



	public void setREB_HOSTEL(String rEB_HOSTEL) {
		REB_HOSTEL = rEB_HOSTEL;
	}



	public void setMAX_DEMD(String mAX_DEMD) {
		MAX_DEMD = mAX_DEMD;
	}



	public void setRECONN_CHARGE(String rECONN_CHARGE) {
		RECONN_CHARGE = rECONN_CHARGE;
	}



	public void setTheValues(ContentValues theValues) {
		this.theValues = theValues;
	}
	
	
}
