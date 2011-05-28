package com.chatterimagemagic.common;
import java.io.*;

import com.chatterimagemagic.salesforce.Downloader;
import com.chatterimagemagic.salesforce.SalesforceDownloader;

public interface Downloadable {
	public String getUrl();
	
	public File getLocalFileName();
	
	public File getLocalDirectory();
	
	public void downloadToFile(Downloader d);
}
