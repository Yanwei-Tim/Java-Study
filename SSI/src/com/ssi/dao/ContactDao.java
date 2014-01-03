/**
 * 
 */
package com.ssi.dao;

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
		int lastId = (int) sqlMapClient2.insert("Contact.insert", contact);
		System.out.println("---sqlMapClient2 insert return : " + lastId);
		return (int) sqlMapClient.insert("Contact.insert", contact);
	}
	
	public List<Map> selectAll() throws Exception {
		return sqlMapClient.queryForList("Contact.getAll");
	}
}
