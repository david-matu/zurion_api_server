package com.zuriontech.web.test.david.zurionwebapi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;

public class DBRead {

	public static AdvancedContact getAdvancedContact(Connection con, int id) throws SQLException {
		String query = "SELECT * FROM advanced_contact WHERE CONTACT_ID = ?";
		try (PreparedStatement pst = con.prepareStatement(query);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				AdvancedContact a = new AdvancedContact();
				a.setContactId(rs.getInt("CONTACT_ID"));
				a.setFullName(rs.getString("FULL_NAME"));
				a.setMaskedName(rs.getString("MASKED_NAME"));
				a.setPhone(rs.getString("PHONE"));
				a.setPhoneMask(rs.getString("PHONE_MASK"));
				a.setPhoneHash(rs.getString("PHONE_HASH"));
				a.setEmail(rs.getString("EMAIL"));
				a.setIdNumber(rs.getInt("ID_NUMBER"));
				a.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
				a.setGender(rs.getString("GENDER"));
				a.setOrganization(rs.getString("ORGANIZATION"));
				a.setDateCreated(rs.getTimestamp("DATE_CREATED"));
				a.setComments(rs.getString("COMMENTS"));
				a.setStatus(rs.getString("STATUS"));
				return a;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<AdvancedContact> getAdvancedContactList(Connection con) throws SQLException {
		String query = "SELECT * FROM advanced_contact";
		List<AdvancedContact> aList = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();) {
			while(rs.next()) {
				AdvancedContact a = new AdvancedContact();
				a.setContactId(rs.getInt("CONTACT_ID"));
				a.setFullName(rs.getString("FULL_NAME"));
				a.setMaskedName(rs.getString("MASKED_NAME"));
				a.setPhone(rs.getString("PHONE"));
				a.setPhoneMask(rs.getString("PHONE_MASK"));
				a.setPhoneHash(rs.getString("PHONE_HASH"));
				a.setEmail(rs.getString("EMAIL"));
				a.setIdNumber(rs.getInt("ID_NUMBER"));
				a.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
				a.setGender(rs.getString("GENDER"));
				a.setOrganization(rs.getString("ORGANIZATION"));
				a.setDateCreated(rs.getTimestamp("DATE_CREATED"));
				a.setComments(rs.getString("COMMENTS"));
				a.setStatus(rs.getString("STATUS"));

				aList.add(a);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return aList;
	}

	public static List<AdvancedContact> getAdvancedContactListByOrganization(Connection con, String organization) throws SQLException {
		String query = "SELECT * FROM advanced_contact WHERE ORGANIZATION = ?";
		List<AdvancedContact> aList = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(query)) {
			// pst.setString(1, "%" + organization + "%");
			pst.setString(1, organization);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				AdvancedContact a = new AdvancedContact();
				a.setContactId(rs.getInt("CONTACT_ID"));
				a.setFullName(rs.getString("FULL_NAME"));
				a.setMaskedName(rs.getString("MASKED_NAME"));
				a.setPhone(rs.getString("PHONE"));
				a.setPhoneMask(rs.getString("PHONE_MASK"));
				a.setPhoneHash(rs.getString("PHONE_HASH"));
				a.setEmail(rs.getString("EMAIL"));
				a.setIdNumber(rs.getInt("ID_NUMBER"));
				a.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
				a.setGender(rs.getString("GENDER"));
				a.setOrganization(rs.getString("ORGANIZATION"));
				a.setDateCreated(rs.getTimestamp("DATE_CREATED"));
				a.setComments(rs.getString("COMMENTS"));
				a.setStatus(rs.getString("STATUS"));

				aList.add(a);
				
				System.out.println("Added the following contact belonging to " + organization + "\n" + a.toString());
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return aList;
	}

	public static AdvancedContact searchAdvancedContact(Connection con, String searchTerm) {
		String query = "SELECT * FROM advanced_contact WHERE PHONE_HASH = \"" + searchTerm + "\"";
		
		if (searchTerm.contains(",") && searchTerm.split(",").length == 2) {
			String[] parts = searchTerm.split(",");
	        String maskedName = parts[0].trim();
	        String maskedPhone = parts[1].trim();
	        
			query = "SELECT * FROM advanced_contact WHERE MASKED_NAME = \"" +  maskedName + "\" AND PHONE_MASK = \"" + maskedPhone + "\"";
		} else {
			
		}
		// String query = "SELECT * FROM advanced_contact WHERE PHONE_HASH = ?";
		try (PreparedStatement pst = con.prepareStatement(query);) {
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				AdvancedContact a = new AdvancedContact();
				a.setContactId(rs.getInt("CONTACT_ID"));
				a.setFullName(rs.getString("FULL_NAME"));
				a.setMaskedName(rs.getString("MASKED_NAME"));
				a.setPhone(rs.getString("PHONE"));
				a.setPhoneMask(rs.getString("PHONE_MASK"));
				a.setPhoneHash(rs.getString("PHONE_HASH"));
				a.setEmail(rs.getString("EMAIL"));
				a.setIdNumber(rs.getInt("ID_NUMBER"));
				a.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
				a.setGender(rs.getString("GENDER"));
				a.setOrganization(rs.getString("ORGANIZATION"));
				a.setDateCreated(rs.getTimestamp("DATE_CREATED"));
				a.setComments(rs.getString("COMMENTS"));
				a.setStatus(rs.getString("STATUS"));
				return a;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
