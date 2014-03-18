package com.zhao.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {
	@Autowired
	A a;
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
