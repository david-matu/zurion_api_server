package com.zuriontech.web.test.david.zurionwebapi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;


public class DBCreate {

	public static void createAdvancedContact(Connection con, AdvancedContact a) throws SQLException {
		String query = "INSERT INTO advanced_contact(CONTACT_ID, FULL_NAME, MASKED_NAME, PHONE, PHONE_MASK, PHONE_HASH, EMAIL, ID_NUMBER, DATE_OF_BIRTH, GENDER, ORGANIZATION, DATE_CREATED, COMMENTS, STATUS )"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		try (PreparedStatement pst = con.prepareStatement(query);) {
			pst.setInt(1, a.getContactId());
			pst.setString(2, a.getFullName());
			pst.setString(3, a.getMaskedName());
			pst.setString(4, a.getPhone());
			pst.setString(5, a.getPhoneMask());
			pst.setString(6, a.getPhoneHash());
			pst.setString(7, a.getEmail());
			pst.setInt(8, a.getIdNumber());
			pst.setDate(9, a.getDateOfBirth());
			pst.setString(10, a.getGender());
			pst.setString(11, a.getOrganization());
			pst.setTimestamp(12, a.getDateCreated());
			pst.setString(13, a.getComments());
			pst.setString(14, a.getStatus());
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
