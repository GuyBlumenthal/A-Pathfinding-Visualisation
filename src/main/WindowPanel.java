package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class WindowPanel extends JPanel implements KeyListener {
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		Main.display(g2d);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Main.key = e.getKeyChar();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (! Main.pathfinding) {
				Main.startPathFinding();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Main.key = ' ';
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
