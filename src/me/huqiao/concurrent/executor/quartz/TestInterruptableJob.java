package me.huqiao.concurrent.executor.quartz;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public class TestInterruptableJob implements InterruptableJob{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		while(true) {
			try {
				System.out.println("interruptable task is running");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("receive inerrupt request.");
			}
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("receive inerrupt request.");
	}

}
