package net.virtualqueues.qboard.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import net.virtualqueues.controller.Messenger;
import net.virtualqueues.controller.NetworkMessage;
import net.virtualqueues.qboard.MessageQueue;

public class SecureNetworkMessenger implements Runnable, Messenger {
	private static final int PORT = 4815;
	private static final InetAddress localhost = InetAddress.getLoopbackAddress(); 
	private SSLSocket socket;
	private static final Messenger instance = new SecureNetworkMessenger();
	private OutputStream outputstream;
	private OutputStreamWriter outputstreamwriter;
	private BufferedWriter bufferedwriter;
	private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	//TODO dependency injection
	private static final LinkedBlockingQueue<NetworkMessage> incomingMessagesQueue = MessageQueue.getInstance();// = new PriorityBlockingQueue<NetworkMessage>();
	private InputStream incoming = null;
	private ObjectInputStream incomingSerial = null;
	private ObjectOutputStream outgoingSerial;	
	
	private SecureNetworkMessenger() {
		
	}
	@Override
	public void run(){
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			System.out.println("QBoard starting at port " + PORT);
			socket = (SSLSocket) sslsocketfactory.createSocket();

			//hack, @see Backbone
			socket.setEnabledCipherSuites(new String[]{"SSL_DH_anon_WITH_3DES_EDE_CBC_SHA"});
			
			socket.bind(new InetSocketAddress(localhost, PORT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		InetSocketAddress localSocketAddress = new InetSocketAddress(localhost, 2343);
        try {
        		socket.connect(localSocketAddress);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			outputstream = socket.getOutputStream();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputstreamwriter = new OutputStreamWriter(outputstream);
        bufferedwriter = new BufferedWriter(outputstreamwriter);
        
		try {
			incoming = socket.getInputStream();
			incomingSerial = new ObjectInputStream(incoming);
			outgoingSerial = new ObjectOutputStream(outputstream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.sendGreetings();
		
		System.out.println("waiting for messages...");
		executor.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				waitForMessages();
			}
			
		}, 0, 50, TimeUnit.MILLISECONDS);//executes this through its lifetime
		System.out.println("end of main thread");
	}
	@Override
	public void sendGreetings(){
		InputStream inputstream = System.in;
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
	
		String string = null;
        try {
			while ((string = bufferedreader.readLine()) != null) {
			    bufferedwriter.write(string + '\n');
			    bufferedwriter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void waitForMessages(){
		Object message = null;
        try {
			if ((message = incomingSerial.readObject()) != null) {
				NetworkMessage incomingMessage = (NetworkMessage) message;
				if(incomingMessage == null || incomingMessage.getData() == null ||
						incomingMessage.getType() == null || incomingMessage.getType() == ""){
					return;
				}
				try {
					incomingMessagesQueue.put( incomingMessage );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public boolean sendMessage(String msgType, Serializable data){
		NetworkMessage message = new NetworkMessage(msgType,data);
		try {
			outgoingSerial.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static Messenger getInstance(){
		return getSecureInstance();
	}

	public static Messenger getSecureInstance(){
		return instance;
	}
}
