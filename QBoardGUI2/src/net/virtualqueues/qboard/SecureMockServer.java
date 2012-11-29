package net.virtualqueues.qboard;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import net.virtualqueues.qboard.controller.NetworkMessage;
import net.virtualqueues.qboard.controller.responders.Responders;
import net.virtualqueues.qboard.model.TicketType;

/**
 * Should we make it singleton?
 * @author Vicho
 *
 */
class SecureMockServer implements Runnable{
	private static ObjectOutputStream outgoingSerial;
	private static ObjectInputStream incomingSerial;
	private static final InetAddress localhost = InetAddress.getLoopbackAddress();
	private static final int PORT = 2343;
	private static LinkedBlockingQueue<NetworkMessage> incomingMessagesQueue = new LinkedBlockingQueue<NetworkMessage>();
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	
	
	public SecureMockServer(){
		//do nothing.. this is in the main thread - it executes synchronously!
	}
	@Override 
	public void run(){		
		SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslserversocket;
		try {
			System.out.println("Server starting at port " + PORT);
			sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket();
			//certificate is made with: "keytool -genkey keystore -keyalg RSA"
			//this below is a 'hack' (the hack was adding all cipher suites, this is the only one we need
			sslserversocket.setEnabledCipherSuites(new String[]{"SSL_DH_anon_WITH_3DES_EDE_CBC_SHA"});
			
			sslserversocket.bind(new InetSocketAddress(localhost, PORT));
			
			//this is not a real server, because there is always only one client... no need for thread-per-client
			SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

            InputStream inputstream = sslsocket.getInputStream();
            OutputStream outputstream  = sslsocket.getOutputStream();
    	    
			outgoingSerial = new ObjectOutputStream(outputstream);
    		incomingSerial = new ObjectInputStream(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				//do whatever here
				sendNewTicketType();//command design pattern?

			}
		});
		
		//in the rest of the lifetime...
		waitForMessages();
	}
	private static void sendNewTicketType() {
		TicketType tt = new TicketType("the reason", 5, 3);
		NetworkMessage tobesent = new NetworkMessage(Responders.ADD_TICKET_TYPE, tt);
		try {
			outgoingSerial.writeObject(tobesent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//taken from the messenger:

	private void waitForMessages(){
		Object message = null;
        while(true){	
	        try {
				if ((message = incomingSerial.readObject()) != null) {
					NetworkMessage incomingMessage = (NetworkMessage) message;
					if(incomingMessage == null || incomingMessage.data == null ||
							incomingMessage.type == null || incomingMessage.type == ""){
						continue;
					}
					try {
						incomingMessagesQueue.put( incomingMessage );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
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
}