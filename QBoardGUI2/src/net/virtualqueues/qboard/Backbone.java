package net.virtualqueues.qboard;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.virtualqueues.qboard.controller.SecureNetworkMessenger;
import net.virtualqueues.qboard.view.QBMain;

public class Backbone {
	
	private final static ExecutorService executor = Executors.newCachedThreadPool();
	
	
	/**
	 * Launch the application. Concurrency is most probably broken.
	 * TODO Revise concurrency. Events?
	 */
	public static void main(String[] args) {
		//System.setProperty("java.net.useSystemProxies", "false");	
		executor.execute(new Runnable() {
			public void run() {
				try {
					//QBMain qbm = new QBMain();
					 new QBMain();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
//		(new Thread(new Runnable() {
//			public void run() {
//				try {
//					QBMain qbm = new QBMain();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		})).start();		
//		
		executor.execute(new SecureMockServer());
		//(new Thread(new SecureMockServer())).start();
		executor.execute((Runnable) SecureNetworkMessenger.getSecureInstance());
		//(new Thread((Runnable) SecureNetworkMessenger.getSecureInstance())).start();
				
	}
}
