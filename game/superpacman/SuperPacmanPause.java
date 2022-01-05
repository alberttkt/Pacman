/*
 *	Author:      Albert Troussard
 *	Date:        15 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPause implements Graphics {
private boolean pause=false;
	public SuperPacmanPause() {
	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
		
		TextGraphics pauseText = new TextGraphics ("THE GAME IS PAUSED...",1.8f,Color.PINK,Color.BLACK,0.008f,true,true,anchor.add(new Vector(1f,12f))); 
		pauseText.draw(canvas);

	}
	public boolean isPaused(Keyboard keyboard) {
		
		if(keyboard.get(Keyboard.P).isDown()&&!pause) {
			pause=true;
			return pause;
		}else if(keyboard.get(Keyboard.P).isDown()&&pause) {
			pause=false;
			return pause;
		}else {
			return pause;
		}
	}

	
}

