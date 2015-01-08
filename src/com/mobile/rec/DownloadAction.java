package com.mobile.rec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadAction {

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	private OutputStream fileoutputStream;
	private String downloadFilename = "something.json";

	private String fileName;
	public String getFileName() {
		return fileName;
	}

	public long getContentLength() {
		return contentLength;
	}

	private long contentLength;	

	public String execute() throws IOException, ParseException  {
		String fDateDisplay = formatToDisplay(Info.fDateDisplay);
		String tDateDisplay = formatToDisplay(Info.tDateDisplay);
		String display = fDateDisplay+" - "+tDateDisplay+".json";
		FileWriter file = new FileWriter("C:/"+display);
		try {
			file.write(Info.fileToDownload.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			file.flush();
			file.close();
		}
		File fileToDownload = new File("C:/"+display);
		inputStream = new FileInputStream(fileToDownload);
		contentLength = fileToDownload.length();
		fileName =  fileToDownload.getName();
		
		return "success";
	}

	public OutputStream getFileoutputStream() {
		return fileoutputStream;
	}
	
	private String formatToDisplay(String input) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
	     Date date = format1.parse(input);
	     SimpleDateFormat format2 = new SimpleDateFormat("MMMMM dd yyyy");
	     System.out.println(format2.format(date));
	     return format2.format(date);
		
	}

}
