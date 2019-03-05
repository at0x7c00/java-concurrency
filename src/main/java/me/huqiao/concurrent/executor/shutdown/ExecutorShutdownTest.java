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
		boolean successToTerminated = executorService.awaitTermination(10, TimeUnit.SECONDS);//这里等待10秒，能正常等待到任务线程退出
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
					
					//收到中断请求后，再循环3次后退出
					if(startToCountDown && --i<=0) {
						break;
					}
				} catch (InterruptedException e) {
					//收到中断请求
					startToCountDown = true;
					System.out.println("received interrupt request.");
				}
			}
		}

	}
	
}
