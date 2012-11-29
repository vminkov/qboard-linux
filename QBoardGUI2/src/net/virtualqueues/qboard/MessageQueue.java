package net.virtualqueues.qboard;

import java.util.concurrent.LinkedBlockingQueue;

import net.virtualqueues.qboard.controller.NetworkMessage;

public class MessageQueue extends LinkedBlockingQueue<NetworkMessage> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageQueue instance = new MessageQueue();
	
	private MessageQueue(){
		
	}
	
	public static MessageQueue getInstance(){
		return MessageQueue.instance;
	}
}
