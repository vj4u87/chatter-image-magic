import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;

import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;

public class QuickstartApiSample {
  private static BufferedReader reader =
      new BufferedReader(new InputStreamReader(System.in));
  
  PartnerConnection connection;
  String authEndPoint = "";

  public static void main(String[] args) {
    if ( args.length < 1 ) {
      System.out.println("Usage: com.example.samples." +
          "QuickstartApiSamples <AuthEndPoint>");
      System.exit(-1);
    }
    QuickstartApiSample sample = new QuickstartApiSample(args[0]);
    if ( sample.login() ) {

    }
  }
  
  public QuickstartApiSample(String authEndPoint) {
    this.authEndPoint = authEndPoint;
  }

 public void querySample() {
    try {
      String soqlQuery = "SELECT FirstName, LastName FROM Contact";
      QueryResult result = connection.query(soqlQuery);
      boolean done = false;
      if (result.getSize() > 0) {
        System.out.println("\nLogged-in user can see " + 
            result.getRecords().length + 
            " contact records."
        );
        while (! done) {
          SObject[] records = result.getRecords();
          for ( int i = 0; i < records.length; ++i ) {
		System.out.println("xxx");
          }
          if (result.isDone()) {
            done = true;
          } else {
            result = 
                connection.queryMore(result.getQueryLocator());
          }
        }
      } else {
        System.out.println("No records found.");
      }
      System.out.println("\nQuery succesfully executed.");
    } catch (ConnectionException ce) {
      ce.printStackTrace();
    }
  }

  public String getUserInput(String prompt) {
    String result = "";
    try {
      System.out.print(prompt);
      result = reader.readLine();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return result;
  }

  public boolean login() {
    boolean success = false;
    String userId = getUserInput("UserID: ");
    String passwd = getUserInput("Password: ");
    try {
      ConnectorConfig config = new ConnectorConfig();
      config.setUsername(userId);
      config.setPassword(passwd);
      System.out.println("AuthEndPoint: " + authEndPoint);
      config.setAuthEndpoint(authEndPoint);
      config.setTraceFile("traceLogs.txt");
      config.setTraceMessage(true);
      config.setPrettyPrintXml(true);
      connection = new PartnerConnection(config);
      GetUserInfoResult userInfo = connection.getUserInfo();
      System.out.println("\nLogging in ...\n");
      System.out.println("UserID: " + userInfo.getUserId());
      System.out.println("User Full Name: " + 
          userInfo.getUserFullName());
      System.out.println("User Email: " + 
          userInfo.getUserEmail());
      System.out.println();
      System.out.println("SessionID: " + 
          config.getSessionId());
      System.out.println("Auth End Point: " + 
          config.getAuthEndpoint());
      System.out.println("Service End Point: " + 
          config.getServiceEndpoint());
      System.out.println();
      success = true;
    } catch (ConnectionException ce) {
      ce.printStackTrace();
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
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

}
