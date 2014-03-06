package com.min.activemq.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 程序的简单说明
 * @author min.zhao
 * @E-mail:zhuifeng1017@gmail.com
 * @version 创建时间：2014-3-5 下午5:58:57
 */
public class Test {

	public Test() {
		
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		int size = 1;
		Receiver receivers[] = new Receiver[size];
		for (int i = 0; i < size; i++) {
			receivers[i] = new Receiver();
			receivers[i].startRecv();
		}
		
		
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < size; i++) {
			receivers[i].stopRecv();
		}
		
		for (int i = 0; i < size; i++) {
			receivers[i].join();
		}
		System.out.println("recv thread exited");
		System.out.println("main thread exited");
	}

}
