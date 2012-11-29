package net.virtualqueues.qboard;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ThreadsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();

		exec.execute(new Runnable(){
			public void run(){
				for(int i=0; i<10000000; i++) System.out.println(i);
			}
		});
		
		exec.execute(new Runnable(){
			public void run(){
				InetAddress localhost = InetAddress.getLoopbackAddress();
				InetSocketAddress localsocketaddr = new InetSocketAddress(localhost, 2343);


				SSLServerSocketFactory sslserversocketfactory =
	                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
				
				System.out.println("Server starting at port " + 2343);
				SSLServerSocket sslserversocket = null;
				try {
					sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket();
			
					sslserversocket.setEnabledCipherSuites(new String[]{"SSL_DH_anon_WITH_3DES_EDE_CBC_SHA"});
				
					sslserversocket.bind(new InetSocketAddress(localhost, 2343));
					SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i=0; i>-10000000; i--) System.out.println(i);
			}
		});
	}

}
