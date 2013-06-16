package commgate.in.meterreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class FileDownload extends ListActivity 
{
	
	ArrayAdapter<String> theArrayAdapter = null;
	String hostName, userName, password; 
	String[] fileList = new String[500];
	String fileName = "inputFile.txt";
	File theLocalFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_file_download);
		
		theArrayAdapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listbackground);
		setListAdapter(theArrayAdapter);
		
		parseFtpOptions();
		FTPInterface fi = new FTPInterface(hostName, userName, password, getApplicationContext());
		fileList = fi.getFiles();
		for (int i = 0; i < fileList.length; i++)
			theArrayAdapter.add(fileList[i]);
		
		
		
		theArrayAdapter.notifyDataSetInvalidated();
		theArrayAdapter.notifyDataSetChanged();
		
		theLocalFile = fi.getFile(fileName);
		
		
	}
	
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		fileName = (String) l.getItemAtPosition(position);
		
		
		
	}
	
	
	
	

	private void parseFtpOptions() 
	{
		try {
			FileInputStream fis = openFileInput("meterreader.txt");
			
			byte buffer[] = new byte[512];
			int ch = fis.read(buffer);
			String msg;
			
			if (ch != -1) {
				msg = new String(buffer, 0, ch);
				StringTokenizer st = new StringTokenizer(msg, ",\n\t ");
				String theString[] = new String[3];
				int i = 0;
				while (st.hasMoreTokens())
				{
					theString[i] = st.nextToken();
					i++;
				}
				
				
				
				hostName = theString[0];
				userName = theString[1];
				password = theString[2];
				
			}
		}
		catch (FileNotFoundException fn)
		{
			
		}
		catch (IOException ie)
		{
			Toast.makeText(getApplicationContext(), "IO Error " + ie.toString(), Toast.LENGTH_SHORT).show();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_download, menu);
		return true;
	}

}
