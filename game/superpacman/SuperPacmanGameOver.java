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
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanGameOver implements Graphics {

	public SuperPacmanGameOver() {

	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
		ImageGraphics gameover = new ImageGraphics(ResourcePath.getSprite("superpacman/gameover"), 25.f, 25.f,
				new RegionOfInterest(0, 0, 1000, 600), anchor.add(new Vector(0, 0)), 1, 2000);
		gameover.draw(canvas);

	}

	public boolean hasResume(SuperPacmanPlayer player, Keyboard keyboard) {
		if (keyboard.get(Keyboard.R).isDown() && player.isDead()) {
			return true;
		} else {
			return false;
		}

	}
}
