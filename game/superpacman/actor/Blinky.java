/*
 *	Author:      Albert Troussard
 *	Date:        9 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Blinky extends Ghost {
	private final static int SPEED = 6;

	public Blinky(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, "superpacman/ghost.blinky");

	}

	@Override
	protected Orientation getNextOrientation() {
		int randomInt = RandomGenerator.getInstance().nextInt(4);
		return Orientation.fromInt(randomInt);
		
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

	}

}
