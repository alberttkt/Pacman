/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior.SuperPacmanCellType;

public class SuperPacman extends RPG {
	private final String[] areas = { "superpacman/Level0", "superpacman/Level1", "superpacman/Level2" };
	private int areaIndex;
	private SuperPacmanPlayer player;
	private SuperPacmanPause pause = new SuperPacmanPause();
	private SuperPacmanWin win = new SuperPacmanWin();
	private SuperPacmanGameOver gameOver = new SuperPacmanGameOver();

	private Keyboard keyboard;

	/**
	 * return the title
	 */
	@Override
	public String getTitle() {
		return "Super Pac-Man";
	}

	public void update(float deltaTime) {
		if (player.hasCrown() && !win.quit(player, keyboard)) {
			win.draw(getWindow());
// display win screen
		} else if (player.isDead() && !gameOver.hasResume(player, keyboard)) {
			gameOver.draw(getWindow());// display gameover screen
		} else if (pause.isPaused(keyboard)) {
			pause.draw(getWindow());// display pause screen
		} else {
			if (player.isPassingADoor()) {
				player.resetDiamond();// reset diamond when you leave an area
			}
			if (player.isInvulnerable()) {
				afraidGhost(true);
			} else {
				afraidGhost(false);
			}
			if (player.getEaten()) {
				player.isEaten();
			}
			// relance le jeu
			if (gameOver.hasResume(player, keyboard)) {
				player.revive();
			}
			// ferme la fenetre de jeu
			if (win.quit(player, keyboard)) {
				getWindow().dispose();
			}
			if (player.GetCollectedDiamond() >= ((SuperPacmanArea) getCurrentArea()).getNumberDiamonds()) {
				((SuperPacmanArea) getCurrentArea()).setAllDiamondsCollected();
				//permet de set le isOn() de l'aire possedant un signalen true
			}
			super.update(deltaTime);
		}
	}

	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			createAreas();
			areaIndex = 0;
			Area area = setCurrentArea(areas[areaIndex], true);
			initPlayer(new SuperPacmanPlayer(area, Orientation.DOWN, ((SuperPacmanArea) area).getSpawnPosition()));
			player = (SuperPacmanPlayer) getPlayer();
			keyboard = getCurrentArea().getKeyboard();
			return true;
		} else
			return false;
	}

	/**
	 * @param b boolean
	 */
	private void afraidGhost(boolean b) {
		List<Ghost> list = (((SuperPacmanArea) getCurrentArea()).getGhostList());
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setAfraid(b);
			}
		}
	}

	
	private void createAreas() {
		addArea(new Level0());
		addArea(new Level1());
		addArea(new Level2());
	}

}
