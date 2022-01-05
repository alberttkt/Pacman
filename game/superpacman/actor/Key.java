/*
 *	Author:      Albert Troussard
 *	Date:        4 déc. 2020
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
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends SuperPacmanCollectable implements Logic {
	private Sprite sprite = new Sprite("superpacman/key", 1.f, 1.f, this);
	private Logic taken=Logic.FALSE;
	
	public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
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
	public void update(float deltaTime) {
		super.update(deltaTime);
		//System.out.println(taken.isOn());
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		 ((SuperPacmanInteractionVisitor)v).interactWith(this);
		 }
	
	public void collect() {
		taken=Logic.TRUE;
		super.collect();
		
	}
	public Logic getTaken() {
		return taken;
	}
	@Override
	public boolean isOn() {
		if(taken.isOn()) {
		return true;
		}else {
			return false;
		}
	}
	@Override
	public boolean isOff() {
		if(taken.isOff()) {
			return true;
			}else {
				return false;
			}
	}
	@Override
	public float getIntensity() {
		if(isOn()) {
			return 1;
		}else {
		return 0;
		}
	}
	

}
