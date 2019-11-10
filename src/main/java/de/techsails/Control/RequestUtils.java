package de.techsails.Control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtils {
	public static String sendGet(String url, String apiKey) throws Exception {
		//System.out.println("GET "+url);
        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");

        //add request header
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        if(apiKey !=null) httpClient.addRequestProperty("api-key", apiKey);
        httpClient.addRequestProperty("Accept", "application/json");
        httpClient.setConnectTimeout(10000);
        
        int responseCode = httpClient.getResponseCode();
       
        System.out.println("Response Code : " + responseCode);

        httpClient.connect();
        
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            httpClient.disconnect();
            in.close();
            //print result
            System.out.println(response.toString());
           return response.toString();

        }

    }
	
	public static String sendGet(String url) throws Exception {
		return sendGet(url, null);

    }
}
