/*
 *	Author:      Albert Troussard
 *	Date:        29 nov. 2020
 */
/**
 * 
 */
package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Crown;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Heart;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanCollectable;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;

public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor {

	default void interactWith(SuperPacmanPlayer player) {

	}

	default void interactWith(Wall wall) {

	}

	default void interactWith(Bonus bonus) {

	}

	default void interactWith(Diamond diamond) {

	}

	default void interactWith(Cherry cherry) {

	}

	default void interactWith(Key key) {

	}

	default void interactWith(Door door) {

	}

	default void interactWith(Ghost ghost) {

	}

	default void interactWith(Heart heart) {

	}

	default void interactWith(Crown crown) {

	}

}
