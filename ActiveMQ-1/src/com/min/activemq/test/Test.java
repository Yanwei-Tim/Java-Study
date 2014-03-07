package com.min.activemq.test;

import com.min.activemq.mq.Constants;
import com.min.activemq.mq.Receiver;

/**
 * 程序的简单说明
 * @author min.zhao
 * @E-mail:zhuifeng1017@gmail.com
 * @version 创建时间：2014-3-5 下午5:58:57
 */
public class Test {

	public Test() {
	}

//	public void testRecveiver(){
//		
//	}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		int size = 1;
		Receiver receivers[] = new Receiver[size];
		for (int i = 0; i < size; i++) {
			receivers[i] = new Receiver("system", "manager", 
					"tcp://192.168.108.13:61616",
					Constants.MQ_TOPIC,
					false,
					"subscriber1");
			receivers[i].startUp();
		}
		
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < size; i++) {
			receivers[i].shutDown();
		}
		
		System.out.println("Receiver main thread exited");
	}

}
