package me.huqiao.concurrent.volatiletest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {

	private volatile AtomicInteger num = new AtomicInteger(0);
	
	public synchronized void update(int num) {
		Printer.print(num, false);
		this.num.set(num);;
	}
	
	public synchronized int get() {
		int res = num.get();
		Printer.print(res, true);
		return res;
	}
	
	static class MyThread extends Thread{
		VolatileTest test;
		boolean updator = false;
		public MyThread(VolatileTest test,boolean updator) {
			this.test = test;
			this.updator = updator;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					if(updator) {
						test.update(new Random().nextInt(100));
					}else {
						test.get();
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		VolatileTest test = new VolatileTest();
		int threadNum = 10;
		for(int i = 0;i<threadNum;i++) {
			MyThread thread = null;
			thread = new MyThread(test,i==threadNum-1);
			thread.start();
		}
	}
	
	static class Printer{
		static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		public static synchronized void print(int num,boolean read) {
			//System.out.print(sdf.format(new Date()) + " ");
			System.out.print(new Date().getTime() + " ");
			if(read) {
				System.out.print(Thread.currentThread().getName() + " read num as " + num);
			}else {
				System.out.print(Thread.currentThread().getName() + " update num to " + num);
			}
			System.out.println();
		}
	}
}
