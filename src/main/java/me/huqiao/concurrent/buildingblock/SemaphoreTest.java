package me.huqiao.concurrent.buildingblock;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	
	public static void main(String[] args) {
		final Semaphore s = new Semaphore(3);
		for(int i = 0;i<10;i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println(System.currentTimeMillis() +":"+ Thread.currentThread().getName() + " waiting for Permit...");
						s.acquire();
						System.out.println(System.currentTimeMillis() +":"+ Thread.currentThread().getName() + " doing his job...");
						Thread.sleep(5000);
						s.release();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				
			}).start();
		}
	}
	
	
}
