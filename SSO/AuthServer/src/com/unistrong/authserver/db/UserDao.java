package com.unistrong.authserver.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	public boolean checkLogin(String uname, String password){
		boolean ret = false;
		String strSql = "select 1 from user_base where username=? and password=?";
		DBConnector connector = new DBConnector();
		try {
			PreparedStatement pstmt = connector.getPstmt(strSql);
			pstmt.setString(1, uname);
			pstmt.setString(2, password);
			ResultSet rs = connector.getRs();
			if (rs.next()) {
				ret = true;
			}
		} catch (SQLException e) {
			ret = false;
			e.printStackTrace();
		}finally {
			connector.closeAll();
		}
		return ret;
	}
	
	public boolean updateTicket(String uname, String ticket){
		boolean ret = false;
		String strSql = "UPDATE user_base SET ticket=?, ticket_time=? WHERE username=?";
		DBConnector connector = new DBConnector();
		try {
			PreparedStatement pstmt = connector.getPstmt(strSql);
			pstmt.setString(1, ticket);
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			pstmt.setDate(2, sqlDate);
			pstmt.setString(3, uname);
			ret = (pstmt.executeUpdate()>0);
		} catch (SQLException e) {
			ret = false;
			e.printStackTrace();
		}finally {
			connector.closeAll();
		}
		return ret;
	}
}
