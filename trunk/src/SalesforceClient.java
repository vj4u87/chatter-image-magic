import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.*;

import java.io.*;

import javax.imageio.ImageIO;

public class SalesforceClient {
	private static String DEFAULT_ENDPOINT = "http://www.salesforce.com/services/Soap/u/21.0";
		
	private ConnectorConfig config = new ConnectorConfig();
	
	private PartnerConnection connection;
	
	public SalesforceClient (String username, String password) {
		try {
			config.setUsername(username);
			config.setPassword(password);

			config.setAuthEndpoint(DEFAULT_ENDPOINT);
			
			if (Utils.isDebugEnabled()) {
				config.setTraceFile("traceLogs.txt");
				config.setTraceMessage(true);
				config.setPrettyPrintXml(true);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	public void setEndpoint(String e) {
		config.setAuthEndpoint(e);
	}
	
	public boolean login() {
		boolean success = false;

		try {
			connection = new PartnerConnection(config);
			success = true;
		} catch (ConnectionException ce) {
			return success;
		}
		return success;
	}
	
	public void logout() {
		try {
			connection.logout();
			System.out.println("Logged out");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}
	
	public String getServiceEndpoint() {
		return config.getServiceEndpoint();
	}
	
	public String getProtocolAndHost() {
		try {
			URL u = new URL(this.getServiceEndpoint());
			StringBuilder s = new StringBuilder(this.getServiceEndpoint());
			Integer pathIndex = s.indexOf(u.getPath());
			
			s.replace(pathIndex, s.length(), "");
			
			return s.toString();
		} catch (MalformedURLException e) {
			return null;
		}	
	}
	
	public String getSessionId() {
		return config.getSessionId();
	}
	
	public void getUserInfo() {
		try {
			GetUserInfoResult userInfo = connection.getUserInfo();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} 
		
		System.out.println("\nLogging in ...\n");
		/*System.out.println("UserID: " + userInfo.getUserId());
		System.out.println("User Full Name: " + userInfo.getUserFullName());
		System.out.println("User Email: " + userInfo.getUserEmail());
		System.out.println();
		*/

	}

	public List<SObject> getQueryResultRecords(String soql) {
		try {
			QueryResult result = connection.query(soql);
			
			List<SObject> allRecords = new ArrayList<SObject>();
			
			boolean done = false;
			
			if (result.getSize() > 0) {
				while (!done) {
					SObject[] records = result.getRecords();
					for (SObject s : records) {
						allRecords.add(s);
					}
					if (result.isDone()) {
						done = true;
					} else {
						result = connection.queryMore(result.getQueryLocator());
					}
				}
			} else {
				System.out.println("No records found.");
			}
			
			return allRecords; 
		} catch (ConnectionException ce) {
			ce.printStackTrace();
			return null;
		}
	}
	
}
