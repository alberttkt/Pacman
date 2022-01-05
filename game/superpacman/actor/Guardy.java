/*
 *	Author:      Albert Troussard
 *	Date:        15 déc. 2020
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

public class Guardy extends Ghost {
	private int MAX_DISTANCE_FROM_REFUGE = 4;
	private Queue<Orientation> path = ((SuperPacmanArea) getOwnerArea()).getGraph()
			.shortestPath(getCurrentMainCellCoordinates(), reachableRandomCell());;

	public Guardy(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, "superpacman/ghost.guardy");

	}

	public DiscreteCoordinates reachableRandomCell() {
		DiscreteCoordinates reachableCell = null;
		boolean cellFound = false;
		while (!cellFound) {
			reachableCell = new DiscreteCoordinates(RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth()),
					RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight()));
			if (DiscreteCoordinates.distanceBetween(reachableCell, super.getRefuge()) <= MAX_DISTANCE_FROM_REFUGE
					&& (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(reachableCell)))
					&& (((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
							reachableCell)) != null) {
				cellFound = true;
			}

		}

		return reachableCell;
	}

	public Orientation getNextOrientation() {
		//garde la meme cible tant qu'il ne l'a pas atteint
		while (path.peek() == null) {
			path = ((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(),
					reachableRandomCell());
		}

		return path.poll();

	}
}
