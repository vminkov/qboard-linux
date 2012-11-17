package net.virtualqueues.qboard.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecureNetworkMessenger implements Runnable, Messenger {
	private static final int PORT = 4815;
	private static final InetAddress localhost = InetAddress.getLoopbackAddress(); 
	private SSLSocket socket;
	private static final Messenger instance = new SecureNetworkMessenger();
	
	private SecureNetworkMessenger() {
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			System.out.println("client starting at port " + PORT);
			socket = (SSLSocket) sslsocketfactory.createSocket();

			//hack, @see Backbone
			socket.setEnabledCipherSuites(new String[]{"SSL_DH_anon_WITH_3DES_EDE_CBC_SHA"});
			
			
			socket.bind(new InetSocketAddress(localhost, PORT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		this.greetings();
	}
	@Override
	public void greetings(){
		InetSocketAddress localSocketAddress = new InetSocketAddress(localhost, 2343);
        try {
			socket.connect(localSocketAddress);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		InputStream inputstream = System.in;
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        OutputStream outputstream = null;
        BufferedWriter bufferedwriter = null;
		try {
			outputstream = socket.getOutputStream();
			OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
	        bufferedwriter = new BufferedWriter(outputstreamwriter);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		String string = null;
        try {
			while ((string = bufferedreader.readLine()) != null) {
			    bufferedwriter.write(string + '\n');
			    bufferedwriter.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Messenger getSecureInstance(){
		return instance;
	}
}
