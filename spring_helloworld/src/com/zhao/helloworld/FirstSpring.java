package com.zhao.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstSpring {
	static ApplicationContext applicationContext ;
	
	
	public static void testHello() {
		 Hello hello =(Hello) applicationContext.getBean("sayhello");
		 hello.sayHello();
		 System.out.println(hello.getClass() + "-- " +
		 hello.getClass().getName());
	}

	public static void testHello2() {
		Second sec = new Second();
		sec.say();
	}
	
	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext(
				new String[]{"applicationContext.xml"});
		testHello();
		//testHello2();
	}
}
