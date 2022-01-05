/*
 *	Author:      Albert Troussard
 *	Date:        4 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Gate extends AreaEntity{
	private RPGSprite sprite;

	
	private Logic signal;

	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
		super(area, orientation, position);
		this.signal = signal;
		this.setSprite(orientation);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {

		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		if (signal.isOff()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas canvas) {
		if(signal.isOff()) {
			sprite.draw(canvas);
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(signal.isOn()) {
		getOwnerArea().unregisterActor(this);	}
		}
	private void setSprite(Orientation orientation) {
		if (orientation == Orientation.DOWN || orientation == Orientation.UP) {
			sprite = new RPGSprite("superpacman/gate", 1.f, 1.f,this,new RegionOfInterest(0, 0, 64, 64));
		}else {
			sprite = new RPGSprite("superpacman/gate", 1.f, 1.f,this,new RegionOfInterest(0, 64, 64, 64));

		}
	}

}
