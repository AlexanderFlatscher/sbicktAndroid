package geotag.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {
	private String serverURL;
	private HttpClient client = new DefaultHttpClient();
	private HttpGet clientRequest;
	
	public HttpHelper(String url){	
		clientRequest = new HttpGet(url);
		
		serverURL = url;
	}

	public String get_serverURL() {
		return serverURL;
	}

	public void set_serverURL(String serverURL) {
		if(serverURL == this.serverURL) {
			return;
		}
		
		this.serverURL = serverURL;
		
		try {
			clientRequest.setURI(new URI(serverURL));
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public String getStream() {
		BufferedReader reader;
		InputStream serverStream;
		HttpResponse serverResponse;
		String currentLine = null;
		StringBuilder stringStream = new StringBuilder();
		
        try {
        	serverResponse = client.execute(clientRequest);
			serverStream = serverResponse.getEntity().getContent();
			reader = new BufferedReader(new InputStreamReader(serverStream));

			while((currentLine = reader.readLine()) != null){
			    stringStream.append(currentLine + "\n");
			}
			
			serverStream.close();
		}
        catch (Exception e) {
			e.printStackTrace();
		}
		
		return stringStream.toString();
	}
}