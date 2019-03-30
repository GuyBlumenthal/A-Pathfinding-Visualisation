package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

	WindowPanel panel;
	
	public Window() {

		setTitle("A* Pathfinding");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);

		pack();
		
		panel = new WindowPanel();
		
		addKeyListener(panel);
		add(panel);

	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension (600, 400);
	}

}
