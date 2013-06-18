package commgate.in.meterreader;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class CalculateTariff 
{
	private int oldReading;
	private int newReading;
	private int tariffCode;
	private int unitsConsumed = 0;
	
	private int level1 = 50;
	private int level2 = 200;
	private int level3 = 400;
	private int level4 = 500;
	
	private int level1Units = 0;
	private int level2Units = 0;
	private int level3Units = 0;
	private int level4Units = 0;
	
	private double fixCharge = 0.0;
	
	private double tariffLevel1 = 2.30;
	private double tariffLevel2 = 4.00;
	private double tariffLevel3 = 5.00;
	private double tariffLevel4 = 5.40;
	
	private double commTariffLevel1 = 5.10;
	private double commTariffLevel2 = 6.20;
	private double commTariffLevel3 = 6.90;
	
	private int commLevel1 = 100;
	private int commLevel2 = 300;
	
	
	private double fixedDom = 20.0; 
	private double fixedComm = 30.0;

	private String TAG = "DEEPGOSWAMI";
	
	public CalculateTariff(int old, int current, String strTrfCode, int reasonCode, double flrdg, double ilrdg, Context ctx)
	{
		oldReading = old;
		newReading = current;
		Log.i(TAG, "CalculateTariff: " + oldReading + " " + newReading + " " + strTrfCode);
		
		Toast.makeText(ctx, "old reading " + old + " new " + current, Toast.LENGTH_SHORT).show();
		if (strTrfCode.equals("1000"))
			tariffCode = 0;
		if (strTrfCode.equals("2000"))
			tariffCode = 1;
		if (reasonCode == 7)
		{
			if ((flrdg - old) < 0)
				unitsConsumed = (int) (current - ilrdg);
			else
				unitsConsumed = (int)((flrdg - old) + (current - ilrdg));
			
		}
		else
			unitsConsumed = newReading - oldReading;
		
	}
	
	
	
	
	
	public int unitsConsumed()
	{
		return unitsConsumed;
	}
	
	
	
	
	public double calculateEnergyCharge()
	{
		if (tariffCode == 0)
			return calculateDomEnergyCharge();
		if (tariffCode == 1)
			return calculateCommEnergyCharge();
		return 0.0;
	}
	
	
	
	
	private double calculateCommEnergyCharge() 
	{
		
		int unitsLevel2 = 0;
		int unitsLevel1 = 0;
		int unitsLevel3 = 0;
		
		int runningValue = unitsConsumed;
		if ( unitsConsumed > commLevel1)
		{
			unitsLevel1 = commLevel1;
			level1Units = unitsLevel1;
			runningValue = runningValue - commLevel1;
			Log.d(TAG, "Level 1 units = " + String.valueOf(unitsLevel1) + "remaining " + runningValue);
			
		}
		else
		{	
			unitsLevel1 = runningValue;
			level1Units = unitsLevel1;
			runningValue = 0;
			Log.d(TAG, "Level 1 units = " + String.valueOf(level1Units));
			return (level1Units * commTariffLevel1);
		}
		
		if (unitsConsumed > commLevel2)
		{
			
			unitsLevel2 = commLevel2 - commLevel1;
			level2Units = unitsLevel2;
			runningValue = runningValue - (commLevel2 - commLevel1);
			Log.d(TAG, "Level 2 units = " + String.valueOf(unitsLevel2)+ "remaining " + runningValue);
			
		}
		else
		{
			unitsLevel2 = runningValue;
			level2Units = unitsLevel2;
			runningValue = 0;
			Log.d(TAG, "Level 2 units = " + String.valueOf(unitsLevel2));
			return ((unitsLevel1 * commTariffLevel1) + (unitsLevel2 * commTariffLevel2));
		}
		level3Units = runningValue;
		unitsLevel3 = runningValue;
		return ((unitsLevel1 * commTariffLevel1) + (unitsLevel2 * commTariffLevel2) + (runningValue * commTariffLevel3));
		
		
	}

	
	
	
	


	public double calculateDomEnergyCharge()
	{
		if (tariffCode == 0)
		{
			int unitsLevel4 = 0;
			int unitsLevel3 = 0;
			int unitsLevel2 = 0;
			int unitsLevel1 = 0;
			
			int runningValue = unitsConsumed;

			if ( unitsConsumed > level1)
			{
				unitsLevel1 = level1;
				level1Units = unitsLevel1;
				runningValue = runningValue - level1;
				Log.d(TAG, "Level 1 units = " + String.valueOf(unitsLevel1) + "remaining " + runningValue);
			}
			else
			{
				unitsLevel1 = runningValue;
				level1Units = unitsLevel1;
				runningValue = 0;
				Log.d(TAG, "Level 1 units = " + String.valueOf(unitsLevel1));
				return (unitsLevel1 * tariffLevel1);
			}
			
			if (unitsConsumed > level2)
			{
				unitsLevel2 = level2 - level1;
				level2Units = unitsLevel2;
				runningValue = runningValue - (level2 - level1);
				Log.d(TAG, "Level 2 units = " + String.valueOf(unitsLevel2)+ "remaining " + runningValue);
				
			}
			else
			{
				unitsLevel2 = runningValue;
				level2Units = unitsLevel2;
				runningValue = 0;
				Log.d(TAG, "Level 2 units = " + String.valueOf(unitsLevel2));
				return ((unitsLevel1 * tariffLevel1) + (unitsLevel2 * tariffLevel2));
			}
			
			if (unitsConsumed > level3)
			{
				unitsLevel3 = level3 - level2;
				level3Units = unitsLevel3;
				runningValue = runningValue - (level3 - level2);
				Log.d(TAG, "Level 3 units = " + String.valueOf(unitsLevel3) + "remaining " + runningValue);
				
			}
			
			else
			{
				unitsLevel3 = runningValue;
				level3Units = unitsLevel3;
				Log.d(TAG, "Level 3 units = " + String.valueOf(unitsLevel3) + "remaining " + runningValue);
				return (((unitsLevel1 * tariffLevel1) + (unitsLevel2 * tariffLevel2)) + (unitsLevel3 * tariffLevel3));
			}
		
			if (unitsConsumed >= level4)
			{
				unitsLevel4 = runningValue;
				level4Units = unitsLevel4;
				Log.d(TAG, "Level 4 units = " + String.valueOf(unitsLevel4) + " remaining " + runningValue);
				
				return ((unitsLevel1 * tariffLevel1) + (unitsLevel2 * tariffLevel2) + (unitsLevel3 * tariffLevel3) +(unitsLevel4 * tariffLevel4));
				
			}
			
			
			
			
		}
		return 0.00;
	}
	
	
	
	
	public int[] getLevelUnits()
	{
		int[] levels = new int[4];
		levels[0] = level1Units;
		levels[1] = level2Units;
		levels[2] = level3Units;
		levels[3] = level4Units;
		
		return levels;
	}
	
	
	public double getFixedCharge(double connectionLoad)
	{
		if (tariffCode == 0)
		{
			return fixedDom * Math.ceil(connectionLoad);
		}
		if (tariffCode == 1)
			return fixedComm * Math.ceil(connectionLoad);
		return 0.0;
	}
	
	
	
	public double getElecDuty(double totalEnergy)
	{
		if ((tariffCode == 0) || (tariffCode == 1))
			return(totalEnergy * 0.04 );
		return 0.0;
	}
	
	public double getRebate(int units)
	{
		if ((tariffCode == 0) || (tariffCode == 1))
			return (0.10 * units);
		
		return 0.0;
	}
	
}
