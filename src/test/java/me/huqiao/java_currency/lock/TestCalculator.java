package me.huqiao.java_currency.lock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestCalculator {

	@Test
	public void test(){
		final Calculator calc = new Calculator();
		List<Thread> threads = new ArrayList<Thread>();
		int threadCount = 10;
		final int threadAddCount = 500;
		for(int i = 0;i< threadCount;i++){
			Thread t = new Thread(new Runnable(){
				@Override
				public void run() {
					for(int i = 0;i<threadAddCount;i++){
						calc.addOne();
					}
				}
			});
			t.start();
			threads.add(t);
		}
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int totalVal = calc.get();
		System.out.println(totalVal);
		Assert.assertEquals(threadCount * threadAddCount, totalVal);
		
	}
	
}
