/**
 * 
 */
package com.ssi.dao;

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

	public void insert(Contact contact) throws Exception {
		
	}
}
