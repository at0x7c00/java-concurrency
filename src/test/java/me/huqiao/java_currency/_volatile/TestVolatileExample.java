package me.huqiao.java_currency._volatile;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class TestVolatileExample {

	static int pairCount = 100000;
	static VolatileExample[] exps = new VolatileExample[pairCount];
	static{
		for(int i = 0;i<pairCount;i++){
			exps[i] = new VolatileExample();
		}
	}
	
	@Test
	public void test() throws InterruptedException{
		final int valuePair[][] = new int[pairCount][2];
		Thread[] threads = new Thread[pairCount*2];
		for(int i = 0;i<pairCount;i++){
			final int index = i;
			//final VolatileExample exp = new VolatileExample();
			//writer
			Thread writer = new Thread(new Runnable(){
				@Override
				public void run() {
					VolatileExample exp = exps[index];
					int val = new Random().nextInt(100);
					valuePair[index][0] = val;
					exp.set(val);
				}
			});
			writer.start();
			threads[i*2] = writer;
			//reader
			Thread reader = new Thread(new Runnable(){
				@Override
				public void run() {
					VolatileExample exp = exps[index];
					while(!exp.changed()){
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//System.out.println("waitting for change...");
					}
					int val = exp.get();
					valuePair[index][1] = val;
				}
				
			});
			reader.start();
			threads[i*2+1] = reader;
		}
		for(Thread t : threads){
			t.join();
		}
		for(int i = 0;i<pairCount;i++){
			int write = valuePair[i][0];
			int read =  valuePair[i][1];
			Assert.assertEquals(write,read);
		}
	}
}
