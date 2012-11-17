/**
 * 
 */
package net.virtualqueues.qboard.view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.virtualqueues.qboard.model.fields.MenuTextInput;

/**
 * @author Vicho
 *
 */
public class TextInputUI extends JPanel {
	private MenuTextInput dbField;

	class InputListener implements KeyListener {
		
		private TextInputUI owner;
		
		InputListener(TextInputUI ti){
			owner = ti;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			char enter = '\n';
			char key = e.getKeyChar();
			if(key == enter){
				owner.writeToDB(owner.inputField.getText());
				System.out.println(owner.inputField.getText());
			}else{
				//System.out.print(key);
			}				
		}
		
	}
	private InputListener inputListener = new InputListener(this);
	public JTextField inputField;
	/**
	 * 
	 */
	public TextInputUI() {
		this.add(new JLabel());
		this.add(inputField = new JTextField());
	}

	/**
	 * @param label
	 */
	public TextInputUI(MenuTextInput mti, String label, int size) {
		this.dbField = mti;
		this.add(new JLabel(label));
		this.add(inputField = new JTextField(size));
		this.setBackground(new Color(0,0,0,0));
		inputField.addKeyListener(inputListener);		
	}
	
	private void writeToDB(String input){
		this.dbField.setInput(input);
	}
}
