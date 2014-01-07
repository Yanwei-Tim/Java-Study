/**
 * 
 */
package com.ssi.service;

import java.util.List;
import java.util.Map;

import com.ssi.bean.Contact;

/**
 * @author dh
 *
 */
public interface ContactService {
	public Map selectById(int id) throws Exception;
	public  List<Map> selectAll() throws Exception;
	public int insert(Contact contact) throws Exception;
	
	public int transMultiDatasource(Contact contact) throws Exception;
		
	public int transInsertUpdate(Contact contact) throws Exception;
}
