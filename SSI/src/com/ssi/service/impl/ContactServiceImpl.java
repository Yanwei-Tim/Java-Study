/**
 * 
 */
package com.ssi.service.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssi.bean.Contact;
import com.ssi.service.ContactService;
import com.ssi.dao.ContactDao;


/**
 * @author dh
 *
 */
@Service
public class ContactServiceImpl implements ContactService  {
	
	@Autowired
	ContactDao contactDao;
	
	@Override
	public Map selectById(int id) throws Exception {
		// TODO Auto-generated method stub
		return contactDao.selectById(id);
	}

	@Override
	public int insert(Contact contact) throws Exception {
		// TODO Auto-generated method stub
		return contactDao.insert(contact);
	}

	@Override
	public List<Map> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return contactDao.selectAll();
	}

}
