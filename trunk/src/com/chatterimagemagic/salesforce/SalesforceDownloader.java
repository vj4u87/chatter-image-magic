package com.chatterimagemagic.salesforce;

import java.net.URL;

import com.chatterimagemagic.common.*;

public class SalesforceDownloader extends Downloader {

	private SalesforceClient salesforceClient;

	public SalesforceDownloader(SalesforceClient sc, String d) {
		super(d);
		salesforceClient = sc;
	}
	
	private String getTokenString() {
		return "?oauth_token=" + salesforceClient.getSessionId();
	}
	
	/*
	 * Sometimes the urls include the host and port, sometimes they don't
	 */
	public String getDownloadUrl(String url) {
		String hostAndPath;
		if (url.contains("https://")) {
			hostAndPath = url;
		} else {
			hostAndPath = salesforceClient.getProtocolAndHost() + url;
		}

		return hostAndPath + getTokenString();
	}

}
