package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import java.sql.Date;
import java.sql.Timestamp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuriontech.web.test.david.zurionwebapi.db.DBConnection;
import com.zuriontech.web.test.david.zurionwebapi.db.DBRead;
import com.zuriontech.web.test.david.zurionwebapi.db.DBUpdate;
import com.zuriontech.web.test.david.zurionwebapi.db.StampUtils;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;


@WebServlet(
		asyncSupported = true, 
		description = "For Updating AdvancedContact", 
		urlPatterns = { "/sudo/update/advancedcontact/*"})
public class SvAdvancedContactUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvAdvancedContactUpdate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int entityID = Integer.parseInt(StampUtils.getResourceID(request));

		AdvancedContact a = null;

		Connection con = DBConnection.createConnection();
		String error = "";

		try {
			a = DBRead.getAdvancedContact(con, entityID);
		} catch (SQLException ex) {
			ex.printStackTrace();
			error = ex.getLocalizedMessage();
		} finally {
			DBConnection.closeConnection(con);
		}

		request.setAttribute("error", error);
		request.setAttribute("AdvancedContact", a);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ADVANCEDCONTACT_UPDATE.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int entityID = Integer.parseInt(StampUtils.getResourceID(request));

		int contactId = Integer.parseInt(request.getParameter("contactId"));
		String fullName = request.getParameter("fullName");
		String maskedName = request.getParameter("maskedName");
		String phone = request.getParameter("phone");
		String phoneMask = request.getParameter("phoneMask");
		String phoneHash = request.getParameter("phoneHash");
		String email = request.getParameter("email");
		int idNumber = Integer.parseInt(request.getParameter("idNumber"));
		Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
		String gender = request.getParameter("gender");
		String organization = request.getParameter("organization");
		Timestamp dateCreated = new Timestamp(System.currentTimeMillis());	//Timestamp.valueOf(request.getParameter("dateCreated"));
		String comments = request.getParameter("comments");
		String status = request.getParameter("status");


		Connection con = DBConnection.createConnection();
		AdvancedContact a = null;

		try {
			a = DBRead.getAdvancedContact(con, contactId);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		a.setContactId(contactId);
		a.setFullName(fullName);
		a.setMaskedName(maskedName);
		a.setPhone(phone);
		a.setPhoneMask(phoneMask);
		a.setPhoneHash(phoneHash);
		a.setEmail(email);
		a.setIdNumber(idNumber);
		a.setDateOfBirth(dateOfBirth);
		a.setGender(gender);
		a.setOrganization(organization);
		a.setDateCreated(dateCreated);
		a.setComments(comments);
		a.setStatus(status);
		String error = "";

		try {
			DBUpdate.updateAdvancedContact(con, a);
		} catch (SQLException ex) {
			ex.printStackTrace();
			error = ex.getLocalizedMessage();
		} finally {
			DBConnection.closeConnection(con);
		}

		if(request.getParameter("submitMode") != null) {
			response.setContentType("text/html");	
			if(!error.equals("")) {
				request.setAttribute("error", error);		
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ADVANCEDCONTACT_UPDATE.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(request.getServletContext().getContextPath() + "/advancedcontact/" + a.getContactId());
				
			}
		} else { //There's no direct form submission indicated
			response.setContentType("text/plain");	
			if(!error.equals("")) {
				response.getWriter().append("UPDATE_FAILED");
			} else {
				response.getWriter().append("UPDATE_OKAY");
			}
		}
	}
}

