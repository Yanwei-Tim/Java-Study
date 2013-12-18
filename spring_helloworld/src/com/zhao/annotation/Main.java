package com.zhao.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      ApplicationContext context = 
		             new ClassPathXmlApplicationContext("AnnoBeans.xml");
	      
	      A obj = (A) context.getBean("a");
	      obj.fun();
	}
}
