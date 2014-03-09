package com.esf.service.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Date date = new Date();  
		   
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");  
		formatter.setTimeZone(TimeZone.getTimeZone("CET"));  
		   
		// Prints the date in the CET timezone  
		System.out.println(formatter.format(date));  
		   
		// Set the formatter to use a different timezone  
		formatter.setTimeZone(TimeZone.getTimeZone("IST"));  
		   
		// Prints the date in the IST timezone  
		System.out.println(formatter.format(date)); 
		
		String createDateIST=formatter.format(date).toString();
		System.out.println(createDateIST); 
		

	}

}
