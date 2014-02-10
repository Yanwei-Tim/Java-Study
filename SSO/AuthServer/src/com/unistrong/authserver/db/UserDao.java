package com.unistrong.authserver.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	public boolean checkLogin(String uname, String password){
		boolean rst = false;
		String strSql = "select 1 from user_base where username=? and password=?";
		try {
			PreparedStatement pstmt = Dbconn.getPstmt(strSql);
			pstmt.setString(1, uname);
			pstmt.setString(2, password);
			ResultSet rs = Dbconn.getRs();
			if (rs.next()) {
				rst = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Dbconn.closeAll();
		}
		return rst;
	}
}
