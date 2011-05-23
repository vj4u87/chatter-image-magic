import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;

import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;

public class ChatterImageMagic {
	

	PartnerConnection connection;
	String authEndPoint = "";
	
	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

	/*
	 * public static void main(String[] args) { BufferedImage image = null; try
	 * {
	 * 
	 * 
	 * // Read from a URL URL url = new
	 * URL("http://dev.caringmeals.com/images/logo.png"); image =
	 * ImageIO.read(url);
	 * 
	 * 
	 * File file = new File("newimage.png"); ImageIO.write(image, "png", file);
	 * 
	 * } catch (IOException e) { System.out.println("error"); }
	 */



	public static void main(String[] args) {
		SalesforceClient salesforceClient = new SalesforceClient();
		if (salesforceClient.login()) {
			SObject[] x = salesforceClient.runQuery("Select id from user");
		}
	}

	public ChatterImageMagic(String authEndPoint) {
		this.authEndPoint = authEndPoint;
	}






	public void logout() {
		try {
			connection.logout();
			System.out.println("Logged out");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
