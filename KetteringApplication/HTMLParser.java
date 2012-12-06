import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

/********************************************************************
 * Class: HTMLParser
 * Purpose: class to parse HTML and return as a string
/*******************************************************************/
public class HTMLParser {
	
	
	public static String parseInput(InputStream stream) throws IOException{
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		
		String inputLine;
		String html = "";
		
		
		// Read entire HTML response
		while ((inputLine = reader.readLine()) != null){ html += inputLine + "\n"; }
		
		return html;

	}
	
	public static String parseResponse(HttpResponse response) throws IOException{
		
		InputStream stream = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		
		String inputLine;
		String html = "";
		
		
		// Read entire HTML response
		while ((inputLine = reader.readLine()) != null){ html += inputLine + "\n"; }
		
		return html;

	}
	
}
