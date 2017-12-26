package me.huqiao.concurrent.finaltest;

public class FinallyCache {

	private final int num;
	private final int increment = 1;
	
	public FinallyCache(int num) {
		this.num = num;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public FinallyCache increment() {
		return new FinallyCache(num + increment);
	}
	
}
