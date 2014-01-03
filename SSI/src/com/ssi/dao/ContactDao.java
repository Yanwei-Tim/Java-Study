/**
 * 
 */
package com.ssi.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssi.bean.Contact;

/**
 * @author dh
 *
 */
@Repository
public class ContactDao extends SqlMapClientUtil {
	
	public Map selectById(int id) throws Exception {
		return (Map) sqlMapClient.queryForObject("Contact.getById", Integer.valueOf(id));
	}

	public int insert(Contact contact) throws Exception {
		return (int) sqlMapClient.insert("Contact.insert", contact);
	}
	
	public List<Map> selectAll() throws Exception {
		return sqlMapClient.queryForList("Contact.getAll");
	}
}
