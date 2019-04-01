package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class WindowPanel extends JPanel implements KeyListener, MouseListener {

	public WindowPanel() {
		addMouseListener(this);
	}

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
			if (!Main.pathfinding) {
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && Main.key == 'c') {
			Main.grid.clear();
			Main.adaptGrid();
		}
	}

}
