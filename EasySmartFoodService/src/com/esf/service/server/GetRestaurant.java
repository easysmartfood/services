package com.esf.service.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esf.service.constants.EsfConstants;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.net.URL;
import java.net.URLEncoder;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GetRestaurant extends RemoteServiceServlet implements IGetRestaurant {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException{
		
		String region = request.getParameter(EsfConstants.region);
		String restaurantUuid = request.getParameter(EsfConstants.restaurantuuid); 

		String searchRegionJson = "";
		String searchRegionJsonUTF;
		String searchRestaurantJson = "";
		String searchRestaurantJsonUTF;
		String urlString;
		if(region!=null)
		{
			searchRegionJson = "{\"region\":\"" + region + "\"}";
			searchRegionJsonUTF = URLEncoder.encode(searchRegionJson, "UTF-8");
			urlString = EsfConstants.getRestaurantUrl + "q=" + searchRegionJsonUTF + "&" + EsfConstants.mongoDb1Key;
		}
		else if(restaurantUuid!=null)
		{
			searchRestaurantJson = "{\"uuid\":\"" + restaurantUuid + "\"}";
			searchRestaurantJsonUTF = URLEncoder.encode(searchRestaurantJson, "UTF-8");
			urlString = EsfConstants.getRestaurantUrl + "q=" + searchRestaurantJsonUTF + "&" + EsfConstants.mongoDb1Key;
		    System.out.println(urlString);
		}
		else
		{
			urlString = EsfConstants.getRestaurantUrl + EsfConstants.mongoDb1Key;
		}
		

 
          		
	
		 URL url = new URL(urlString);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		 String line;
	     String responseString = "";
		 while ((line = reader.readLine()) != null) {
		        responseString = responseString + line;
		    }
		reader.close();

        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");

		response.getWriter().println(responseString);


	}
	
	

}
