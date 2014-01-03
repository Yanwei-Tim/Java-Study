/**
 * 
 */
package com.ssi.dao;

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
		int retId = 0;
		try {
			sqlMapClient2.startTransaction();
			sqlMapClient.startTransaction();
			
			int lastId = (int) sqlMapClient2.insert("Contact.insert", contact);
			System.out.println("insert lastId:" + lastId);
			
			retId = (int) sqlMapClient.insert("Contact.insert", contact);
			
			sqlMapClient.commitTransaction();
			sqlMapClient2.commitTransaction();
		} catch (SQLException e) {
			System.out.println("insert exception:" + e);
		}finally{
			sqlMapClient.endTransaction();
			sqlMapClient2.endTransaction();
		}
		return retId;
	}
	
	public List<Map> selectAll() throws Exception {
		return sqlMapClient.queryForList("Contact.getAll");
	}
}
