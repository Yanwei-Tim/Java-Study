/**
 * 
 */
package com.ssi.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ssi.bean.Contact;

/**
 * @author dh
 *
 */
@Repository
public class ContactDao extends SqlMapClientUtil {
	
	@Resource(name = "sqlMapClient2")
	protected SqlMapClient sqlMapClient2;
	
	public Map selectById(int id) throws Exception {
		return (Map) sqlMapClient.queryForObject("Contact.getById", Integer.valueOf(id));
	}

	public int insert(Contact contact) throws Exception {
		return (int) sqlMapClient.insert("Contact.insert", contact);
	}
	
	public List<Map> selectAll() throws Exception {
		return sqlMapClient.queryForList("Contact.getAll");
	}
	
	// jdbc 事务实现
	public int testMultiTransaction(Contact contact) throws Exception{
		int retId = 0;
		Connection conn2 = sqlMapClient2.getDataSource().getConnection();
		Connection conn = sqlMapClient.getDataSource().getConnection();
		boolean flag2 = conn2.getAutoCommit();
		boolean flag = conn.getAutoCommit();
		try {
			conn.setAutoCommit(false);
			conn2.setAutoCommit(false);
			sqlMapClient2.setUserConnection(conn2);
			sqlMapClient.setUserConnection(conn);
			
			int lastId = (int) sqlMapClient2.insert("Contact.insert", contact);
			System.out.println("insert lastId:" + lastId);
			
			retId = (int) sqlMapClient.insert("Contact.insert", contact);
			
			conn.commit();
			conn2.commit();
		} catch (SQLException e) {
			System.out.println("insert exception:" + e);
			conn.rollback();
			conn2.rollback();
		}finally{
			conn.setAutoCommit(flag);
			conn2.setAutoCommit(flag2);
		}
		return retId;
	}
	
	// jotm事务实现
	/*
	public int syncInsert(Contact contact) throws Exception {
		int lastId = (int) sqlMapClient.insert("Contact.insert", contact);
		System.out.println("---------insert success return:---------" +  lastId);
		
		Contact obj = new Contact();
		obj.setEmail(null);
		obj.setName(contact.getName());
		obj.setPhone(contact.getPhone());
		
		lastId = (int) sqlMapClient2.insert("Contact.insert", obj);
		return lastId;
	}
	 */
	
	public int syncInsert(Contact contact) throws Exception {
		return (int) sqlMapClient.insert("Contact.insert", contact);
	}

}
