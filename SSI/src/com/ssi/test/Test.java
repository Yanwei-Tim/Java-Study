package com.ssi.test;

import java.util.List;
import java.util.Map;

import com.ssi.bean.Contact;
import com.ssi.common.ApplicationContext;
import com.ssi.service.ContactService;

public class Test {
	static void testTrans(ContactService contactService){
		Contact contact = new Contact();
		contact.setName("xxxxxx");
		contact.setPhone("10010");
		contact.setEmail("xxxx@gmail.com");
		try {
			int lastId = contactService.transInsertUpdate(contact);
			System.out.println("contactService.transInsertUpdate return:" + lastId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void testMutilTrans(ContactService contactService){
		System.out.println("-------------------------testMutilTrans-------------------------------------------");

		Contact contact = new Contact();
		contact.setName("xxxxxx");
		contact.setPhone("10010");
		contact.setEmail("xxxx@gmail.com");
		try {
			int lastId = contactService.transMultiDatasource(contact);
			System.out.println("testMultiTransaction success return:" + lastId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void test1(ContactService contactService){
		try {
			for (int i = 0; i < 1; i++) {
				Map map = contactService.selectById(1);
				System.out.println("selectById(1) return " + map);
			}

			System.out
					.println("----------------------selectAll----------------------------------------------");

			for (int i = 0; i < 1; i++) {
				List<Map> list = contactService.selectAll();
				for (Map map : list) {
					System.out.println(map);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = new ApplicationContext();
		ContactService contactService = applicationContext
				.loadBean(ContactService.class);
		test1(contactService);
		//testTrans(contactService);
		//testMutilTrans(contactService);
	}

}
