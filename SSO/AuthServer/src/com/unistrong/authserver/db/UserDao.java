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
}
