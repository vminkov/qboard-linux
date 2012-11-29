package net.virtualqueues.qboard;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.virtualqueues.qboard.controller.SecureNetworkMessenger;
import net.virtualqueues.qboard.controller.TasksManager;
import net.virtualqueues.qboard.view.QBGUI;

public class Backbone {
	/**
	 * Launch the application. Concurrency is probably broken.
	 * TODO Revise concurrency. Events?
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
        System.setProperty("java.net.useSystemProxies", "false");	
        SecureMockServer sms;
		TasksManager tm;
		
		executor.execute(tm = TasksManager.getInstance() );
		System.out.println("task manager running");	
				
		executor.execute(sms = new SecureMockServer());
        System.out.println("mock server running");
        
        
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
