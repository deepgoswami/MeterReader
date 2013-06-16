package commgate.in.meterreader;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPInterface 
{
	String mUserName, mPassword, mHostName;
	FTPClient ftpClient;
	String localFileName = null;
	Context ctx;

	public FTPInterface(String host, String uname, String password, Context context)
	{
		mUserName = uname;
		mPassword = password;
		mHostName = host;
		ctx = context;
		
	}
	
	public String[] getFiles()
	{
		
		String[] fileList = new String[500];
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(mHostName);
			ftpClient.login(mUserName, mPassword);
			fileList = ftpClient.listNames("/ftp");
			
			
		} 
		catch (SocketException e) {
			Log.d("DEEPGOSWAMI", "Socket Exception " + e.toString());
			
		} 
		catch (IOException e) {
			
			Log.d("DEEPGOSWAMI", "IOException " + e.toString());
		}
		
		return fileList;
	}
	
	
	public File getFile(String fileName)
	{
		String filePath = null;
		localFileName = "inputfile.txt";
		try {
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
			File localFile = getPublicStorageDirectory(fileName);
			return (localFile);
		}
		catch (IOException ie)
		{
			Log.d("DEEPGOSWAMI", "IOException in FTPInterface: getFile " + ie.toString() );
		}
		return null;
	}

	
	
	
	private File getPublicStorageDirectory(String theFile) 
	{
		File file = new File(ctx.getExternalFilesDir(null), theFile);
		return file;
	}
	
}
