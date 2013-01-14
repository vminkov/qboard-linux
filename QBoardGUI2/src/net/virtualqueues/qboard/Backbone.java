package net.virtualqueues.qboard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.virtualqueues.qboard.controller.SecureNetworkMessenger;
import net.virtualqueues.qboard.controller.TasksManager;
import net.virtualqueues.qboard.view.QBGUI;

public class Backbone {
	/**
	 * Launch the application.
	 * TODO Revise concurrency. Events? p.s. event driven mvc SUCKS!
	 */
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        System.setProperty("java.net.useSystemProxies", "false");	
		TasksManager tm = TasksManager.getInstance();
		
		executor.scheduleAtFixedRate(tm, 0, 200, TimeUnit.MILLISECONDS);
		System.out.println("task manager running");	
				
        //SecureMockServer sms;
        //executor.execute(sms = new SecureMockServer());
        //System.out.println("mock server running");
        
        
        //we want dependency injection here
		executor.execute((Runnable) SecureNetworkMessenger.getSecureInstance());
		System.out.println("netowork messenger running");

		executor.execute(new Runnable() {
			public void run() {
				try {
					//QBMain qbm = new QBMain();
					 new QBGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
		System.out.println("GUI running");

		
	}
}
