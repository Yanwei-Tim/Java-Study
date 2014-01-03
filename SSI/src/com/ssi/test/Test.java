package com.ssi.test;



import java.util.Map;

import com.ssi.common.ApplicationContext;
import com.ssi.service.ContactService;


public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = new ApplicationContext();
		ContactService contactService = applicationContext.loadBean(ContactService.class);
		try {
			for (int i = 0; i < 100; i++) {
				Map map = contactService.selectById(1);
				System.out.println(map.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
