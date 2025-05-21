package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuriontech.web.test.david.zurionwebapi.db.DBConnection;
import com.zuriontech.web.test.david.zurionwebapi.db.DBCreate;
import com.zuriontech.web.test.david.zurionwebapi.db.DBRead;
import com.zuriontech.web.test.david.zurionwebapi.db.StampUtils;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;


@WebServlet(
		asyncSupported = true, 
		description = "Read single AdvancedContact", 
		urlPatterns = { "/sudo/show/advancedcontact/*"})
public class SvAdvancedContactShow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvAdvancedContactShow() {
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

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ADVANCEDCONTACT_VIEW.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}

