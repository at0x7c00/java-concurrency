package me.huqiao.java_currency._volatile;

public class VolatileExample {

	private int x;
	private boolean changed = false; 
	
	public void set(int x){
		this.x = x;
		this.changed = true;
	}
	
	public int get(){
		return x;
	}
	
	public boolean changed(){
		return changed;
	}
}
