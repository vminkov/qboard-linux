package net.virtualqueues.qboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import net.virtualqueues.qboard.controller.SecureNetworkMessenger;
import net.virtualqueues.qboard.view.QBMain;

public class Backbone {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setProperty("java.net.useSystemProxies", "false");
	
		class MockServer implements Runnable {
			InetAddress localhost = InetAddress.getLoopbackAddress();
			InetSocketAddress localsocketaddr = new InetSocketAddress(localhost, 2342);

			InputStream is;
			ServerSocketChannel sockChannel = null;
			Socket clientSocket = null;
			
			@Override
			public void run() {
				try {
					sockChannel = ServerSocketChannel.open();
					sockChannel.bind(localsocketaddr);
					clientSocket = sockChannel.accept().socket();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					is = clientSocket.getInputStream();
					byte[] output = new byte[256];
					is.read(output);
					String outputText = new String(output);
					System.out.println(outputText);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		class SecureMockServer extends MockServer {
			@Override 
			public void run(){
				String string;
				SSLServerSocketFactory sslserversocketfactory =
		                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	            SSLServerSocket sslserversocket;
				try {
					System.out.println("Server starting at port " + 2343);
					sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket();
					
					//certificate is made with: "keytool -genkey keystore -keyalg RSA"
					//this below is a hack
					sslserversocket.setEnabledCipherSuites(new String[]{"SSL_DH_anon_WITH_3DES_EDE_CBC_SHA"});
					
					sslserversocket.bind(new InetSocketAddress(localhost, 2343));
		            SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
		            InputStream inputstream = sslsocket.getInputStream();
		            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
		            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
		            while ((string = bufferedreader.readLine()) != null) {
		                System.out.println(string);
		                System.out.flush();
		            }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		(new Thread(new SecureMockServer())).start();
			
		(new Thread(new Runnable() {
			public void run() {
				try {
					QBMain qbm = new QBMain();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})).start();		
		
		(new Thread((Runnable) SecureNetworkMessenger.getSecureInstance())).start();

	}
}
