package com.zhao.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstSpring {
	static ApplicationContext applicationContext ;
	
	
	public static void testHello() {
		 Hello hello =(Hello) applicationContext.getBean("hello");
		 hello.sayHello();
		 System.out.println("hello:--" + hello.toString());
	}

	public static void testHello2() {
		Second sec = (Second) applicationContext.getBean("second");
		sec.say2();
	}
	
	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext(
				new String[]{"applicationContext.xml"});
		
		testHello();
		testHello2();
	}
}
