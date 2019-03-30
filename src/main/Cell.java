package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

public class Cell implements MouseListener, MouseMotionListener {

	int x, y, w;
	Color c;
	
	Cell parent;

	Cell(int x, int y) {
		this.x = x;
		this.y = y;

		this.w = Main.CELL_SIZE;

		setEmptyColor();

	}

	public void show(Graphics2D g2d) {
		Rectangle rect = getBounds();
		g2d.setColor(c);
		g2d.fill(rect);
	}

	public Rectangle getBounds() {
		return new Rectangle(x * w, y * w, w, w);
	}

	public void handleMouseEvent(MouseEvent e) {
		if (getBounds().contains(e.getPoint())) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (Main.key == 's') {
					Main.designateStart(this);
					setStartColor();
				} else if (Main.key == 'e') {
					Main.designateEnd(this);
					setEndColor();
				} else {
					setWallColor();
				}
			} else if (SwingUtilities.isRightMouseButton(e)) {
				setEmptyColor();
			}
		}
	}
	
	public int getHCost (Cell end) {
		return cityBlockDistance(end, this);		
	}
	
	public int getGCost (Cell start) {
		if (isEqual(start, this)) {
			return 1;
		} else {
			return parent.getGCost(start) + 1;
		}
	}
	
	public static int cityBlockDistance (Cell a, Cell b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); 
	}
	
	public int getFCost (Cell start, Cell end) {
		return getGCost(start) + getHCost(end);
	}
	
	public void setParent (Cell cell, Cell start) {
		if (parent == null) {
			this.parent = cell;
		}
		if (cell.getGCost(start) < this.parent.getGCost(start)) {
			this.parent = cell;
		}
	}
	
	public void setWallColor () {
		setColor(Style.WALL_COLOR);
	}
	
	public void setStartColor () {
		setColor(Style.START_COLOR);
	}
	
	public void setEndColor () {
		setColor(Style.END_COLOR);
	}
	
	public void setEmptyColor() {
		setColor(Style.EMPTY_COLOR);
	}
	
	public void setOpenColor() {
		setColor(Style.OPEN_COLOR);
	}
	
	public void setClosedColor() {
		setColor(Style.CLOSED_COLOR);
	}
	
	public void setPathColor() {
		setColor(Style.PATH_COLOR);
	}
	
	public boolean isPathColor() {
		
		if (c == Style.PATH_COLOR || c == Style.OPEN_COLOR || c == Style.CLOSED_COLOR) {
			return true;
		}
		return false;
		
	}
	
	private void setColor (Color c) {
		this.c = c;
		Main.repaint();
	}
	
	public boolean isWall () {
		return c == Style.WALL_COLOR;
	}
	
	public void removeParent() {
		this.parent = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Main.removeVisualization();
		handleMouseEvent(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Main.removeVisualization();
		handleMouseEvent(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		return;
	}
	
	public static boolean isEqual (Cell a, Cell b) {
		return a.x == b.x && a.y == b.y;
	}

}
