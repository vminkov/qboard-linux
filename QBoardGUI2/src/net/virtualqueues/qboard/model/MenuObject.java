package net.virtualqueues.qboard.model;

public abstract class MenuObject {
	protected String label;
	protected int itemCode;
	private static int itemCodesCounter = 0;
	
	public String getLabel(){
		return this.label;
	}
	
	MenuObject(String label_arg){
		this.label = label_arg;
		this.itemCode = MenuObject.itemCodesCounter++;
	}
}