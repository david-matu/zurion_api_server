package com.zuriontech.web.test.david.zurionwebapi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zuriontech.web.test.david.zurionwebapi.model.AdvancedContact;
import com.zuriontech.web.test.david.zurionwebapi.model.Entity;

public class DBUpdate {

	public static void updateAdvancedContact(Connection con, AdvancedContact a) throws SQLException {
		String query = "UPDATE advanced_contact SET CONTACT_ID=?, FULL_NAME=?, MASKED_NAME=?, PHONE=?, PHONE_MASK=?, PHONE_HASH=?, EMAIL=?, ID_NUMBER=?, DATE_OF_BIRTH=?, GENDER=?, ORGANIZATION=?, DATE_CREATED=?, COMMENTS=?, STATUS=? WHERE CONTACT_ID=?";

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
			pst.setInt(15, a.getContactId());
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}


	/** ******************************************************************************************************************************************
	 * 	DELETE A RESOURCE
	 * 	******************************************************************************************************************************************
	 */
	public static boolean deleteResource(Connection con, Entity e) throws SQLException {			
		String sql = "DELETE FROM " + e.getTableName() + " WHERE " + e.getColumn() + " = ?";
		
		try (PreparedStatement pst = con.prepareStatement(sql);){
			//pst.setString(1, e.getTableName());
			//pst.setString(2, e.getColumn());
			pst.setLong(1, e.getEntityID());	//Or just pass entityID
			
			//System.out.println(sql);			
			pst.executeUpdate();
			return true;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}}
