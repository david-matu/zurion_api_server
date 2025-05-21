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
import com.zuriontech.web.test.david.zurionwebapi.db.DBCreate;
import com.zuriontech.web.test.david.zurionwebapi.db.DBRead;
import com.zuriontech.web.test.david.zurionwebapi.db.DBUpdate;
import com.zuriontech.web.test.david.zurionwebapi.db.StampUtils;
import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;
import com.zuriontech.web.test.david.zurionwebapi.model.Entity;

@WebServlet(
		asyncSupported = true, 
		description = "Delete AdvancedContact", 
		urlPatterns = { "/sudo/advancedcontact/delete/*"})
public class SvAdvancedContactDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvAdvancedContactDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<AdvancedContact> aList = new ArrayList<>();

		Connection con = DBConnection.createConnection();
		String error = "";

		try {
			aList = DBRead.getAdvancedContactList(con);  //SessionManager.getLoggedUser(request.getSession()).getUserId()
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
		int contactID = Integer.parseInt(StampUtils.getResourceID(request));
		
		Connection con = DBConnection.createConnection();

		String error = "";
		
		Entity et = new Entity();
		et.setEntityID(contactID);
		et.setTableName("advanced_contact");
		et.setColumn("CONTACT_ID");			//The matching criteria pattern, mostly primary key
		
		boolean isDeleted = false;
		try {
			isDeleted = DBUpdate.deleteResource(con, et);
		} catch (SQLException ex) {
			ex.printStackTrace();
			error = ex.getLocalizedMessage();
		} finally {
			DBConnection.closeConnection(con);
		}
		
		//Assuming the request is AJAX, return text response
		response.setContentType("text/html");
		if(isDeleted == false || error!= "") {
			response.getWriter().append("DEL_FAILED");
		} else {
			//response.sendRedirect(request.getServletContext().getContextPath() + "/contact/" + c.getContactId());
			response.getWriter().append("DEL_OKAY");
		}
	}
}


