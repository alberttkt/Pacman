/*
 *	Author:      Albert Troussard
 *	Date:        15 déc. 2020
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

/**
 * @author trous
 *
 */
public class Crown extends SuperPacmanCollectable {
	private Sprite sprite = new Sprite("superpacman/crown", 1.f, 1.f, this);

	/**
	 * @param area
	 * @param orientation
	 * @param position
	 */
	public Crown(Area area, Orientation orientation, DiscreteCoordinates position) {
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
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		 ((SuperPacmanInteractionVisitor)v).interactWith(this);
		 }

}
