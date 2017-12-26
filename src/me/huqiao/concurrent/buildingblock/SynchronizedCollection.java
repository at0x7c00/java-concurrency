package me.huqiao.concurrent.buildingblock;

import java.util.Vector;

public class SynchronizedCollection {
	
	//两个同步的原子操作合在一起就不再具有原子性了
	public void getLast(Vector vector) {
		//客户端显式锁保证符合操作的同步
		synchronized (vector) {
			int size = vector.size();
			vector.get(size);
		}
	}
}
