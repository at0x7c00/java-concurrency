package me.huqiao.concurrent.join;

public class JoinAndForkTest {

	public static void main(String[] args) throws InterruptedException {
		Task task1 = new Task("task1",null);
		Task task2 = new Task("task2",task1);
		
		task1.start();
		Thread.sleep(2000);
		task2.start();
		task2.join();
		
	}
	
	static class Task extends Thread{

		Task waitFor = null;
		public Task(String name,Task waitFor) {
			super(name);
			this.waitFor = waitFor;
		}
		
		@Override
		public void run() {
			System.out.println(getName() + " start");
			if(waitFor!=null) {
				try {
					waitFor.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int i = 0;i<5;i++) {
				System.out.println(getName() + " running " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			System.out.println(getName() + " stop");
		}
		
	}
	
}
