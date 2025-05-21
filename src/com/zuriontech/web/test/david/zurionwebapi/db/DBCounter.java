package com.zuriontech.web.test.david.zurionwebapi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBCounter {

	public static int countAdvancedContact(Connection con, int contactId) throws SQLException {
		String query = "SELECT COUNT(*) FROM advanced_contact WHERE CONTACT_ID = ?";
		
		int result = 0;

		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setInt(1, contactId);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
