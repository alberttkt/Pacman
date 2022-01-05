/*
 *	Author:      Albert Troussard
 *	Date:        4 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends SuperPacmanCollectable {
	Sprite[] sprites = RPGSprite.extractSprites("superpacman/coin", 4, 1, 1, this, 16, 16);
	private Animation animation=new Animation(ANIMATION_DURATION / 4, sprites);
	private final static int ANIMATION_DURATION=17;
	private final int invulnerableTime=10;
	
	public Bonus(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
	}

	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
	
	
	
	
	
	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas);
		
	}
	
	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
	}
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		 ((SuperPacmanInteractionVisitor)v).interactWith(this);
		 }

	public int getinvulnerableTime() {
		return invulnerableTime;
	}
	
	
	
}

