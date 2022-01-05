/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea implements Logic {

	private final  DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 6);
	private Door door;

	@Override
	public String getTitle() {
		return "superpacman/Level1";
	}

	protected void createArea(SuperPacmanBehavior behavior) {
		super.createArea(behavior);
		door = new Door("superpacman/Level2", (new Level2()).getSpawnPosition(), Logic.TRUE, (SuperPacmanArea) new Level2(),
				Orientation.DOWN, new DiscreteCoordinates(14, 0), new DiscreteCoordinates(15, 0));
		registerActor(door);

		Gate gate1 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this);
		this.registerActor(gate1);
		Gate gate2 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this);
		this.registerActor(gate2);
		
		//Inky inky = new Inky(this,Orientation.RIGHT, new DiscreteCoordinates(10, 10));
	//	this.registerActor(inky);
	}

	public  DiscreteCoordinates getSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public boolean isOn() {
		if(this.getAllDiamondsCollected()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean isOff() {
		if(this.getAllDiamondsCollected()) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public float getIntensity() {
		// TODO Auto-generated method stub
		return 0;
	}
}
