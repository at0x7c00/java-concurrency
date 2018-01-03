package me.huqiao.concurrent.executor.shutdown;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdownTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		for(int i= 0;i<1;i++) {
			executorService.execute(new Task(i+""));
		}
		
		executorService.shutdown();
		boolean successToTerminated = executorService.awaitTermination(10, TimeUnit.SECONDS);//����ȴ�10�룬�������ȴ��������߳��˳�
		//System.out.println("successToTerminated=" + successToTerminated);
		//for(Runnable runnable : taskList) {
		//	Task task = (Task)runnable;
		//	System.out.println(task.getName());
		//}
	}
	
	private static class Task implements Runnable{

		private String name;
		public String getName() {
			return name;
		}
		public Task(String name) {
			this.name = name;
		}
		@Override
		public void run() {
			int i = 3;
			boolean startToCountDown = false;
			while(true) {
				try {
					Thread.sleep(1000);
					System.out.println("task " + name + " is running...");
					
					//�յ��ж��������ѭ��3�κ��˳�
					if(startToCountDown && --i<=0) {
						break;
					}
				} catch (InterruptedException e) {
					//�յ��ж�����
					startToCountDown = true;
					System.out.println("received interrupt request.");
				}
			}
		}

	}
	
}
