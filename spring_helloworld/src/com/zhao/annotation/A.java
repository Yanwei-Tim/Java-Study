package com.zhao.annotation;
import org.springframework.stereotype.Component;

@Component("a")
public class A {

	void fun(){
		System.out.println("call A fun()");
	}

}
