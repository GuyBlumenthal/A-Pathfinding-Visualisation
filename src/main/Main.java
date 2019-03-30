package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Main {

	static Window window;
	public final static int CELL_SIZE = 25;
	static ArrayList<ArrayList<Cell>> grid;

	static ArrayList<Cell> open;
	static ArrayList<Cell> closed;

	static Cell start;
	static Cell end;

	static boolean ended = false;
	static boolean pathExists = false;
	static boolean pathVisualized = false;
	
	static Timer timer;

	public static boolean pathfinding = false;

	public static char key = ' ';

	private static void startProgram() {

		window = new Window();
		
		grid = new ArrayList<ArrayList<Cell>>();

	}

	public static void display(Graphics2D g2d) {

		adaptGrid();

		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				grid.get(i).get(j).show(g2d);
			}
		}
	}

	public static void adaptGrid() {
		Rectangle windowBounds = window.panel.getBounds();

		int rows = windowBounds.height / CELL_SIZE + 1;
		int cols = windowBounds.width / CELL_SIZE + 1;

		while (grid.size() > cols) {
			grid.remove(grid.size() - 1);
		}

		while (grid.size() < cols) {
			grid.add(new ArrayList<Cell>());
		}

		for (int i = 0; i < grid.size(); i++) {

			ArrayList<Cell> a = grid.get(i);

			for (int j = 0; j < rows; j++) {
				try {
					a.get(j);
				} catch (IndexOutOfBoundsException e) {
					Cell cell = new Cell(i, j);
					window.panel.addMouseListener(cell);
					window.panel.addMouseMotionListener(cell);
					a.add(j, cell);
				}
			}

			while (a.size() > rows) {
				a.remove(a.size() - 1);
			}

		}

	}

	public static void startPathFinding() {
		pathfinding = true;

		open = new ArrayList<Cell>();
		closed = new ArrayList<Cell>();

		for (Cell cell : getNeighbours(start)) {
			addToOpen(cell, start);
		}

		int timerDelay = 20;
		timer = new Timer(timerDelay, new ActionListener(){
		  public void actionPerformed(ActionEvent e) {
		    scanBestNode();
		    repaint();
		  }
		});
		timer.start();

		
	}
	
	public static void endViz () {
		if (pathExists) {
			visualizePath();
		}

		pathfinding = false;
		ended = false;
		pathExists = false;
		pathVisualized = true;

		for (ArrayList<Cell> arrayList : grid) {
			for (Cell cell : arrayList) {
				cell.removeParent();
			}
		}

		closed.clear();
		open.clear();
		timer.stop();
	}

	public static void scanBestNode() {
		colorOpenAndClosed();

		if (open.size() == 0) {
			ended = true;
			
			endViz();
		} else {

			Cell likely = lowestF();

			if (Cell.isEqual(likely, end)) {

				ended = true;
				pathExists = true;
				likely.setEndColor();
				
				endViz();

			} else {

				for (Cell cell : getNeighbours(likely)) {
					addToOpen(cell, likely);
				}

				open.remove(likely);
				closed.add(likely);

			}
		}
	}

	public static void visualizePath() {
		Cell curPathCell = end.parent;
		while (!Cell.isEqual(start, curPathCell)) {
			curPathCell.setPathColor();
			curPathCell = curPathCell.parent;
		}
	}

	public static void removeVisualization() {
		if (!pathfinding && pathVisualized) {
			for (ArrayList<Cell> arrayList : grid) {
				for (Cell cell : arrayList) {
					if (cell.isPathColor()) {
						cell.setEmptyColor();
					}
				}
			}
			pathVisualized = false;
		}
	}

	public static Cell lowestF() {
		Cell lowest = open.get(0);
		for (Cell cell : open) {
			if (cell.getFCost(start, end) < lowest.getFCost(start, end)) {
				lowest = cell;
			}
		}
		return lowest;
	}

	public static boolean reachedEnd() {
		return ended;
	}

	public static void addToOpen(Cell cell, Cell parent) {
		if (!cell.isWall()) {
			if (!isOpen(cell) && !Cell.isEqual(start, cell)) {
				if (!isClosed(cell)) {
					open.add(cell);
				}
				cell.setParent(parent, start);
			}
		}
	}

	public static void colorOpenAndClosed() {
		for (Cell cell : open) {
			cell.setOpenColor();
		}
		for (Cell cell : closed) {
			cell.setClosedColor();
		}
	}

	public static boolean isClosed(Cell cell) {
		for (Cell closedCell : closed) {
			if (Cell.isEqual(cell, closedCell)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOpen(Cell cell) {
		for (Cell openCell : open) {
			if (Cell.isEqual(cell, openCell)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Cell> getNeighbours(Cell cell) {

		ArrayList<Cell> a = new ArrayList<Cell>();

		for (int i = cell.x - 1; i <= cell.x + 1; i++) {
			if (i >= 0 && i < grid.size()) {
				a.add(grid.get(i).get(cell.y));
			}
		}

		for (int i = cell.y - 1; i <= cell.y + 1; i++) {
			if (i >= 0 && i < grid.get(i).size()) {
				a.add(grid.get(cell.x).get(i));
			}
		}

		return a;

	}

	public static void designateStart(Cell cell) {
		if (start != null) {
			start.setEmptyColor();
		}
		start = cell;
	}

	public static void designateEnd(Cell cell) {
		if (end != null) {
			end.setEmptyColor();
		}
		end = cell;
	}

	public static void repaint() {
		window.repaint();
	}

	public static void delay(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		startProgram();
	}

}
