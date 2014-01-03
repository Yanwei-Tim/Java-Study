/**
 * 
 */
package com.ssi.service;

import java.util.Map;

import com.ssi.bean.Contact;

/**
 * @author dh
 *
 */
public interface ContactService {
	public Map selectById(int id) throws Exception;
	public void insert(Contact contact) throws Exception;
		
}
