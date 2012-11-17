/**
 * 
 */
package net.virtualqueues.qboard.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Vicho
 *
 */
public class ButtonHolder extends JPanel {

	/**
	 * 
	 */
	public ButtonUI button;
	public ButtonHolder(String btnName) {
		super();
		setLayout(new BorderLayout());
		
		button = new ButtonUI(btnName);
		
		Container upper = new JLabel("1");
		upper.setPreferredSize(new Dimension(this.getWidth(), 
				(this.getHeight() - button.getHeight()) / 2 ));
		Container bottom = new JLabel("2");
		bottom.setPreferredSize(new Dimension(this.getWidth(),
				(this.getHeight() - button.getHeight()) / 2 ));

		Container left = new JLabel("3");
		left.setPreferredSize(new Dimension((this.getHeight() - button.getHeight()) / 2,
				this.getHeight()));
		Container right = new JLabel("4");
		right.setPreferredSize(new Dimension((this.getHeight() - button.getHeight()) / 2,
				this.getHeight()));
		
		this.add(upper, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(left, BorderLayout.WEST);
		this.add(button, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
		
		System.out.println(this.getHeight());
		System.out.println(button.getHeight());
		System.out.println((this.getHeight() - button.getHeight()) / 2);
	}

}
