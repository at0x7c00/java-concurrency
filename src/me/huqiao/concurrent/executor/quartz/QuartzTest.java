package me.huqiao.concurrent.executor.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

	public static void main(String[] args) throws InterruptedException, SchedulerException {
		QuartzTest manager = new QuartzTest();
		JobKey jobKey = manager.startScheduleTask(10, "test", "test-group", TestInterruptableJob.class);
		Thread.sleep(3000);
		manager.shutdownJob(jobKey);
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.deleteJob(jobKey);
		scheduler.shutdown(false);
	}
	
	public JobKey startScheduleTask(int period,String idName,String idGroup,Class jobClass){
		try {
			
			final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail job = JobBuilder.newJob(jobClass)
					.withIdentity("job-" + idName, "group-" + idGroup)
					.build();
	
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger-" + idName, "group-user")
					.startNow()
					.withSchedule(SimpleScheduleBuilder
							.simpleSchedule()
							.withIntervalInSeconds(period)
							.repeatForever())
					.build();
	
			scheduler.scheduleJob(job, trigger);
			if(!scheduler.isStarted()){
				scheduler.start();
			}
			
			return job.getKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void shutdownJob(JobKey jobKey) throws SchedulerException{
		if(jobKey!=null){
			final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			System.out.println("delete job " + jobKey + "=" + scheduler.deleteJob(jobKey));
		}
	}
	
}
