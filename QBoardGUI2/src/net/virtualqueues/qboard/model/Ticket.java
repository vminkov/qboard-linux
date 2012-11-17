package net.virtualqueues.qboard.model;

import org.joda.time.*;

public class Ticket {
	// the ticket number on the queue order
	private int orderNum;
	// the reason for the client's visit
	private String reason;
	// the expected duration of the visit (in seconds)
	private int duration;
	// the time when the visit is expected to start
	private DateTime startDate;
	
	/*
	 * Tickets will be created by the desktop and then sent over to 
	 * the queue board
	 */
	public Ticket(String reason_arg, int duration_arg, int orderNum_arg, DateTime startDate_arg){
		this.orderNum = orderNum_arg;
		this.reason = reason_arg;
		this.duration = duration_arg;
		this.startDate = startDate_arg;
	}
	
}
