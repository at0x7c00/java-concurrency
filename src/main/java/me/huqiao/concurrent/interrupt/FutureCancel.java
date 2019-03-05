package me.huqiao.concurrent.interrupt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureCancel {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		Future<String> future = newFixedThreadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				int i = 1;
				while(i==1) {
					System.out.println("running..."  + Thread.currentThread().isInterrupted());
				}
				return null;
			}
		});
		try {
			future.get(5, TimeUnit.SECONDS);
		} catch (ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}finally {
			future.cancel(true);
		}
	}
	
	public static void timeRun() {
		
	}

}
