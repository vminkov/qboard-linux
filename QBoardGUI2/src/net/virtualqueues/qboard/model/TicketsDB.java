package net.virtualqueues.qboard.model;

public class TicketsDB {
	/*
	 * Singleton
	 */
	private TicketsDB instance = new TicketsDB();
	
	private TicketsDB(){
		
	}
	
	public TicketsDB getInstance(){
		return this.instance;
	}
	
	
	
}
