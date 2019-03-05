package me.huqiao.concurrent.executor.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0)
			throws JobExecutionException {
		//while(true){
			try {
				System.out.println("job is running");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("receive interrupt request");
			}
		//}
	}
	
}
