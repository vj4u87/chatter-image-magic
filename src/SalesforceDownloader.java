import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


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
	public String getFullUrl(String path) {
		String hostAndPath;
		if (path.contains("https://")){
			hostAndPath = path;
		} else {
			hostAndPath = salesforceClient.getProtocolAndHost() + path;
		}
		
		return hostAndPath + getTokenString();
	}
	
	public void downloadImageToFile(Downloadable d) {
		 try {
			 URL url = new URL(getFullUrl(d.getUrl()));
			 
			 System.out.println(url);
			 BufferedImage image = ImageIO.read(url);
			 
			//need to check format and save with correct extension
			 
			 File directory = new File(dowloadDirectory);
			 directory.mkdir();
			 
			 File file = new File(directory, d.getFileName()); 
			 ImageIO.write(image, "png", file);
		 } catch (IOException e) { 
			 System.out.println("Error" + e.getMessage()); 
		 }
	}
	
}
