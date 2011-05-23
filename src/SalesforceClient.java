import java.io.FileNotFoundException;

import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
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


public class SalesforceClient {
	private static String DEFAULT_ENDPOINT = "http://www.salesforce.com/services/Soap/u/21.0";
	PartnerConnection connection;
	String authEndPoint = "";
	
	public boolean login() {
		boolean success = false;
		String userId = Utils.getUserInput("UserID: ");
		String passwd = Utils.getUserInput("Password: ");
		try {
			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(userId);
			config.setPassword(passwd);
			
			config.setAuthEndpoint(DEFAULT_ENDPOINT);
			config.setTraceFile("traceLogs.txt");
			config.setTraceMessage(true);
			config.setPrettyPrintXml(true);
			connection = new PartnerConnection(config);
			GetUserInfoResult userInfo = connection.getUserInfo();
			System.out.println("\nLogging in ...\n");
			System.out.println("UserID: " + userInfo.getUserId());
			System.out.println("User Full Name: " + userInfo.getUserFullName());
			System.out.println("User Email: " + userInfo.getUserEmail());
			System.out.println();
			System.out.println("SessionID: " + config.getSessionId());
			System.out.println("Auth End Point: " + config.getAuthEndpoint());
			System.out.println("Service End Point: "
					+ config.getServiceEndpoint());
			System.out.println();
			success = true;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		return success;
	}
	
	public SObject[] runQuery(String soql) {
		try {
			QueryResult result = connection.query(soql);
			
			boolean done = false;
			
			if (result.getSize() > 0) {
				while (!done) {
					SObject[] records = result.getRecords();
					for (int i = 0; i < records.length; ++i) {
						System.out.println("xxx");
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
			
			return null; 
		} catch (ConnectionException ce) {
			ce.printStackTrace();
			return null;
		}
	}
	
}
