package com.zhao.annotation;
import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("a")
@Scope("singleton") //// @Scope("prototype")
public class A {

	private String mName;
	
	public A(){
		System.out.println("Construct A, mName= " + mName);
	}
	
	public void fun(){
		System.out.println("mName = " + mName);
	}
	
	@PostConstruct
	public void init() {
		System.out.println("init mName");
		mName = "yes";
	}

}
