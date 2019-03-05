package me.huqiao.concurrent.volatiletest;

public class VolatileTest2 {

	public static void main(String[] args) {
		VolatileTest2 test = new VolatileTest2();
		
		test.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		test.stop();
		
	}
	
	volatile boolean stop = false;
	
	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!stop) {
					System.out.println("running...");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void stop() {
		stop = true;
	}
	
}
