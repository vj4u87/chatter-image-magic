import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class SalesforceDownloader {
	
	private SalesforceClient salesforceClient;
	
	public SalesforceDownloader(SalesforceClient sc) {
		salesforceClient = sc;
	}
	
	public String getFullUrl(String path) {
		return salesforceClient.getProtocolAndHost() + path +
				"?oauth_token=" + salesforceClient.getSessionId();
	}
	
	public void downloadImageToFile(Downloadable d) {
		 try {
			 URL url = new URL(getFullUrl(d.getUrl())); 
			 System.out.println(url);
			 BufferedImage image = ImageIO.read(url);
		  
			 //get type
			 
			 //save with correct extension
			 File file = new File(d.getFileName()); 
			 ImageIO.write(image, "png", file);
			 
		 } catch (IOException e) { 
			 System.out.println("error" + e.getMessage()); 
		 }
	}
	
}
