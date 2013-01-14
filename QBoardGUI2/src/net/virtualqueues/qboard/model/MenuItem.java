package net.virtualqueues.qboard.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import sun.security.krb5.internal.TicketFlags;

import net.virtualqueues.model.Ticket;
import net.virtualqueues.model.TicketsFactory;
import net.virtualqueues.qboard.controller.commands.SendNewTicketCommand;
import net.virtualqueues.qboard.model.fields.MenuTextInput;
/**
 * Just a button, containing info about the {@link MenuObject}s it uncovers.
 * @author Vicho
 *
 */
public class MenuItem extends MenuObject {

	private ArrayList<MenuObject> submenu = new ArrayList<MenuObject>();
	public MenuItem(String label_arg) {
		super(label_arg);
	}
	
	public void addMenuItem(String label){
		submenu.add(new MenuItem(label));
	}
	
	public void addTextInput(String label){
		submenu.add(new MenuTextInput(label));
	}
	
	public List<MenuObject> open(){
		// send event that the menu is opened?
		SendNewTicketCommand sntc = new SendNewTicketCommand(TicketsFactory.getTicketFromType(TicketsFactory.getTicketType(1), new DateTime(2013, 1, 13, 6, 0), 0));
		if(sntc.execute())
			System.out.println("SUCCESS!!!");
		return submenu;
	}
}
