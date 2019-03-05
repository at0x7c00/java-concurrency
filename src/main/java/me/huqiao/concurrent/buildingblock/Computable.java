package me.huqiao.concurrent.buildingblock;

public interface Computable<T,V> {
	
	public V compute(T t);

}
