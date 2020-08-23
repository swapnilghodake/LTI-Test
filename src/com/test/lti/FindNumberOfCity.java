package com.test.lti;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FindNumberOfCity {

	public static void main(String[] args) {

		Logger logger = null;
		try {
			
			FileHandler handler = new FileHandler("default.log", true);			 
			logger = Logger.getLogger(FindNumberOfCity.class.getName());			
			logger.addHandler(handler);	
			
			
					
			HttpClient httpClient = HttpClient.newBuilder().build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
					"https://samples.openweathermap.org/data/2.5/box/city?bbox=12.32.15.37.10&appid=b6907d289e10d714a6e88b30761fae22"))
					.GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			logger.info("Response status code: " + response.statusCode());
			

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonParentObject = (JSONObject) jsonParser.parse(response.body());
			JSONArray jsonArray = (JSONArray) jsonParentObject.get("list");

						
			List<String> listOfCity = new ArrayList<String>();
			
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonChildObject = (JSONObject) jsonArray.get(i);			
				listOfCity.add((String) jsonChildObject.get("name"));
			}
			
			logger.log(Level.INFO, "Count of CityName Starts With T : "+listOfCity.stream().filter(cityName->cityName.startsWith("T")).count());
				
			//System.out.println("------------------"+listOfCity.stream().filter(cityName->cityName.startsWith("T")).count());
			//System.out.println("------------------"+listOfCity.stream().filter(cityName->cityName.startsWith("Z")).count());
		

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}

	}

}
