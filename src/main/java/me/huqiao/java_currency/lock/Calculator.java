package me.huqiao.java_currency.lock;

public class Calculator {

	private int val;
	public synchronized void addOne(){
		val++;
	}
	
	public synchronized int get(){
		return val;
	}
	
}
