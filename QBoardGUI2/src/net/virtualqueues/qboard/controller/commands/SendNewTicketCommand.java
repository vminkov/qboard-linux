package net.virtualqueues.qboard.controller.commands;

import java.io.Serializable;

import net.virtualqueues.model.Ticket;
import net.virtualqueues.controller.Messages;
import net.virtualqueues.qboard.controller.SecureNetworkMessenger;
import net.virtualqueues.controller.Messenger;
import net.virtualqueues.controller.commands.Command;

public class SendNewTicketCommand implements Command {
	private Serializable data;
	private String msgType = Messages.NEW_QBOARD_TICKET;
	
	public SendNewTicketCommand(Ticket t){
		data = (Serializable) t;
	}
	
	public boolean execute(){
		Messenger snm = SecureNetworkMessenger.getInstance();
		
		return snm.sendMessage(this.msgType, this.data);
	}
}
