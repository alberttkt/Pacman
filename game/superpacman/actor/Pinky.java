/*
 *	Author:      Albert Troussard
 *	Date:        12 déc. 2020
 */
/**
 * 
 */
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

/**
 * @author trous
 *
 */
public class Pinky extends SmartGhost {
	private int MIN_DISTANCE_FROM_PLAYER_WHEN_AFRAID = 5;
	private DiscreteCoordinates targetPos;
	Path graphicPath;
	private Queue<Orientation> path;
	private boolean hasAfraidTarget = false;

	public Pinky(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, "superpacman/ghost.pinky");
	}

	public void changeTargetPos() {
		if (getAfraid() == true && getMemory() != null) {
			if (!hasAfraidTarget || (hasAfraidTarget && path.peek() == null)) {
				// garde la meme cible tant qu'il ne l'a pas atteint
				targetPos = reachableRandomCell(MIN_DISTANCE_FROM_PLAYER_WHEN_AFRAID);
				hasAfraidTarget = true;
			}
		} else {
			hasAfraidTarget = false;
			if (getMemory() != null) {
				targetPos = getMemory().getCurrentMainCell();
			} else {
				targetPos = reachableRandomCell();
			}
		}

	}

	public Orientation getNextOrientation() {
		path = ((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(), targetPos);
		while (path.peek() == null) {
			path = ((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
					reachableRandomCell());
		}
		graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));
		return path.poll();
	}

	public DiscreteCoordinates reachableRandomCell(int MIN_DISTANCE) {
		if (path == null) {

		}
		DiscreteCoordinates reachableCell = null;
		boolean cellFound = false;
		while (!cellFound) {
			reachableCell = new DiscreteCoordinates(RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth()),
					RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight()));
			if (DiscreteCoordinates.distanceBetween(reachableCell, getMemory().getCurrentMainCell()) >= MIN_DISTANCE
					&& (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(reachableCell)))
					&& (((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
							reachableCell)) != null) {
				cellFound = true;
			}

		}

		return reachableCell;
	}

	public DiscreteCoordinates reachableRandomCell() {
		DiscreteCoordinates reachableCell = null;
		boolean cellFound = false;
		while (!cellFound) {
			reachableCell = new DiscreteCoordinates(RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth()),
					RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight()));
			if ((getOwnerArea().canEnterAreaCells(this, Collections.singletonList(reachableCell)))
					&& (((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
							reachableCell)) != null) {
				cellFound = true;
			}

		}

		return reachableCell;
	}

}
