package app.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Curl {
	URL url;
	HttpURLConnection connection = null; 


	public Curl(){
		
	}


	public String excutePost(String targetUrl, String poststring, String method)
	{
		URL url;
		HttpURLConnection connection = null; 
		try {
			//	Create connection
			//System.out.println("Inside Java class");
			url = new URL(targetUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(method);
			
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(poststring.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US"); 

			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			System.out.println("PS : " + poststring);
		//	Send request
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			wr.writeBytes (poststring);
			wr.flush ();
			wr.close ();

		//	Get Response 
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			System.out.println("Inside response is"+response);
			while((line = rd.readLine()) != null) {
				System.out.println("Line -> "+line);
				response.append(line);
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if(connection != null) {
			connection.disconnect(); 
			}
		}
	}

}


