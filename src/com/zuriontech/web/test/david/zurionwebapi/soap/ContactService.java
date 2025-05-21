package com.zuriontech.web.test.david.zurionwebapi.soap;

import java.util.List;

import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface ContactService {
    
	@WebMethod
    String sayHello(String name, String organization);
	
	@WebMethod
	public List<AdvancedContact> getContactsByOrganization(@WebParam(name = "organization") String organization);
}