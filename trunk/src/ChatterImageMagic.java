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
	
	/*
	function downloadImage($url,$file) {
		// create curl resource 
		$ch = curl_init(); 

		// set url 
		curl_setopt($ch, CURLOPT_URL, $url);

		//return the transfer as a string 
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
		curl_setopt($ch, CURLOPT_BINARYTRANSFER,1);

		// $output contains the output string 
		$output = curl_exec($ch); 

		// close curl resource to free up system resources 
		curl_close($ch);

		$fullpath = $file;
		if(file_exists($fullpath)){
		        unlink($fullpath);
		    }
		    $fp = fopen($fullpath,'x');
		    fwrite($fp, $output);
		    fclose($fp);
		}
	
	*/
	
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
		SalesforceClient salesforceClient = new SalesforceClient("vswamidass@dev.salesforce.com","kanowins");
		if (salesforceClient.login()) {
			SObject[] x = salesforceClient.runQuery("Select id from user");
		}
		
		try {
			URL u = new URL("http://salesforce.com/xyx/asdfa");
			System.out.println(u.getPath());
			StringBuilder s = new StringBuilder("http://salesforce.com/xyx/asdfa");
			Integer location = s.indexOf(u.getPath());
			
			s.replace(location, s.length(), "");
			
			System.out.println(s);
		} catch (Exception e) {
			
		}
		
		
		
		//$query = "Select Id,SmallPhotoUrl,FullPhotoUrl from User where FullPhotoUrl !=  '/profilephoto/005/F' and IsActive = true";
		//$response = $client->query($query);

		/*
		 * 
		 * Query all users with photo URL
		 * create ChatterProfileObject with small/full photo urls
		 * call downloader(Client, ChatterProfileObject)
		 * 
		 * 
		 */
		
		
		/*
		while (!$done) {
			foreach ($response->records as $record) {
				if($record instanceof SObject){
						$row = getQueryResultRow($record);
				} else{
						$row = getQueryResultRow(new SObject($record));
				}

		$id = $row[0];
		$smallPath = $row[1];
		$fullPath = $row[2];

		$smallUrl = "https://na1.salesforce.com" . $smallPath . "?oauth_token=".$loginResult->sessionId; 
		$smallFile = 'small_images/' . $id . '_s.png';
		downloadImage($smallUrl, $smallFile);

		$fullUrl = "https://na1.salesforce.com" . $fullPath . "?oauth_token=".$loginResult->sessionId; 
		$fullFile = 'large_images/' . $id . '_f.png';
		#downloadImage($fullUrl, $fullFile);
			}


		        if ($response->done) {
		                $done = true;
		        } else {
		                $response = $client->queryMore($response->queryLocator);
		        }
		}
		*/
		
	}




}
