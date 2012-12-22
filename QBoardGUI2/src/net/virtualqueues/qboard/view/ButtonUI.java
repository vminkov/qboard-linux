package net.virtualqueues.qboard.view;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class ButtonUI extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private static final String imagePath = "./res/button-new.png";
	private static final String pressedImgPath = "./res/button-new_pressed.png";
	
	public ButtonUI(String name) {
		super(name);
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		setIcon(new ImageIcon(imagePath));
		setPressedIcon(new ImageIcon(pressedImgPath));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		this.setContentAreaFilled(false);
		//this.setBackground(new Color(0,0,0,0));
		
		File imgFile = new File(ButtonUI.imagePath);
		try{
			this.image = ImageIO.read(imgFile);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	    this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}

	public ButtonUI(Action a) {
		super(a);
	}

	public ButtonUI(String text, Icon icon) {
		super(text, icon);
	}
	
	/*@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(this.image != null){
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this );
		}
	}
	*/
	
	@Override
	public Dimension getPreferredSize() {
		if(image != null){
			return new Dimension(image.getWidth(), image.getHeight());
		}
		return super.getPreferredSize();
	}
	
}
