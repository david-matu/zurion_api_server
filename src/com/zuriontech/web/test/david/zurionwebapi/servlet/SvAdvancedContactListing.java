package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuriontech.web.test.david.zurionwebapi.db.DBConnection;
import com.zuriontech.web.test.david.zurionwebapi.db.DBRead;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;


@WebServlet(
		asyncSupported = true, 
		description = "Read List of AdvancedContact", 
		urlPatterns = { "/", "/sudo/listing/advancedcontact"})
public class SvAdvancedContactListing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvAdvancedContactListing() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

		request.setAttribute("error", error);
		request.setAttribute("aList", aList);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ADVANCEDCONTACT_LIST.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}

