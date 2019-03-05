package me.huqiao.concurrent.uncatchexceptionhandler;

import java.lang.Thread.UncaughtExceptionHandler;

public class MyUncatchExceptionHandler implements UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(t.getName() + " occur exception:" + e.getMessage());
	}

}
