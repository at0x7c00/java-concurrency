package me.huqiao.concurrent.buildingblock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierTest {
	
	static int hours = 0;
	static boolean stopAll = false;
	public static void main(String[] args) {
		final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
			@Override
			public void run() {
				System.out.println("every on stop,wait for a minute.");
				hours++;
				if(hours>8) {
					System.out.println("times up,Go off work!");
					stopAll = true;
				}
			}
			
		});
		final Random r = new Random();
		//barrier.
		for(int i = 0;i<3;i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while(!stopAll) {
						System.out.println(Thread.currentThread().getName() + " is working...");
						try {
							Thread.sleep(r.nextInt(2) * 1000);
							barrier.await();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						} catch (BrokenBarrierException e) {
							e.printStackTrace();
						}
					}
				}
				
			}).start();
		}
	}
	


}
