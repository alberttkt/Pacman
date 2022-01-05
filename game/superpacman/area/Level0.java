/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Heart;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea {
	private final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(10, 1);

	@Override
	public String getTitle() {
		return "superpacman/Level0";
	}

	protected void createArea(SuperPacmanBehavior behavior) {
		super.createArea(behavior);
		Door door= new Door("superpacman/Level1", (new Level1()).getSpawnPosition(), Logic.TRUE, this,
				Orientation.UP, new DiscreteCoordinates(5, 9), new DiscreteCoordinates(6, 9));
		this.registerActor(door);
		Key key=new Key(this,null,new DiscreteCoordinates(3,4));
		this.registerActor(key);
		Gate gate1= new Gate(this,Orientation.RIGHT,new DiscreteCoordinates(5,8),key);
		this.registerActor(gate1);
		Gate gate2= new Gate(this,Orientation.RIGHT,new DiscreteCoordinates(6,8),key);
		this.registerActor(gate2);
		Heart heart =new Heart(this,null,new DiscreteCoordinates(6,7));
		this.registerActor(heart);



		
	}

	public  DiscreteCoordinates getSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}
}
