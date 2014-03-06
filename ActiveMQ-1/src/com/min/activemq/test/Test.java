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
	 */
	public static void main(String[] args) {
		Receiver receivers[] = new Receiver[3];
		for (int i = 0; i < 1; i++) {
			receivers[i] = new Receiver();
			receivers[i].startRecv();
		}
		
		
		for (int i = 0; i < 100; i++) {
			try {
				//receivers[i].join(); // 等待线程结束
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("main thread exited");
	}

}
