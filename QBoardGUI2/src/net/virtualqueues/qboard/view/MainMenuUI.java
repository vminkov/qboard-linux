package net.virtualqueues.qboard.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.virtualqueues.qboard.model.MenuItem;
import net.virtualqueues.qboard.model.MenuObject;
import net.virtualqueues.qboard.model.fields.MenuTextInput;


public class MainMenuUI extends JPanel {

	private BufferedImage image;
	
	/**
	 * Create the panel.
	 */
	public MainMenuUI(BufferedImage image) {
		//this.setBackground(new Color(0,0,0,0));
		setLayout(new GridLayout(0, 1, 10, 10));
		this.image = image; 
		System.out.println(image.getWidth() + " : " + image.getHeight());
		addTitle();
		
		List<MenuItem> options = new ArrayList<MenuItem>();
		for(int i = 0; i < 5; i++){
			MenuItem newMI = new MenuItem("label"+i);
			newMI.addMenuItem("duummy"+i);
			newMI.addTextInput("type here"+i);
			options.add(newMI);
		}
		fillButtons(options);
	}

	private void fillButtons(List<MenuItem> options){
		ArrayList<ButtonUI> buttons = new ArrayList<ButtonUI>();
		
		for(int i=0; i < options.size(); i++){
			ButtonUI btnNewButton = addButton(options.get(i));
						
			buttons.add(btnNewButton);
			
		}
	}
	
	private ButtonUI addButton(final MenuItem mi){
		ButtonUI btnNewButton = new ButtonUI(mi.getLabel());
		final MainMenuUI thisMenu = this;
		add( btnNewButton );
		final String buttonText = "clicked it!" + mi.getLabel();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(buttonText);
				thisMenu.load(mi.open());
			}
		});
		return btnNewButton;
	}
	private TextInputUI addInput(final MenuTextInput mti){
		TextInputUI input = new TextInputUI(mti, mti.getLabel(), 15);
		add( input );
		
		return input;
	}
	
	private void addTitle(){
		JLabel label_main = new JLabel("This is the main menu");
		label_main.setFont(new Font("Verdana", Font.BOLD, 18));
		label_main.setForeground(Color.BLACK);
		label_main.setHorizontalAlignment(SwingConstants.CENTER);
		add(label_main);
	}
	
	public void load(List<MenuObject> options){
		this.removeAll();
		
		addTitle();


		for(MenuObject mo : options){
			if(mo instanceof MenuTextInput){
				MenuTextInput mti = (MenuTextInput)	mo;
				addInput(mti);
			}else if(mo instanceof MenuItem){
				MenuItem mi = (MenuItem) mo;
				addButton(mi);
			}else{
				add(new JLabel("MenuTextInput Not implemented yet"));

			}
		}
		this.revalidate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image != null)
		{
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this );
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		if(image != null){
			return new Dimension(image.getWidth(), image.getHeight());
		}
		return super.getPreferredSize();
	}

}
