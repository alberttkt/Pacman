/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaBehavior.Cell;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;

public class SuperPacmanBehavior extends AreaBehavior {
	private int numberDiamonds = 0;
	private List<Ghost> ghostList = new ArrayList();
	private AreaGraph graph = new AreaGraph();

	public SuperPacmanBehavior(Window window, String name) {
		super(window, name);
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				setCell(x, y, new SuperPacmanCell(x, y, SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x))));

			}
		}
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				addToGraph(x, y, getHeight(), getWidth());
			}
		}
	}

	/**
	 * @param x x coordinate of the cell
	 * @param y y coordinate of the cell
	 * @param width width of the area
	 * @param height height of the area
	 * 
	 * add to the graph a node depending to the edges of the cell
	 */
	private void addToGraph(int x, int y, int height, int width) {
		boolean leftEdge = false;
		boolean rightEdge = false;
		boolean upEdge = false;
		boolean downEdge = false;
		if (x > 0 && ((SuperPacmanCell) getCell(x - 1, y)).type != SuperPacmanCellType.WALL)
			leftEdge = true;
		if (x < width - 1 && ((SuperPacmanCell) getCell(x + 1, y)).type != SuperPacmanCellType.WALL)
			rightEdge = true;
		if (y < height - 1 && ((SuperPacmanCell) getCell(x, y + 1)).type != SuperPacmanCellType.WALL)
			upEdge = true;
		if (y > 0 && ((SuperPacmanCell) getCell(x, y - 1)).type != SuperPacmanCellType.WALL)
			downEdge = true;
		graph.addNode(new DiscreteCoordinates(x, y), leftEdge, upEdge, rightEdge, downEdge);

	}

	public enum SuperPacmanCellType {
		NONE(0), // never used as real content
		WALL(-16777216), // black
		FREE_WITH_DIAMOND(-1), // white
		FREE_WITH_BLINKY(-65536), // red
		FREE_WITH_PINKY(-157237), // pink
		FREE_WITH_INKY(-16724737), // cyan
		FREE_WITH_CHERRY(-36752), // light red
		FREE_WITH_BONUS(-16478723), // light blue
		FREE_EMPTY(-6118750); // sort of gray

		final int type;

		SuperPacmanCellType(int type) {
			this.type = type;
		}

		public static SuperPacmanCellType toType(int type) {
			for (SuperPacmanCellType ict : SuperPacmanCellType.values()) {
				if (ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it
			// to a type
			System.out.println(type);
			return NONE;
		}

	}

	/**
	 * @param area
	 * add the different actors linked to the cell type
	 */
	protected void registerActors(Area area) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.WALL) {
					Wall wall = new Wall(area, new DiscreteCoordinates(x, y),
							getNeighborhood(x, y, getWidth(), getHeight()));
					area.registerActor(wall);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_DIAMOND) {
					Diamond diamond = new Diamond(area, null, new DiscreteCoordinates(x, y));
					area.registerActor(diamond);
					this.numberDiamonds++;
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_CHERRY) {
					Cherry cherry = new Cherry(area, null, new DiscreteCoordinates(x, y));
					area.registerActor(cherry);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BONUS) {
					Bonus bonus = new Bonus(area, null, new DiscreteCoordinates(x, y));
					area.registerActor(bonus);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BLINKY) {
					Blinky blinky = new Blinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(blinky);
					ghostList.add(blinky);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_INKY) {
					Inky inky = new Inky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(inky);
					ghostList.add(inky);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_PINKY) {
					Pinky pinky = new Pinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(pinky);
					ghostList.add(pinky);
				}

			}
		}
	}

	/**
	 * @param x x coordinate of the cell
	 * @param y y coordinate of the cell
	 * @param w width of the area
	 * @param h height of the area
	 * @return table of neighbor of the cell
	 */
	private boolean[][] getNeighborhood(int x, int y, int w, int h) {
		boolean[][] neighborhood = new boolean[3][3];
		for (int a = -1; a <= 1; a++) {
			for (int b = -1; b <= 1; b++) {
				if ((x + a >= 0) && (x + a < w) && (y + b >= 0) && (y + b < h)
						&& ((SuperPacmanCell) getCell(x + a, y + b)).type == SuperPacmanCellType.WALL) {
					neighborhood[a + 1][1 - b] = true;

				}
			}
		}
		return neighborhood;
	}

	public int getNumberDiamonds() {
		return numberDiamonds;
	}

	public List<Ghost> getGhostList() {
		return ghostList;
	}

	public AreaGraph getGraph() {
		return graph;
	}

	public class SuperPacmanCell extends Cell {
		private final SuperPacmanCellType type;

		protected SuperPacmanCell(int x, int y, SuperPacmanCellType type) {
			super(x, y);
			this.type = type;
		}

		@Override
		public boolean isCellInteractable() {
			return false;
		}

		@Override
		public boolean isViewInteractable() {
			return false;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
		}

		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			if (hasNonTraversableContent()) {
				return false;
			} else {
				return true;
			}
		}

	}
}
