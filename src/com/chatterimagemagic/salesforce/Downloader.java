package com.chatterimagemagic.salesforce;

import java.net.URL;

public abstract class Downloader {
	protected String downloadDirectory;
	
	public String getDownloadDirectory() {
		return downloadDirectory;
	}
	
	public abstract String getDownloadUrl(String url);
}
