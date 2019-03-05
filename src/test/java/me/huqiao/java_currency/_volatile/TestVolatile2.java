package me.huqiao.java_currency._volatile;

import org.junit.Test;

public class TestVolatile2 {

	@Test
	public void test() throws InterruptedException{
		final Calculator cal = new Calculator();
		
		//adder
		Thread adder = new Thread(new Runnable(){

			@Override
			public void run() {
				while(!cal.isStop()){
					cal.addOne();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		adder.start();
		
		//stop
		Thread stoper = new Thread(new Runnable(){
			
			@Override
			public void run() {
				/*while(cal.get()<1000){
				}
				cal.stop();*/
				while(!cal.ifGeThenStop(1000)){
					//cal.ifGeThenStop(1000);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		stoper.start();
		
		stoper.join();
		adder.join();
		System.out.println(cal.get());
		
	}
	
	class Calculator{
		int val;
		boolean stop;
		public synchronized void addOne(){
			val++;
		}
		public synchronized int get(){
			return val;
		}
		
		public synchronized boolean isStop(){
			return stop;
		}
		public synchronized void stop(){
			stop = true;
		}
		
		public synchronized boolean ifGeThenStop(int val){
			if(this.val >= val){
				stop = true;
				return true;
			}
			return false;
		}
	}
}
