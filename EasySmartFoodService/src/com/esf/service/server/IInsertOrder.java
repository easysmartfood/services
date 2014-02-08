package com.esf.service.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IInsertOrder {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
}
