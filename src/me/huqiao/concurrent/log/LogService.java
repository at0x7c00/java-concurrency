package me.huqiao.concurrent.log;

import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author huqiao
 */
public class LogService {
	
	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintWriter writer;
	private boolean isStoped = false;
	private int logCount = 0;
	
	public LogService() {
		queue = new ArrayBlockingQueue<>(10);
		loggerThread = new LoggerThread();
		writer = new PrintWriter(System.out);
	}
	public void start() {
		loggerThread.start();
	}
	
	public void stop() {
		isStoped = true;
		loggerThread.interrupt();//实现queue.take()抛异常的效果,促使任务线程赶紧去查看“isStoped”状态
	}
	public void log(String log) throws InterruptedException {
		synchronized (this) {
			if(isStoped) {
				//拒绝新日志写入
				throw new IllegalStateException(" logger is stoped");
			}
			logCount++;
		}
		queue.put(log);
	}
	
	private class LoggerThread extends Thread{

		@Override
		public void run() {
			try {
				while(true) {
					try {
						String log = null;
						synchronized (LogService.this) {
							if(isStoped && logCount == 0) {//logCount确保在stop之后还有机会处理接下来的日志
								break;
							}
						}
						log = queue.take();
						synchronized (LogService.this) {
							logCount--;
						}
						writer.write(log);
					} catch (InterruptedException e) {
						//忽略异常（try-catch到之后，中断状态已经被标记为false了，再take时就不会出错了），因为已经自己实现了中断机制
					}
				}
			}finally {
				writer.close();
			}
		}
	}

}
