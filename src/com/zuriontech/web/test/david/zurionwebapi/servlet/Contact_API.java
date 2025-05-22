package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
import com.zuriontech.web.test.david.zurionwebapi.util.AppUtils;
import com.zuriontech.web.test.david.zurionwebapi.util.SqlDateAdapter;

@WebServlet("/api/contact")
public class Contact_API extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // private static final Gson gson = new Gson();
	private static final Gson gson = new GsonBuilder()
		    .registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
		    .create();
	
	private static final Gson gsonDateAdapt = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	
	public Contact_API() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Received API request at Contact");
		
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
	    AdvancedContact contact = gsonDateAdapt.fromJson(request.getReader(), AdvancedContact.class);
	   
	    System.out.println("Create Contact received on API: " + contact.toString());
	    
	    Timestamp dateCreated = new Timestamp(System.currentTimeMillis());	//Timestamp.valueOf(request.getParameter("dateCreated"));
	    String maskedName = AppUtils.maskName(contact.getFullName());
		String phoneMask = AppUtils.maskPhoneNumber(contact.getPhone());
		String phoneHash = AppUtils.hashPhoneNumber(contact.getPhone());
		
		contact.setDateCreated(dateCreated);
		contact.setMaskedName(maskedName);
		contact.setPhoneMask(phoneMask);
		contact.setPhoneHash(phoneHash);
		contact.setStatus("ACTIVE");
		
	    // 2. Validate and insert into DB
	    try (Connection con = DBConnection.createConnection()) {
	        DBCreate.createAdvancedContact(con, contact);
	        response.setStatus(HttpServletResponse.SC_CREATED); // If we tune the DBCreate method, we can get success feedback for this to be: HttpServletResponse.SC_BAD_REQUEST);
	    } catch (SQLException e) {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	    }
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    AdvancedContact contact = gson.fromJson(request.getReader(), AdvancedContact.class);
	    
	    try (Connection con = DBConnection.createConnection()) {
	        DBUpdate.updateAdvancedContact(con, contact); 
	        response.setStatus(HttpServletResponse.SC_OK);	// : HttpServletResponse.SC_NOT_FOUND);
	    } catch (SQLException e) {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	    }
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idParam = request.getParameter("id");
	    if (idParam == null) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing contact ID");
	        return;
	    }
	    
	    Entity e = new Entity();
	    e.setTableName("advanced_contact");
	    e.setColumn("CONTACT_ID");
	    e.setEntityID(Long.parseLong(idParam));
	    
	    try (Connection con = DBConnection.createConnection()) {
	        boolean deleted = DBUpdate.deleteResource(con, e);
	        response.setStatus(deleted ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_NOT_FOUND);
	    } catch (SQLException ex) {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
	    }
	}
}

