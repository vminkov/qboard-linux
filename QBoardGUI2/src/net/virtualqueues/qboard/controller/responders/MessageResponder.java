package net.virtualqueues.qboard.controller.responders;

import java.io.Serializable;

public interface MessageResponder {
	String getType();
	void handleMessage(Serializable data);
	
}
