package tmcit.freedom.System;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ProblemDownloader {
	final private static String problemURL = "http://153.126.185.98/yurucon01practice/quest/";
	final private static String tmpDir = System.getProperty("user.home") + "/temp/YuruCon01/Problem/";

	final public static String getFileName(int num){
		String name = "quest" + String.valueOf(num) + ".txt";
		return name;
	}
	final public static String getDirectory(){
		return tmpDir;
	}
	
	private static int problemNum = 0;
	public static int getProblemNum(){
		return problemNum;
	}
	
	public enum Bool{TRUE, FALSE, MAKE, EXCEPTION}

	public ProblemDownloader(){
		for(problemNum = 1; true; problemNum++){
			String fileName = getFileName(problemNum);
			if(this.isExistURL(problemURL + fileName) == false)break;
			Bool b = isFileExist(fileName);
			if(b == Bool.TRUE)continue;
			if(b == Bool.EXCEPTION)break;
			if(b == Bool.FALSE){
				b = makeFile(fileName);
				if(b == Bool.EXCEPTION)break;
			}
			if(this.download(fileName) == false)break;
		}
		problemNum--;
	}
	
	public static Bool isFileExist(String fileName){
		File file = new File(tmpDir);
		if(file.exists() == false){
			try{file.mkdirs();}
			catch(Exception e){return Bool.EXCEPTION;}
		}
		file = new File(tmpDir + fileName);
		if(file.exists())return Bool.TRUE;
		return Bool.FALSE;
	}
	
	public static Bool makeFile(String fileName){
		File file = new File(tmpDir + fileName);
		if(file.exists() == false){
			try{file.createNewFile();return Bool.MAKE;}
			catch(Exception e){return Bool.EXCEPTION;}
		}
		return Bool.TRUE;
	}

	private boolean isExistURL(String urlStr) {
		int status = 0;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			status = conn.getResponseCode();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (status == HttpURLConnection.HTTP_OK) {
			return true;
		} else {
			return false;
		}
	}

	private boolean download(String fileName){
		try {
			URL url = new URL(problemURL + fileName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
			
			int httpStatusCode = conn.getResponseCode();
			
			if(httpStatusCode != HttpURLConnection.HTTP_OK){
				throw new Exception();
			}
			
			// Input Stream
			DataInputStream dataInStream = new DataInputStream(conn.getInputStream());
			
			// Output Stream
			FileOutputStream fileOutputStream = new FileOutputStream(getDirectory() + fileName);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			DataOutputStream dataOutStream = new DataOutputStream(bufferedOutputStream);

			// Read Data
			byte[] b = new byte[4096];
			int readByte = 0;
			while(-1 != (readByte = dataInStream.read(b))){
				dataOutStream.write(b, 0, readByte);
			}
			
			// Close Stream
			dataInStream.close();
			dataOutStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
