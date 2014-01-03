package com.ssi.test;

import java.util.List;
import java.util.Map;

import com.ssi.bean.Contact;
import com.ssi.common.ApplicationContext;
import com.ssi.service.ContactService;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = new ApplicationContext();
		ContactService contactService = applicationContext
				.loadBean(ContactService.class);
		try {
			for (int i = 0; i < 1; i++) {
				Map map = contactService.selectById(1);
				System.out.println("selectById(1) return " + map);
			}

			System.out
					.println("--------------------------------------------------------------------");

			for (int i = 0; i < 1; i++) {
				List<Map> list = contactService.selectAll();
				for (Map map : list) {
					System.out.println(map.toString());
				}
			}

			System.out
					.println("--------------------------------------------------------------------");

			for (int i = 0; i < 1; i++) {
				Contact actual = new Contact();
				actual.setName("zhuifeng");
				actual.setPhone("(000) 111-1111");
				actual.setEmail("zhuifeng@gmail.com");
				int retId = contactService.insert(actual);
				System.out.println("retId :" + retId);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
