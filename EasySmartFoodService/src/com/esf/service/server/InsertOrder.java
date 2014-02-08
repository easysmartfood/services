package com.esf.service.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esf.service.constants.EsfConstants;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class InsertOrder extends HttpServlet implements IInsertOrder {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws IOException {
		
		InputStream body = request.getInputStream();
		String bodyString = getStringFromInputStream(body);
		System.out.println("Input Data : " + bodyString);
		
		UUID orderUuid = UUID.randomUUID();
		
		
		
		String postContent= "{\"orderuuid\":\""  + orderUuid.toString() + "\"," +  "\"orderdata\":" +  bodyString + "}"; 
		System.out.println("Post Content : " + postContent);
		
		String urlString = EsfConstants.insertOrderUrl + EsfConstants.mongoDb2Key;
		System.out.println("Url : " + urlString);

		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(postContent);
        writer.close();
        String responseString = "";
        System.out.println("response code:" + connection.getResponseCode());
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	responseString="{\"orderuuid\":\"" + orderUuid.toString() + "\"}";
        }
        else
        {
        	responseString="{\"response\":\"error\"}";
        }

		response.setContentType("application/json");
		response.getWriter().println(responseString);
	}

	// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {
	 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
	 

}
