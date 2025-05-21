package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zuriontech.web.test.david.zurionwebapi.db.DBConnection;
import com.zuriontech.web.test.david.zurionwebapi.db.DBCreate;
import com.zuriontech.web.test.david.zurionwebapi.db.DBRead;
import com.zuriontech.web.test.david.zurionwebapi.db.DBUpdate;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;
import com.zuriontech.web.test.david.zurionwebapi.model.Entity;
import com.zuriontech.web.test.david.zurionwebapi.model.SearchTerm;
import com.zuriontech.web.test.david.zurionwebapi.util.AppUtils;
import com.zuriontech.web.test.david.zurionwebapi.util.DateAdapter;
import com.zuriontech.web.test.david.zurionwebapi.util.SqlDateAdapter;

@WebServlet("/api/contact/search")
public class Contact_Search_API extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // private static final Gson gson = new Gson();
	/*
	private static final Gson gson = new GsonBuilder()
		    .registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
		    .create();
	*/
	
	Gson gson = new GsonBuilder()
	        .registerTypeAdapter(Date.class, new DateAdapter())
	        .create();
	
	public Contact_Search_API() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Received Search API request at Contact_Search_API handler");
		
		List<AdvancedContact> aList = new ArrayList<>();

		Connection con = DBConnection.createConnection();
		String error = "";

		try {
			aList = DBRead.getAdvancedContactList(con);
		} catch (SQLException e) {
			e.printStackTrace();
			error = e.getLocalizedMessage();
		} finally {
			DBConnection.closeConnection(con);
		}
		
		String json = gson.toJson(aList);
		
		System.out.println("JSON element: " + json);
		
		response.setContentType("APPLICATION/JSON");
		response.getWriter().write(json);
		//return Response.ok(json).build();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 1. Parse request body to AdvancedContact object
	    SearchTerm searchTerm = gson.fromJson(request.getReader(), SearchTerm.class);
	    
	    if(!Objects.isNull(searchTerm.getSearchType())) {
	    	if(searchTerm.getSearchType().equalsIgnoreCase("company")) {
	    		// Borrowed from SOAP endpoint handler
	    		List<AdvancedContact> aList = new ArrayList<>();
	    	    Connection con = DBConnection.createConnection();

	    	    try {
	    	        aList = DBRead.getAdvancedContactListByOrganization(con, searchTerm.getSearchTerm()); 
	    	    } catch (SQLException e) {
	    	        e.printStackTrace();
	    	    } finally {
	    	        DBConnection.closeConnection(con);
	    	    }
	    	    
	    	    response.setStatus(200);
		        response.setContentType("application/json");
		        response.getWriter().write(gson.toJson(aList));
	    	} 
	    	
	    	else { // We are searching based on individual 
	    		AdvancedContact returnContact = null;
	    	    
	    	    try (Connection con = DBConnection.createConnection()) {
	    	        returnContact = DBRead.searchAdvancedContact(con, searchTerm.getSearchTerm());
	    	        //response.setStatus(HttpServletResponse.SC_CREATED); // If we tune the DBCreate method, we can get success feedback for this to be: HttpServletResponse.SC_BAD_REQUEST);
	    	        if(Objects.isNull(returnContact)) {
	    	        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    	        	return;
	    	        }
	    	        
	    	        Gson gs = new Gson();
	    	        
	    	        response.setStatus(200);
	    	        response.setContentType("application/json");
	    	        response.getWriter().write(gson.toJson(returnContact));
	    	    } catch (SQLException e) {
	    	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	    	    }
	    	}
	    }
	}
}

