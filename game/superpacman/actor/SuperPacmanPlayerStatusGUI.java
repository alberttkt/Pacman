/*
 *	Author:      Albert Troussard
 *	Date:        4 déc. 2020
 */
/**
 * 
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanPlayerStatusGUI implements Graphics {
	private SuperPacmanPlayer player;

	public SuperPacmanPlayerStatusGUI(SuperPacmanPlayer player) {
		this.player = player;
	}

	@Override
	public void draw(Canvas canvas) {
		//POINTS DE VIE
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
	for(int i=0;i<player.HP_MAX;i++) {
		if(i<player.GetHp()) {
			ImageGraphics life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f,
					new RegionOfInterest(0, 0, 64, 64), anchor.add(new Vector(i, height - 1.375f)), 1, 2000);
			life.draw(canvas);
		}else {
			ImageGraphics life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f,
					new RegionOfInterest(64, 0, 64, 64), anchor.add(new Vector(i, height - 1.375f)), 1, 2000);
			life.draw(canvas);		
		}
	}
	//SCORE
	TextGraphics score = new TextGraphics (player.scoreToString(),0.8f,Color.PINK,Color.BLACK,0.008f,true,true,(anchor.add(new Vector(6, (height - 1.3f))))); 
		score.draw(canvas);
		

	}

}
