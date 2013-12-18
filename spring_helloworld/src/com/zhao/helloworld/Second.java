package com.zhao.helloworld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Second {
	 @Autowired
	  private Hello hello2;
	 
	 @Autowired
	 private Hello hello; 
	 
	 void say2(){
		 System.out.println("hello:--" + hello.toString());
		 System.out.println("hello2:--" + hello2.toString());
		 
		 hello.sayHello();
		 hello2.sayHello2();
	 }
}
