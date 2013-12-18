package com.zhao.helloworld;

import org.springframework.stereotype.Service;

@Service
public class HelloImpl implements Hello {

	@Override
	public void sayHello() {
		System.out.println("这是一个测试程序");
	}
	
	public void sayHello2(){
		System.out.println("第二次sayHello");
	}

}
