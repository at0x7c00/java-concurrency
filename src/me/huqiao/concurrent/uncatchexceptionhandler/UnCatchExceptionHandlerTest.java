package me.huqiao.concurrent.uncatchexceptionhandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnCatchExceptionHandlerTest {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(10,new MyThreadFactory());
		service.execute(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				System.out.println(100 / i);
			}
		});
	}

}
