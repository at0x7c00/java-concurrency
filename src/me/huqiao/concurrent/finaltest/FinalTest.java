package me.huqiao.concurrent.finaltest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;


public class FinalTest {

	private volatile FinallyCache num = new FinallyCache(0);
	
	public  void update() {
		//Printer.print(res, false);
		num = num.increment();
	}
	
	public int get() {
		int res = num.getNum();
		//Printer.print(res, true);
		return res;
	}
	
	static class MyThread extends Thread{
		FinalTest test;
		public MyThread(FinalTest test) {
			this.test = test;
		}
		
		@Override
		public void run() {
			int i = 0;
			while(i<1) {
				test.update();
				i++;
			}
			cdl.countDown();
		}
		
	}
	
	static int threadNum = 100;
	final static CountDownLatch cdl = new CountDownLatch(threadNum);
	public static void main(String[] args) {
		FinalTest test = new FinalTest();
		new CountDownLatch(threadNum);
		for(int i = 0;i<threadNum;i++) {
			MyThread thread = null;
			thread = new MyThread(test);
			thread.start();
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(test.get());
		
	}
	
	static class Printer{
		static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		public static void print(int num,boolean read) {
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
