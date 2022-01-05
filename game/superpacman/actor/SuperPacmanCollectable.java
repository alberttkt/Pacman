/*
 *	Author:      Albert Troussard
 *	Date:        4 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanCollectable extends CollectableAreaEntity {
	

	

	public SuperPacmanCollectable(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
	}

	
	public void collect() {
		this.getOwnerArea().unregisterActor(this);
	}

	public void update(float deltaTime) {

	}

	
}
