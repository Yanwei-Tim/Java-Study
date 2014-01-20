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
public class ContactDao2 {
	
	@Resource(name = "sqlMapClient2")
	protected SqlMapClient sqlMapClient2;
	
	public Map selectById(int id) throws Exception {
		return (Map) sqlMapClient2.queryForObject("Contact.getById", Integer.valueOf(id));
	}

	public int insert(Contact contact) throws Exception {
		return (int) sqlMapClient2.insert("Contact.insert", contact);
	}
	
	public List<Map> selectAll() throws Exception {
		return sqlMapClient2.queryForList("Contact.getAll");
	}
	
	// jdbc 事务实现
	public int testMultiTransaction(Contact contact) throws Exception{
		throw new Exception("接口没有实现");
	}
	
	// jotm事务实现
	public int syncInsert(Contact contact) throws Exception {
		return (int) sqlMapClient2.insert("Contact.insert", contact);
	}
}
