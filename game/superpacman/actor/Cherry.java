/*
 *	Author:      Albert Troussard
 *	Date:        4 déc. 2020
 */
/**
 * 
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;


public class Cherry extends SuperPacmanCollectable {
	private Sprite sprite = new Sprite("superpacman/cherry", 1.f, 1.f, this);
	
	public Cherry(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
	}

	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
	
	
	
	
	
	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		
	}
	public int score() {
		return 200;
	}
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		 ((SuperPacmanInteractionVisitor)v).interactWith(this);
		 }
	
	
	
}
