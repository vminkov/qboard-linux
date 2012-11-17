package net.virtualqueues.qboard.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Must be singleton!
 * @author Vicho
 *
 */
public class NetworkMessenger implements Runnable, Messenger {
	private Socket socket;
	private InetAddress localhost;
	private static final int PORT = 4815;
	
	private static Messenger instance = new NetworkMessenger();
	
	private NetworkMessenger() {
		localhost = InetAddress.getLoopbackAddress();
		
		try {
			socket = new Socket();
			socket.bind(new InetSocketAddress(localhost, PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Messenger getInstance(){
		return instance;
	}
	
	public void greetings(){
		InetSocketAddress localSocketAddress = new InetSocketAddress(localhost, 2342);

		OutputStream os;
		try {
			socket.connect(localSocketAddress);
			os = socket.getOutputStream();
			os.write(new String("hillllllooooou").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		greetings();
		
	}
	
}
