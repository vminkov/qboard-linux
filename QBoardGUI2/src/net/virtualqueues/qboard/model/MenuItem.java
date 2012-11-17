package net.virtualqueues.qboard.model;

import java.util.ArrayList;
import java.util.List;

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
		return submenu;
	}
}
