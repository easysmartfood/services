package com.esf.service.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esf.service.constants.EsfConstants;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.net.URL;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GetRestaurant extends RemoteServiceServlet implements IGetRestaurant {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException{
		
          		
		 String urlString = EsfConstants.getRestaurantUrl + EsfConstants.mongoDb1Key;
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
