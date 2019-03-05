package me.huqiao.concurrent.buildingblock;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TimeoutFutureTaskComputeMemroyCache<T,V> {

	ConcurrentHashMap<T,ComputeFutureTask<V>> cache = new ConcurrentHashMap<T,ComputeFutureTask<V>>();
	Computable<T,V> computable;
	
	public TimeoutFutureTaskComputeMemroyCache(Computable<T,V> computable) {
		this.computable = computable;
	}
	
	public  V compute(final T t) throws InterruptedException, ExecutionException {
		ComputeFutureTask<V> result = cache.get(t);
		if(result == null) {
			result = new ComputeFutureTask<V>(new Callable<V>() {
				@Override
				public V call() throws Exception {
					return computable.compute(t);
				}
			},1000 * 60);//一分钟超时
			ComputeFutureTask<V> existed = cache.putIfAbsent(t, result);
			if(existed==null) {//之前没有启动计算时这里才需要启动
				result.run();
			}else if(existed.timeout()) {//超时重新计算
				cache.replace(t, existed, result);
				result.run();
			}
		}
		return result.get();
	}
	
	class ComputeFutureTask<X> extends FutureTask<X>{
		
		long timestamp;
		long age;

		public ComputeFutureTask(Callable<X> callable,long age) {
			super(callable);
			timestamp = System.currentTimeMillis();
			this.age =age;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
		public boolean timeout() {
			return System.currentTimeMillis() - timestamp > age;
		}
		
	}
	
}
