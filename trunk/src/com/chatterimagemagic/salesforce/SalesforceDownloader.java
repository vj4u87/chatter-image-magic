package com.chatterimagemagic.salesforce;

import com.chatterimagemagic.common.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.*;

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
	 */
	public String getFullUrl(String url) {
		String hostAndPath;
		if (url.contains("https://")) {
			hostAndPath = url;
		} else {
			hostAndPath = salesforceClient.getProtocolAndHost() + url;
		}

		return hostAndPath + getTokenString();
	}

	// Returns the format name of the image in the object 'o'.
	// 'o' can be either a File or InputStream object.
	// Returns null if the format is not known.
	private static String getFormatName(Object o) {
		try {
			// Create an image input stream on the image
			ImageInputStream iis = ImageIO.createImageInputStream(o);

			// Find all image readers that recognize the image format
			Iterator iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				// No readers found
				return null;
			}

			// Use the first reader
			ImageReader reader = (ImageReader) iter.next();

			// Close stream
			iis.close();

			// Return the format name
			return reader.getFormatName();
		} catch (IOException e) {
		}
		// The image could not be read
		return null;
	}
	
	public String getNormalizedFormat(URL u) {
		try {
			String format = getFormatName(u.openStream()).toLowerCase();
			if (format.equals("bmp")) {
				format = "jpeg";
			}
			return format;
		} catch (IOException e) {
			
		}
		
		return null;
	}

	public void downloadImageToFile(Downloadable d) {
		try {
			URL url = new URL(getFullUrl(d.getUrl()));

			if (Utils.isDebugEnabled()) {
				System.out.println(url);
			}
			BufferedImage image = ImageIO.read(url);

			String format = getNormalizedFormat(url);
			

			File rootDirectory = new File(dowloadDirectory);
			rootDirectory.mkdir();

			File subDirectory = new File(rootDirectory, d.getDirectory()
					.getPath());
			subDirectory.mkdir();

			File file = new File(subDirectory, d.getLocalFileName().getPath() + "." + format);
			ImageIO.write(image, format.toLowerCase(), file);
		} catch (IOException e) {
			System.out.println("Error" + e.getMessage());
		}
	}
}
