package innovitics.azimut.utilities.threading;

import innovitics.azimut.businessmodels.user.BusinessUser;

public class MyThread implements Runnable {

	private Thread t;
	private ThreadLocal<BusinessUser> threadLocalBusinessUser;
	public MyThread(BusinessUser businessUser,String id) 
	{
		this.t = new Thread(this);
		this.threadLocalBusinessUser.set(businessUser);
		this.t.setName(id);
		System.out.println("Starting New thread: " + t.getName() + " for user "+businessUser.getUserPhone());
		t.start(); // Starting the thread
	}
	@Override
	public void run() {
		
	}
	public Thread getT() {
		return t;
	}
	public void setT(Thread t) {
		this.t = t;
	}
	public ThreadLocal<BusinessUser> getThreadLocalBusinessUser() {
		return threadLocalBusinessUser;
	}
	public void setThreadLocalBusinessUser(ThreadLocal<BusinessUser> threadLocalBusinessUser) {
		this.threadLocalBusinessUser = threadLocalBusinessUser;
	}
	
	
}
