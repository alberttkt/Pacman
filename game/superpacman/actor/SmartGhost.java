/*
 *	Author:      Albert Troussard
 *	Date:        12 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public abstract class SmartGhost extends Ghost {
	
	
	public SmartGhost(Area area, Orientation orientation, DiscreteCoordinates coordinates,String spriteName) {
		super(area, orientation, coordinates, spriteName);

	}

	

	public void update(float deltaTime) {
		
			changeTargetPos();
		
		
		super.update(deltaTime);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		//graphicPath.draw(canvas);
	}

	public abstract void changeTargetPos() ;

	//public abstract Orientation getNextOrientation() ;

	
}



