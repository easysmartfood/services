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
public class GetFoodItems extends RemoteServiceServlet implements IGetRestaurant {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException{
		
	     String restaurantUuid = request.getParameter(EsfConstants.restaurantuuid); 
	     
	     System.out.println("restaurnatuuid : " + restaurantUuid);
	     
	     String searchRestaurantJson = "{\"restaurantUuid\":\"" + restaurantUuid + "\"}";
	    
	  //   System.out.println("searchRestaurantJson : " + searchRestaurantJson);
	     
		// String searchRestaurantJson = "{\"restaurantUuid\":\"ad45f90a-8b84-11e3-ae4d-d231feb1dc81\"}"; 
		
         String searchRestaurantJsonUTF = URLEncoder.encode(searchRestaurantJson, "UTF-8");
         
		 String urlString = EsfConstants.getFoodItemsUrl + "q=" + searchRestaurantJsonUTF + "&" + EsfConstants.mongoDb1Key;
		 
		 System.out.println("urlString : " + urlString);
		 
		 URL url = new URL(urlString);
		 
		 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		 String line;
	     String responseString = "";
		 while ((line = reader.readLine()) != null) {
		        responseString = responseString + line;
		    }
		reader.close();

        response.setContentType("application/json");

		response.getWriter().println(responseString);


	}
	
	

}
