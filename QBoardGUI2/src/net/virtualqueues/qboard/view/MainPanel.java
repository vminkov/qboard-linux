package net.virtualqueues.qboard.view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainPanel extends JPanel {

	private static final String imgPath = GuiConsts.backgroundPath;
	private BufferedImage image;
	
	/**
	 * Create the main panel.
	 */
	public MainPanel() {
		this.setOpaque(true);
		this.setLayout(new GridLayout(1, 3 , 0, 0));
		this.setLayout(new BorderLayout());
		addBackgroundImage();
		MainMenuUI centralPanel = new MainMenuUI(this.image.getSubimage(GuiConsts.LEFT_GAP, GuiConsts.TOP_GAP, 
				this.image.getWidth() - GuiConsts.LEFT_GAP - GuiConsts.RIGHT_GAP, 
				image.getHeight() - GuiConsts.TOP_GAP - GuiConsts.BOTTOM_GAP));

		//TestPanelSwing centralPanel = new TestPanelSwing();
		Container top = new JLabel("");
		top.setPreferredSize(new Dimension(this.getSize().width, GuiConsts.TOP_GAP));
		Container bottom = new JLabel("");
		bottom.setPreferredSize(new Dimension(this.getSize().width, GuiConsts.BOTTOM_GAP));

		Container left = new JLabel("");
		left.setPreferredSize(new Dimension(GuiConsts.LEFT_GAP, this.getSize().height));
		Container right = new JLabel("");
		right.setPreferredSize(new Dimension(GuiConsts.RIGHT_GAP, this.getSize().height));
		
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(left, BorderLayout.WEST);
		this.add(centralPanel, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);

	}
	
	private void addBackgroundImage(){
		File imgFile = new File(imgPath);
		try{
			image = ImageIO.read(imgFile);
			image = image.getSubimage(0, 0, GuiConsts.SCREEN_WIDTH, GuiConsts.SCREEN_HEIGHT);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
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
