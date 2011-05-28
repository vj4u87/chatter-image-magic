package com.chatterimagemagic.salesforce;

import java.io.File;
import java.net.URL;

public abstract class Downloader {
	private String downloadDirectory;
	
	public Downloader(String d) {
		downloadDirectory = d;
	}
	
	public String getDownloadDirectory() {
		return downloadDirectory;
	}
	
	public void setDownloadDirectory(String d) {
		downloadDirectory = d;
	}
	
	public abstract String getDownloadUrl(String url);
}
