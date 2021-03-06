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
import com.esf.service.databeans.BeanId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


//import com.google.common.util.Base64;
//import com.google.common.util.Base64DecoderException;


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
		
		Date date = new Date();  
		   
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");  
		formatter.setTimeZone(TimeZone.getTimeZone("IST"));  
		String createDateIST=formatter.format(date).toString();
		System.out.println(createDateIST); 
		
		
		String postContent= "{\"orderUuid\":\""  + orderUuid.toString() + "\"," + 
				"\"createDate\":\""  + createDateIST + "\"," + 
				"\"orderdata\":" +  bodyString + "}"; 
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
        	responseString="{\"orderUuid\":\"" + orderUuid.toString() + "\"}";
        }
        else
        {
        	responseString="{\"response\":\"error\"}";
        }

		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
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
		
		public void doOptions(HttpServletRequest request, HttpServletResponse response)
		{
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			response.addHeader("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE");
	
		}
			
		public void doPut(HttpServletRequest request, HttpServletResponse response)
			      throws IOException {
			
			System.out.println("Put");
			InputStream body = request.getInputStream();
			String bodyString = getStringFromInputStream(body);
			System.out.println("Input Data : " + bodyString);
			
			String postContent= "{\"$set\":{" + "\"orderdata\":" + bodyString + "}}"; 
			System.out.println("Post Content : " + postContent);
			
			String orderUuid = request.getParameter(EsfConstants.orderuuid); 
		     
		     System.out.println("orderuuid : " + orderUuid);
		     
		     String searchOrderJson = "{\"orderUuid\":\"" + orderUuid + "\"}";
		    
		  //   System.out.println("searchRestaurantJson : " + searchRestaurantJson);
		     
			// String searchRestaurantJson = "{\"restaurantUuid\":\"ad45f90a-8b84-11e3-ae4d-d231feb1dc81\"}"; 
			
	         String searchOrderJsonUTF = URLEncoder.encode(searchOrderJson, "UTF-8");
	         
			 String urlString = EsfConstants.orderUrl + "q=" + searchOrderJsonUTF + "&" + EsfConstants.mongoDb2Key;
			 
			 System.out.println("urlString : " + urlString);
			

			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("PUT");
	        connection.setRequestProperty("Content-Type","application/json");
	        
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	        writer.write(postContent);
	        writer.close();
	        String responseString = "";
	        System.out.println("response code:" + connection.getResponseCode());
	        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	        	responseString="{\"orderUuid\":\"" + orderUuid.toString() + "\"}";
	        }
	        else
	        {
	        	responseString="{\"response\":\"error\"}";
	        }

			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
	//		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			response.getWriter().println(responseString);
			
			
			
		}
		
		public void doDelete(HttpServletRequest request, HttpServletResponse response)
			      throws IOException {
			
			System.out.println("Delete");
			String orderUuid = request.getParameter(EsfConstants.orderuuid); 
		     
		     System.out.println("orderuuid : " + orderUuid);
		     
		     String searchOrderJson = "{\"orderUuid\":\"" + orderUuid + "\"}";
		    
		  //   System.out.println("searchRestaurantJson : " + searchRestaurantJson);
		     
			// String searchRestaurantJson = "{\"restaurantUuid\":\"ad45f90a-8b84-11e3-ae4d-d231feb1dc81\"}"; 
			
	         String searchOrderJsonUTF = URLEncoder.encode(searchOrderJson, "UTF-8");
	         
			 String urlString = EsfConstants.orderUrl + "q=" + searchOrderJsonUTF + "&" + EsfConstants.mongoDb2Key;
			 
			 System.out.println("urlString : " + urlString);
				 
				 URL url = new URL(urlString);
				 
				 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				 String line;
			     String responseString = "";
				 while ((line = reader.readLine()) != null) {
				        responseString = responseString + line;
				    }
				reader.close();

				Gson gson = new Gson();
				JsonElement jelem = gson.fromJson(responseString, JsonElement.class);
				JsonArray jsonArray = jelem.getAsJsonArray();
				JsonElement jelem1 = jsonArray.get(0);
				JsonObject jobj = jelem1.getAsJsonObject();
				JsonElement jobj1 = jobj.get("_id");
				System.out.println("jObject");
				JsonObject jobj2 = jobj1.getAsJsonObject();
				
				
				String idString = jobj2.toString();
				
				BeanId idBean = gson.fromJson(idString, BeanId.class);
				
				System.out.println("idString:" + idBean.get$oid());
				
				String deleteUrlString = EsfConstants.deleteOrderUrl + idBean.get$oid() + "?" +  EsfConstants.mongoDb2Key;
				URL durl = new URL(deleteUrlString);
				
				HttpURLConnection connection = (HttpURLConnection) durl.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestMethod("DELETE");
		        connection.setRequestProperty("Content-Type","application/json");
		        
		        String deleteContent = "";
		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		        writer.write(deleteContent);
		        writer.close();
		        String deleteResponseString = "";
		        System.out.println("response code:" + connection.getResponseCode());
		        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		        	deleteResponseString="{\"deletedOrderUuid\":\"" + orderUuid.toString() + "\"}";
		        }
		        else
		        {
		        	deleteResponseString="{\"response\":\"error\"}";
		        }

				response.setContentType("application/json");
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.getWriter().println(deleteResponseString);
					
				
		}
		
		public void doGet(HttpServletRequest request, HttpServletResponse response)
			      throws IOException {
			
			System.out.println("Get");
			String orderUuid = request.getParameter(EsfConstants.orderuuid); 
		     
		     System.out.println("orderuuid : " + orderUuid);
		     
		     String searchOrderJson = "{\"orderUuid\":\"" + orderUuid + "\"}";
		    
		  //   System.out.println("searchRestaurantJson : " + searchRestaurantJson);
		     
			// String searchRestaurantJson = "{\"restaurantUuid\":\"ad45f90a-8b84-11e3-ae4d-d231feb1dc81\"}"; 
			
	         String searchOrderJsonUTF = URLEncoder.encode(searchOrderJson, "UTF-8");
	         
			 String urlString = EsfConstants.orderUrl + "q=" + searchOrderJsonUTF + "&" + EsfConstants.mongoDb2Key;
			 
			 System.out.println("urlString : " + urlString);
				 
				 URL url = new URL(urlString);
				 
				 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				 String line;
			     String responseString = "";
				 while ((line = reader.readLine()) != null) {
				        responseString = responseString + line;
				    }
				reader.close();

				Gson gson = new Gson();
				String responseStringOrder="";
				JsonElement jelem = gson.fromJson(responseString, JsonElement.class);
				JsonArray jsonArray = jelem.getAsJsonArray();
				if(jsonArray.size()>0)
				{
				  JsonElement jelem1 = jsonArray.get(0);
				  JsonObject jobj = jelem1.getAsJsonObject();
				
				  JsonElement jobj1 = jobj.get("orderdata");
				  System.out.println("jObject");
				  JsonObject jobj2 = jobj1.getAsJsonObject();
				
				  responseStringOrder = jobj2.toString();
				  System.out.println("responseStringOrder:" + responseStringOrder);
			    
				}
				
				else
				{
					responseStringOrder="{\"response\":\"no order found\"}";
				}
		        response.setContentType("application/json");
		        response.addHeader("Access-Control-Allow-Origin", "*");

				response.getWriter().println(responseStringOrder);

		}

}
