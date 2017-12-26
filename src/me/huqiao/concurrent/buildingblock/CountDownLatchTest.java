package me.huqiao.concurrent.buildingblock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

	public static void main(String[] args) throws InterruptedException {
		
		final Random r = new Random();
		
		final CountDownLatch latch = new CountDownLatch(5);
		for(int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(r.nextInt(5) * 1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					System.out.println(Thread.currentThread().getName() + " execute complated!");
					latch.countDown();
				}
				
			}).start();
		}
		System.out.println("Wait for sub thread execute...");
		latch.await();
		System.out.println("All Thread execute complated!");
	}
	
}
