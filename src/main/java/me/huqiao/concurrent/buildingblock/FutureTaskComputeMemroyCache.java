package me.huqiao.concurrent.buildingblock;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskComputeMemroyCache<T,V> {

	ConcurrentHashMap<T,FutureTask<V>> cache = new ConcurrentHashMap<T,FutureTask<V>>();
	Computable<T,V> computable;
	
	public FutureTaskComputeMemroyCache(Computable<T,V> computable) {
		this.computable = computable;
	}
	
	public  V compute(final T t) throws InterruptedException, ExecutionException {
		FutureTask<V> result = cache.get(t);
		if(result == null) {
			result = new FutureTask<V>(new Callable<V>() {
				@Override
				public V call() throws Exception {
					return computable.compute(t);
				}
			});
			FutureTask<V> existed = cache.putIfAbsent(t, result);
			if(existed==null) {//之前没有启动计算时这里才需要启动
				result.run();
			}
			result.run();
		}
		return result.get();
	}
}
