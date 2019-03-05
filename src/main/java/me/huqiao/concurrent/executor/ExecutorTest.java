package me.huqiao.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

	public static void main(String[] args) throws InterruptedException {
		final ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("run task..");
			}
		});
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				executorService.shutdown();
			}
			
		}).start();
		//µÈ´ýExecutor¹Ø±Õ
		executorService.awaitTermination(1000, TimeUnit.DAYS);
		System.out.println("executorService shutdown");
		
	}
}
