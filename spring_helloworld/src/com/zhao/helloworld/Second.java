package com.zhao.helloworld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Second {
	 @Autowired
	 @Qualifier("HelloImpl")
	  private Hello hello; 
	 
	 void say(){
		 hello.sayHello();
	 }
}
