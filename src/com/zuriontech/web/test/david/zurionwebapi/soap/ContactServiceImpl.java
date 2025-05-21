package com.zuriontech.web.test.david.zurionwebapi.soap;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zuriontech.web.test.david.zurionwebapi.db.DBConnection;
import com.zuriontech.web.test.david.zurionwebapi.db.DBRead;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(endpointInterface = "com.zuriontech.web.test.david.zurionwebapi.soap.ContactService")
public class ContactServiceImpl implements ContactService {
	
	@Override
	public String sayHello(@WebParam(name = "name") String name, @WebParam(name = "organization") String organization) {
		return "Hello, " + name + " at " + organization + "! Welcome to Zurion Contact Registry";
	}

	@Override
	public List<AdvancedContact> getContactsByOrganization(String organization) {
		System.out.println("Processing soap request for organization: " + organization);
		
		List<AdvancedContact> aList = new ArrayList<>();
	    Connection con = DBConnection.createConnection();

	    try {
	        // Replace this with a name-filtering version of your DB call
	        aList = DBRead.getAdvancedContactListByOrganization(con, organization); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBConnection.closeConnection(con);
	    }
	    

	    return aList;
	}
}
