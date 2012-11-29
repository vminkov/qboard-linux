package net.virtualqueues.qboard.controller.responders;

import java.io.Serializable;
import net.virtualqueues.qboard.controller.responders.Responders;
import net.virtualqueues.qboard.model.TicketType;
import net.virtualqueues.qboard.model.TicketsFactory;

public class AddTicketTypeResponder implements MessageResponder {
	@Override
	public String getType() {
		return Responders.ADD_TICKET_TYPE;
	}

	@Override
	public void handleMessage(Serializable data) {
		TicketType newTicketType = (TicketType) data;
		TicketsFactory tf = TicketsFactory.getInstance();
		System.out.println("Adding new ticket type: " + newTicketType.reason + "; " + newTicketType.duration + "; "  + newTicketType.type);
		tf.addTicketType(newTicketType.reason, newTicketType.duration, newTicketType.type);

	}

}
