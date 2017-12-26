package me.huqiao.concurrent.buildingblock;

import java.util.HashMap;

public class ComputeMemroyCache<T,V> {

	HashMap<T,V> cache = new HashMap<T,V>();
	Computable<T,V> computable;
	
	public ComputeMemroyCache(Computable<T,V> computable) {
		this.computable = computable;
	}
	
	public synchronized V compute(T t) {
		V result = cache.get(t);
		if(result == null) {
			result = computable.compute(t);
			cache.put(t, result);
		}
		return result;
	}
	
}
