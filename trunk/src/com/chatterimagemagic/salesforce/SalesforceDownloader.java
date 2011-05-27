package com.chatterimagemagic.salesforce;
import com.chatterimagemagic.common.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.chatterimagemagic.common.Utils;



public class SalesforceDownloader {
	
	private SalesforceClient salesforceClient;
	private String dowloadDirectory;
	
	public SalesforceDownloader(SalesforceClient sc, String d) {
		salesforceClient = sc;
		dowloadDirectory = d;
	}
	
	private String getTokenString() {
		return "?oauth_token=" + salesforceClient.getSessionId();
	}
	
	/*
	 * Sometimes the urls include the host and port, sometimes they don't
	 * 
	 */
	public String getFullUrl(String url) {
		String hostAndPath;
		if (url.contains("https://")){
			hostAndPath = url;
		} else {
			hostAndPath = salesforceClient.getProtocolAndHost() + url;
		}
		
		return hostAndPath + getTokenString();
	}
	
	public void downloadImageToFile(Downloadable d) {
		 try {
			 URL url = new URL(getFullUrl(d.getUrl()));
			 
			 if (Utils.isDebugEnabled()) {
				 System.out.println(url);
			 }
			 BufferedImage image = ImageIO.read(url);
			 
			 //need to check format and save with correct extension
			 
			 File directory = new File(dowloadDirectory,d.getDirectory().getPath());
			 directory.mkdir();
			 
			 File file = new File(directory,d.getFile().getPath());
			 ImageIO.write(image, "png", file);
		 } catch (IOException e) { 
			 System.out.println("Error" + e.getMessage()); 
		 }
	}
	
}
