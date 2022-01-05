package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;
//import ch.epfl.cs107.play.game.areagame.actor.Path;

public class Inky extends SmartGhost {
	private int MAX_DISTANCE_WHEN_SCARED = 5;
	private int MAX_DISTANCE_WHEN_NOT_SCARED = 10;
	private DiscreteCoordinates targetPos;
	Path graphicPath;

	public Inky(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, "superpacman/ghost.inky");

	}

	public DiscreteCoordinates reachableRandomCell(int MAX_DISTANCE) {
		DiscreteCoordinates reachableCell = null;
		boolean cellFound = false;
		while (!cellFound) {
			reachableCell = new DiscreteCoordinates(RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth()),
					RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight()));
			if (DiscreteCoordinates.distanceBetween(reachableCell, super.getRefuge()) <= MAX_DISTANCE
					&& (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(reachableCell)))
					&& (((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
							reachableCell)) != null) {
				cellFound = true;
			}

		}

		return reachableCell;
	}

	public void changeTargetPos() {
		if (getAfraid() == true) {
			targetPos = reachableRandomCell(MAX_DISTANCE_WHEN_SCARED);
		}

		else {
			if (getMemory() != null) {
				targetPos = getMemory().getCurrentMainCell();
			} else {
				targetPos = reachableRandomCell(MAX_DISTANCE_WHEN_NOT_SCARED);
			}
		}
	}

	public Orientation getNextOrientation() {
		Queue<Orientation> path = ((SuperPacmanArea) getOwnerArea()).getGraph()
				.shortestPath(getCurrentMainCellCoordinates(), targetPos);
		while (path.peek() == null) {
			path = ((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
					reachableRandomCell(MAX_DISTANCE_WHEN_NOT_SCARED));
		}
		graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));
		return path.poll();

	}
}
