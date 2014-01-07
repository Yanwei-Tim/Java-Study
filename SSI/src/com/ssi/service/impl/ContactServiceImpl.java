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
		return contactDao.selectAll();
	}

	@Override
	// 测试多数据源事务
	public int transMultiDatasource(Contact contact) throws Exception {
		//return contactDao.testMultiTransaction(contact);
		return contactDao.syncInsert(contact);
	}

	@Override
	// 测试单数据源事务
	public int transInsertUpdate(Contact contact) throws Exception {
		int lastId = 0;
		lastId = contactDao.insert(contact);
		System.out.println("---------insert success return:" +  lastId);
		Contact obj = new Contact();
		obj.setEmail(null);
		obj.setName(contact.getName());
		obj.setPhone(contact.getPhone());
		lastId = contactDao.insert(obj);
		return lastId;
	}

}
