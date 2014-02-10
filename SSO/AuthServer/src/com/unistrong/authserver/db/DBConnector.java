package com.unistrong.authserver.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnector {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public Connection getConn(){
		Context ctx = null;   //JNDI
		DataSource ds = null;
		try {
			ctx = new InitialContext();//JNDI
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/mysqlds");//其中java:/comp/env/指明tomact应用容器使用
			conn = ds.getConnection();
			System.out.println("成功获取连接！");
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn ;
	 }
	
	public PreparedStatement getPstmt(String sqlStr) throws SQLException{
		if(conn==null || conn.isClosed()){
			conn = getConn();
		}
		pstmt = conn.prepareStatement(sqlStr);
		return pstmt;
	}
	
	public ResultSet getRs() throws SQLException{
		rs = pstmt.executeQuery();
		return rs ;
	}
	
	public void closeAll() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("已成功关闭连接！");
	}

}
