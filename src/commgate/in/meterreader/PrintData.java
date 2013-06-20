package commgate.in.meterreader;

import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class PrintData 
{
	private static BluetoothSocket mbtSocket;
	private OutputDataBean dataToPrint;
	private Context theContext;
	private OutputStream theOutputStream;
	private Activity theActivity;
	byte fontStyle;
	
	
	public PrintData(Context ctx, OutputDataBean theOutput, OutputStream theOutputDataStream, Activity theActivity)
	{
		dataToPrint = theOutput;
		theContext = ctx;
		theOutputStream = theOutputDataStream;
	}

	
	public void startPrinting(OutputStream os)
	{
		
		fontStyle &= 0xF7;
		fontStyle &= 0x7F; 
		fontStyle &= 0xEF; 
		fontStyle &= 0xEF;
		fontStyle &= 0xDF;
		fontStyle &= 0xFC;
		fontStyle &= 0xFC;
		fontStyle &= 0xFC;
			
		
		byte[] Command = { 0x1B, 0x21, fontStyle};
		try
		{
			Log.d("DEEPGOSWAMI", "starting write");
			os.write(Command);
			Log.d("DEEPGOSWAMI", "Here");
			String sendingmessage = "This is a test Message";
			byte[] send = sendingmessage.getBytes();
			os.write(send);
			os.write(0x0D);
			sendingmessage = "This is a second Line";
			send = sendingmessage.getBytes();
			os.write(send);
			os.write(0x0D);
			os.write(0x0D);
			os.write(0x0D);
			os.write(0x0D);
			os.flush();
			Log.d("DEEPGOSWAMI", "finished write");
		}
		catch (IOException ie)
		{
			Log.d("DEEPGOSWAMI", "Error writing to printer " + ie.toString());
		}

	}
	
	
	
	
	
}
