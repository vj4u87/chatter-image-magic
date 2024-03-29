package com.chatterimagemagic.salesforce.chatter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.chatterimagemagic.common.Downloadable;
import com.chatterimagemagic.common.Downloader;
import com.chatterimagemagic.common.Utils;
import com.chatterimagemagic.salesforce.SalesforceDownloader;
import com.chatterimagemagic.salesforce.SalesforceResource;

public class ChatterProfileImage extends SalesforceResource implements Downloadable {
	
	ChatterUser user;
	ChatterImageType type;
	
	BufferedImage image;
	
	public enum ChatterImageType {SMALL, FULL}
	
	public ChatterProfileImage(ChatterUser u, ChatterImageType t) {
		user = u;
		type = t;
	}
	
	public File getLocalFileName() {
		return new File(user.getUserId() + '_' + user.getName().toLowerCase().replace('\'', '_').replace(' ', '_'));
	}

	public File getLocalDirectory() {
		return new File(type.toString().toLowerCase()); 
	}
	
	public String getUrl() {
		return type == ChatterImageType.SMALL ? user.getSmallPhotoUrl() : user.getFullPhotoUrl();
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
			//bmps don't work well with metapixel, so jpeg should be a good substitute
			String format = getFormatName(u.openStream()).toLowerCase();
			if (format.equals("bmp")) {
				format = "jpeg";
			}
			return format;
		} catch (IOException e) {
			
		}
		
		return null;
	}

	public void downloadToFile(Downloader sd) throws Exception {
		try {
			URL url = new URL(sd.getDownloadUrl(getUrl()));

			if (Utils.isDebugEnabled()) {
				System.out.println(url);
			}
			
			File subDirectory = new File(sd.getDownloadDirectory(), getLocalDirectory()
					.getPath());
			
			if (!subDirectory.exists()) {
				if (!subDirectory.mkdirs())
					throw new IOException("unable to create directory for file " + subDirectory.getPath());
			}
			
			image = ImageIO.read(url);

			//This should be optimized somehow to get the format from the Image, but 
			//I don't know how to do that yet
			String format = getNormalizedFormat(url);

			File file = new File(subDirectory, getLocalFileName().getPath() + "." + format);
			
			//http://bugs.sun.com/view_bug.do;jsessionid=9d9608039487fffffffffa30abad058c82ac?bug_id=6560950
			ImageIO.write(image, format, file);
		} catch (Exception e) {
			throw e;
		}
	}

	
}
