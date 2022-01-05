/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Crown;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Guardy;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level2 extends SuperPacmanArea implements Logic {
	private final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 29);

	@Override
	public String getTitle() {
		return "superpacman/Level2";
	}

	protected void createArea(SuperPacmanBehavior behavior) {
		super.createArea(behavior);

		Key key1 = new Key(this, null, new DiscreteCoordinates(3, 16));
		this.registerActor(key1);
		Key key2 = new Key(this, null, new DiscreteCoordinates(26, 16));
		this.registerActor(key2);
		Key key3 = new Key(this, null, new DiscreteCoordinates(2, 8));
		this.registerActor(key3);
		Key key4 = new Key(this, null, new DiscreteCoordinates(27, 8));
		this.registerActor(key4);
		And keys3And4 = new And(key3, key4);
		// key1
		Gate gate1 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 14), key1);
		this.registerActor(gate1);
		Gate gate2 = new Gate(this, Orientation.DOWN, new DiscreteCoordinates(5, 12), key1);
		this.registerActor(gate2);
		Gate gate3 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 10), key1);
		this.registerActor(gate3);
		Gate gate4 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 8), key1);
		this.registerActor(gate4);
		// key2
		Gate gate5 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 14), key2);
		this.registerActor(gate5);
		Gate gate6 = new Gate(this, Orientation.DOWN, new DiscreteCoordinates(24, 12), key2);
		this.registerActor(gate6);
		Gate gate7 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 10), key2);
		this.registerActor(gate7);
		Gate gate8 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 8), key2);
		this.registerActor(gate8);
		// key 3&4
		Gate gate9 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(10, 2), keys3And4);
		this.registerActor(gate9);
		Gate gate10 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(19, 2), keys3And4);
		this.registerActor(gate10);
		Gate gate11 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(12, 8), keys3And4);
		this.registerActor(gate11);
		Gate gate12 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(17, 8), keys3And4);
		this.registerActor(gate12);
		// All DiamondCollected
		Gate gate13 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this);
		this.registerActor(gate13);
		Gate gate14 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this);
		this.registerActor(gate14);
		//End game actors
		Crown crown = new Crown(this, null, new DiscreteCoordinates(15, 1));
		this.registerActor(crown);
		Guardy guardy1 = new Guardy(this, Orientation.RIGHT, new DiscreteCoordinates(15, 7));
		this.registerActor(guardy1);
		Guardy guardy2 = new Guardy(this, Orientation.RIGHT, new DiscreteCoordinates(14, 7));
		this.registerActor(guardy2);
	}

	public DiscreteCoordinates getSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public boolean isOn() {
		if (this.getAllDiamondsCollected()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isOff() {
		if (this.getAllDiamondsCollected()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public float getIntensity() {
		if (isOn()) {
			return 1;
		} else {
			return 0;
		}
	}
}
