package de.techsails.Control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.techsails.JavaJSON.JSONObject;

public class RequestUtils {
	public static String CACHE_LOCATION = "cached.c";
	private static RequestUtils cached = null;
	
	private static RequestUtils getCache() {
		return (cached==null)? load() : cached;
	}
	
	private HashMap<String, String> map;
	
	private RequestUtils() {map = new HashMap<String,String>();}
	
	public static void save() {
		try {
		JSONObject obj = new JSONObject(getCache().map);
		writeAllText(CACHE_LOCATION,obj.toString());
		}
		catch(Exception e) {
			System.out.println("Caching Failed");
		}
		
		//Files.writeStringToFile(new File("cache.c"), obj.toString());
	}
	
	public static RequestUtils load(){
		
		RequestUtils reqUtil = new RequestUtils();
		try {
		String JSON = readAllText(CACHE_LOCATION);
		JSONObject jsonMap = new JSONObject(JSON);
		
		 Iterator<String> iterator = jsonMap.keys();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			reqUtil.map.put(key, jsonMap.getString(key));
			System.out.println(key);
		}
		}
		catch(Exception e) {
			System.out.println("Failed to parse");
			e.printStackTrace();
		}
		return (cached = reqUtil);
	}
	
	
	
	private static void writeAllText(String path, String text) throws IOException {
		File file = new File(path);
		FileWriter writer = new FileWriter(file);
		writer.write(text);
		writer.close();
		System.out.println("Write: "+text);
	}
	
	private static String readAllText(String path) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(path));
		
		StringBuilder built = new StringBuilder();
		built.append(bf.readLine());
		bf.close();
		System.out.println(built.toString());
		return built.toString();
	}
	
	
	public static String sendGet(String url, String apiKey) throws Exception {
		
		RequestUtils req = getCache();
		
		if(!req.map.containsKey(url)) {
			
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
       
        //System.out.println("Response Code : " + responseCode);

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

            req.map.put(url,response.toString());
            req.save();
           return response.toString();
        }
        }
		else {
        	return req.map.get(url);
        }

    }
	
	public static String sendGet(String url) throws Exception {
		return sendGet(url, null);

    }
}
