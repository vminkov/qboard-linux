package net.virtualqueues.qboard.view;

import javax.swing.JFrame;
import javax.swing.UIManager;


public class QBMain extends JFrame {

	/**
	 * Create the application GUI.
	 */
	public QBMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);  
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		this.add(new MainPanel());
		this.setVisible(true);
	}

}
