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
		loggerThread.interrupt();//ʵ��queue.take()���쳣��Ч��,��ʹ�����̸߳Ͻ�ȥ�鿴��isStoped��״̬
	}
	public void log(String log) throws InterruptedException {
		synchronized (this) {
			if(isStoped) {
				//�ܾ�����־д��
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
							if(isStoped && logCount == 0) {//logCountȷ����stop֮���л��ᴦ�����������־
								break;
							}
						}
						log = queue.take();
						synchronized (LogService.this) {
							logCount--;
						}
						writer.write(log);
					} catch (InterruptedException e) {
						//�����쳣��try-catch��֮���ж�״̬�Ѿ������Ϊfalse�ˣ���takeʱ�Ͳ�������ˣ�����Ϊ�Ѿ��Լ�ʵ�����жϻ���
					}
				}
			}finally {
				writer.close();
			}
		}
	}

}
