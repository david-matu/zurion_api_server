package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

//@WebService(serviceName = "/MyWebService")
// @WebServlet("/MyWebService")
public class Contact_SOAP_Service_Dummy extends HttpServlet {
	
	@WebMethod
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Process the SOAP request
            SOAPMessage soapRequest = MessageFactory.newInstance().createMessage(null, request.getInputStream());
            SOAPBody soapBody = soapRequest.getSOAPBody();

            // Extract data from the SOAP request
            String name = soapBody.getElementsByTagName("organization").item(0).getTextContent();

            // Invoke the service method
            String result = sayHello(name);

            // Create the SOAP response
            SOAPMessage soapResponse = MessageFactory.newInstance().createMessage();
            SOAPBody responseBody = soapResponse.getSOAPBody();
            SOAPElement returnElement = responseBody.addChildElement("return");
            returnElement.setTextContent(result);

            // Send the SOAP response
            response.setContentType("text/xml");
            soapResponse.writeTo(response.getOutputStream());

        } catch (SOAPException e) {
            throw new ServletException("Error processing SOAP request", e);
        }
    }
}
