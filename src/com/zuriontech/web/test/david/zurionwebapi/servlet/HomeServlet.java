package com.zuriontech.web.test.david.zurionwebapi.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		asyncSupported = true, 
		description = "Serves Home (Welcome) Page", 
		urlPatterns = { "/home", "/index", "/random"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
		
	Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();
		
		String allServlets = "";
		
		for(Map.Entry<String, ? extends ServletRegistration> entry : servletRegistrations.entrySet()) {
			allServlets +=  Arrays.toString(entry.getValue().getMappings().toArray()) +  " :::::::::::::::::::::::::::::::::::::::: " + entry.getKey() + "\n\n <br>";
		}
		
		
		request.setAttribute("endpoints", allServlets);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/admin/INDEX.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
