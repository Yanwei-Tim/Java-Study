package com.zhao.test;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.Vector;

interface TTActionListener {
	public abstract void actionPerformed();
}

public class TT {

	private class Thread1 implements Runnable {
		private byte[] lock = new byte[0];
		public void run() {

			int i = 100;
			while (i-- > 0) {
				try {
					Thread.sleep(100);
					System.out.println(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Thread1 t = new Thread1();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TT obj = new TT();
		Thread thr = new Thread(obj.t);
		thr.run();
		
		Vector<Object> vec = new Vector<Object>();
		vec.add(Integer.valueOf(1));
	}

}
