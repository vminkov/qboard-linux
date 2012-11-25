package net.virtualqueues.qboard.model;

import java.io.Serializable;

import org.joda.time.*;

public class Ticket implements Serializable {
	/**
	 * generated
	 */
	private static final long serialVersionUID = -5558035346929155305L;
	
	// the ticket number on the queue order
	private int orderNum;
	
	// the reason for the client's visit
	private String reason;
	
	// the expected duration of the visit (in seconds)
	private int duration;
	
	// the time when the visit is expected to start
	private DateTime startDate;
	
	// a number for categorizing the tickets by same reasons
	// types will change by clients' wishes, so no enumerations
	private int type;
	
	/*
	 * Tickets will be created by the desktop and then sent over to 
	 * the queue board
	 */
	Ticket(String reason_arg, int duration_arg, int orderNum_arg, DateTime startDate_arg, int type_arg){
		this.orderNum = orderNum_arg;
		this.reason = reason_arg;
		this.duration = duration_arg;
		this.startDate = startDate_arg;
		this.type = type_arg;
	}
	public Ticket(TicketType templateTicket, DateTime startDate_arg, int orderNum_arg){
		this.orderNum = orderNum_arg;
		this.startDate = startDate_arg;
		this.reason = templateTicket.reason;
		this.duration = templateTicket.duration;
		this.type = templateTicket.type;
	}
}
