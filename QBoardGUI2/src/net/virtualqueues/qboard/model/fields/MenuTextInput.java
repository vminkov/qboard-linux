package net.virtualqueues.qboard.model.fields;

import net.virtualqueues.qboard.model.MenuFormObject;

public class MenuTextInput extends MenuFormObject {
	private String input;
	
	public void setInput(String in){
		this.input = in;
	}
	
	public String getInput(){
		return this.input;
	}
	public MenuTextInput(String label){
		super(label);
	}
}
