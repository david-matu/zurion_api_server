package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuriontech.web.test.david.zurionwebapi.db.DBConnection;
import com.zuriontech.web.test.david.zurionwebapi.db.DBCreate;
import com.zuriontech.web.test.david.zurionwebapi.db.StampUtils;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;
import com.zuriontech.web.test.david.zurionwebapi.util.AppUtils;


@WebServlet(
		asyncSupported = true, 
		description = "Creates a new AdvancedContact", 
		urlPatterns = { "/sudo/create/advancedcontact"})
public class SvAdvancedContactCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvAdvancedContactCreate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ADVANCEDCONTACT_ADD.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullName = request.getParameter("fullName");
		// String maskedName = request.getParameter("maskedName");
		String phone = request.getParameter("phone");
		// String phoneMask = request.getParameter("phoneMask");
		// String phoneHash = request.getParameter("phoneHash");
		String email = request.getParameter("email");
		int idNumber = Integer.parseInt(request.getParameter("idNumber"));
		Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
		String gender = request.getParameter("gender");
		String organization = request.getParameter("organization");
		Timestamp dateCreated = new Timestamp(System.currentTimeMillis());	//Timestamp.valueOf(request.getParameter("dateCreated"));
		String comments = request.getParameter("comments");
		String status = request.getParameter("status");

		
		String maskedName = AppUtils.maskName(fullName);
		String phoneMask = AppUtils.maskPhoneNumber(phone);
		String phoneHash = AppUtils.hashPhoneNumber(phone);

		Connection con = DBConnection.createConnection();
		AdvancedContact a = new AdvancedContact();
		// a.setContactId(StampUtils.generateUniqueContactId(con));
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
		con = DBConnection.createConnection();	//Since connection is closed by above CodeUtil

		String error = "";

		try {
			DBCreate.createAdvancedContact(con, a);
		} catch (SQLException ex) {
			ex.printStackTrace();
			error = ex.getLocalizedMessage();
		} finally {
			DBConnection.closeConnection(con);
		}

		if(request.getParameter("submitMode") != null) {
			response.setContentType("text/html");
			String submitMode = request.getParameter("submitMode");
			
			if(submitMode.equals("formSubmittted")) {	
				if(!error.equals("")) {
					request.setAttribute("error", error);		
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ADVANCEDCONTACT_ADD.jsp");
					dispatcher.forward(request, response);
				} else {
					response.sendRedirect(request.getServletContext().getContextPath() + "/advancedcontact/" + a.getContactId());
				}
			} 
			
			else if(submitMode.equals("ajaxx")) {
				response.setContentType("text/plain");
				if(!error.equals("")) {
					response.getWriter().write("FAILED");
				} else {
					response.getWriter().write("SUCCESS");
				}
			}
		}
	}
}

