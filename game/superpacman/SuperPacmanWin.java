/*
 *	Author:      Albert Troussard
 *	Date:        15 déc. 2020
 */
package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanWin implements Graphics {

	public SuperPacmanWin() {
	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
		ImageGraphics winScreen = new ImageGraphics(ResourcePath.getSprite("superpacman/winscreen"), 25.f, 25.f,
				new RegionOfInterest(0, 0, 1000, 1000), anchor.add(new Vector(0, 0)), 1, 2000);
		winScreen.draw(canvas);

	}

	public boolean quit(SuperPacmanPlayer player, Keyboard keyboard) {
		if (keyboard.get(Keyboard.SPACE).isDown() && player.hasCrown()) {
			return true;
		} else {
			return false;
		}
	}

}
