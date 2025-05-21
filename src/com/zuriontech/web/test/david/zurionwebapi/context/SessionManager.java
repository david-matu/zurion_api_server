package com.zuriontech.web.test.david.zurionwebapi.context;

import javax.servlet.http.HttpSession;

import com.zuriontech.web.test.david.zurionwebapi.model.User;


/**
 * 
 * 	@author David
 *	Dated: Sep 7, 2022
 */
public class SessionManager {
	
	public static void storeLoggedUser(HttpSession session, User user) {
		session.setAttribute("user", user);
	}

	public static User getLoggedUser(HttpSession session) {
		User signedUser = (User) session.getAttribute("user");
		   return signedUser;
	}
}

