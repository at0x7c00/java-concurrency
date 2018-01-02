package me.huqiao.concurrent.interrupt;

import java.util.concurrent.Executors;

public class InterruptTest {
	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					try {
						System.out.println("task is running " + Thread.currentThread().isInterrupted());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();//重新标记为已中断
						System.out.println("intrrupted!");
					}
				}
				
			}
			
		});
		t.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		t.interrupt();
		while(true) {
			try {
				System.out.println(t.getName() + " interrupted:" + t.isInterrupted());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
