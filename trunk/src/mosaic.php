<?php
header("Content-Type: text/csv");
//header("Content-Disposition: inline; filename=feedcomment.csv");

error_reporting(E_ALL);
require_once ('./soapclient/SforcePartnerClient.php');
require_once ('./soapclient/SforceHeaderOptions.php');

$username = $argv[1];
$password = $argv[2];
if (strlen($argv[3]) > 0) {
	$endpoint = $argv[3];
} else {
	$endpoint = "https://www.salesforce.com";
}

function getQueryResultHeaders($sobject, $tail=""){	
	if(!isset($headerBufferArray)){
		$headerBufferArray = array();
	}

	if (isset($sobject->Id)){
		$headerBufferArray[] = $tail . "Id";
	}

	if (isset($sobject->fields)){
		foreach($sobject->fields->children() as $field){
			$headerBufferArray[] = $tail . htmlspecialchars($field->getName(),ENT_QUOTES,'UTF-8');
		}
	}

	if(isset($sobject->sobjects)){
		foreach($sobject->sobjects as $sobjects){
			$recurse = getQueryResultHeaders($sobjects, $tail . htmlspecialchars($sobjects->type,ENT_QUOTES,'UTF-8') . ".");
			$headerBufferArray = array_merge($headerBufferArray, $recurse);
		}
	}

	if(isset($sobject->queryResult)){
		if(!is_array($sobject->queryResult)) $sobject->queryResult = array($sobject->queryResult);
		foreach($sobject->queryResult as $qr){
			$headerBufferArray[] = $qr->records[0]->type;			
		}
	}	

	return $headerBufferArray;
}


function getQueryResultRow($sobject, $escapeHtmlChars=true){

	if(!isset($rowBuffer)){
		$rowBuffer = array();
	}
	 
	if (isset($sobject->Id)){
		$rowBuffer[] = $sobject->Id;
	}

	if (isset($sobject->fields)){
		foreach($sobject->fields as $datum){
			$rowBuffer[] = $escapeHtmlChars ? htmlspecialchars($datum,ENT_QUOTES,'UTF-8') : $datum;
		}
	}

	if(isset($sobject->sobjects)){
		foreach($sobject->sobjects as $sobjects){
			$rowBuffer = array_merge($rowBuffer, getQueryResultRow($sobjects,$escapeHtmlChars));
		}
	}
	
	if(isset($sobject->queryResult)){
		$rowBuffer[] = $sobject->queryResult;
	}
	
	return $rowBuffer;
}


function createQueryResultTable($records){
	$table = "<table id='query_results'>\n";

	//call shared recusive function above for header printing
	$table .= "<tr><th></th><th>";
	
	if($records[0] instanceof SObject){
		$table .= implode("</th><th>", getQueryResultHeaders($records[0]));
	} else{
		$table .= implode("</th><th>", getQueryResultHeaders(new SObject($records[0])));
	}	
	$table .= "</th></tr>\n";

	$rowNum = 1;
	//Print the remaining rows in the body
	foreach ($records as $record){
		//call shared recusive function above for row printing
		$table .= "<tr><td>" . $rowNum++ . "</td><td>";
		
		if($record instanceof SObject){
			$row = getQueryResultRow($record); 
		} else{
			$row = getQueryResultRow(new SObject($record)); 
		}
		
		for($i = 0; $i < count($row); $i++){				
			if($row[$i] instanceof QueryResult && !is_array($row[$i])) $row[$i] = array($row[$i]);		
			if(isset($row[$i][0]) && $row[$i][0] instanceof QueryResult){
				foreach($row[$i] as $qr){					
					$table .= createQueryResultTable($qr->records);	
					if($qr != end($row[$i])) $table .= "</td><td>";
				}
			} else {
				$table .= $row[$i];
			}
					
			if($i+1 != count($row)){
				$table .= "</td><td>";
			}
		}
		
		$table .= "</td></tr>\n";
	}
	
	$table .= "</table>";

	return $table;
}

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





// Salesforce Login information
$wsdl = './soapclient/sforce.200.partner.wsdl';


// Process of logging on and getting a salesforce.com session
$client = new SforcePartnerClient();
$client->createConnection($wsdl);
$client->setEndpoint($endpoint);
$loginResult = $client->login($username, $password);

$query = "Select Id,SmallPhotoUrl,FullPhotoUrl from User where FullPhotoUrl !=  '/profilephoto/005/F' and IsActive = true";
$response = $client->query($query);

$done = false;
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
