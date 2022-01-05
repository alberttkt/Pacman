/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior.SuperPacmanCell;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior.SuperPacmanCellType;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPlayer extends Player {

	private static int SPEED = 2;
	private Orientation desiredOrientation = Orientation.RIGHT;
	private final SuperPacmanPlayerHandler handler = new SuperPacmanPlayerHandler();
	private final int ANIMATION_DURATION = 10;
	private int hp = 3;
	protected final int HP_MAX = 5;
	private int score = 0;
	private int collectedDiamond = 0;
	private float invulnerableTime = 0;
	private boolean invulnerable = false;
	private boolean eaten = false;
	private boolean hasCrown = false;
	private boolean dead = false;

	private SuperPacmanPlayerStatusGUI StatusGUI = new SuperPacmanPlayerStatusGUI(this);
	private Animation currentAnimation;
	private Animation[] animations;
	private Sprite[][] sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1, this, 64, 64,
			new Orientation[] { Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT });

	public SuperPacmanPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);

		animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);
		currentAnimation = animations[orientation.UP.ordinal()];

	}

	/**
	 * @return the current cells
	 */
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	/**
	 * @return the current main cell
	 */
	public DiscreteCoordinates getCurrentMainCell() {
		return super.getCurrentMainCellCoordinates();
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return null;
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return false;
	}

	@Override
	public void interactWith(Interactable other) {

		other.acceptInteraction(handler);

	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);

	}

	@Override
	public void draw(Canvas canvas) {
		currentAnimation.draw(canvas);
		StatusGUI.draw(canvas);
	}

	public void update(float deltaTime) {

		Keyboard keyboard = getOwnerArea().getKeyboard();
		desiredOrientation(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		desiredOrientation(Orientation.UP, keyboard.get(Keyboard.UP));
		desiredOrientation(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		desiredOrientation(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
		if (canMove())
			move(SPEED);
		invulnerability(deltaTime);
		if (isDisplacementOccurs()) {
			currentAnimation = animations[getOrientation().ordinal()];
			currentAnimation.update(deltaTime);
		} else {
			currentAnimation.reset();
		}
		speedUpdate();
		hpUpdate();
		super.update(deltaTime);
	}

	private void desiredOrientation(Orientation orientation, Button b) {
		if (b.isDown()) {
			this.desiredOrientation = orientation;
			// modifie l'orientation voulue
		}
	}

//indique si le joueur peut initier un mouvement dans la direction voulue et si c'est le cas l'oriente
	private boolean canMove() {
		List<DiscreteCoordinates> frontCell = Collections
				.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector()));
		if ((!isDisplacementOccurs())) {
			if (getOwnerArea().canEnterAreaCells(this, frontCell)) {
				orientate(this.desiredOrientation);
			}
			return true;
		} else {
			return false;
		}
	}
//s'occupe de dimminuer le temps d'invulnerabilité et donc gerer cet etat
	private void invulnerability(float deltaTime) {
		if (invulnerableTime > 0f) {
			invulnerableTime -= deltaTime;
			invulnerable = true;
		} else {
			invulnerableTime = 0f;
			invulnerable = false;
		}
	}
//limite le nombre de vie et tue le joueur si il n'en a plus
	private void hpUpdate() {
		if (hp > 5)
			hp = 5;
		if (hp <= 0) {
			hp = 0;
			dead = true;
		}

	}

	/**
	 * @return hp
	 */
	public int GetHp() {
		return hp;
	}

	/**
	 * @return the invulnerability
	 */
	public boolean isInvulnerable() {
		return invulnerable;
	}

	/**
	 * @return collectedDiamond
	 */
	public int GetCollectedDiamond() {
		return collectedDiamond;
	}

	public boolean getEaten() {
		return eaten;
	}

	public SuperPacmanPlayerHandler getHandler() {
		return handler;
	}

	public void resetDiamond() {
		collectedDiamond = 0;
	}

	public void increaseScore(int points) {
		score += points;
	}

	public String scoreToString() {
		return "SCORE: " + Integer.toString(score);
	}
//effectue les actions quand le joueur se fait manger par un fantôme
	public void isEaten() {
		eaten = false;
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		resetMotion();
		hp--;
		this.setCurrentPosition(((SuperPacmanArea) getOwnerArea()).getSpawnPosition().toVector());
		invulnerableTime=1;

	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
//fait varier la vitesse selon la vie
	private void speedUpdate() {
		if (hp > 3) {
			SPEED = 3 * hp / 4;
		} else {
			SPEED = 2;
		}

	}

	public boolean hasCrown() {
		return hasCrown;
	}

	public boolean isDead() {
		return dead;
	}

	public void revive() {
		hp = 3;
		dead = false;
	}

	public void setDead(boolean b) {
		dead = b;

	}

	private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {
		public void interactWith(Door door) {
			setIsPassingADoor(door);

		}

		public void interactWith(Bonus bonus) {
			bonus.collect();
			invulnerableTime += bonus.getinvulnerableTime();
		}

		public void interactWith(Cherry cherry) {
			cherry.collect();
			score += cherry.score();
		}

		public void interactWith(Diamond diamond) {
			diamond.collect();
			score += diamond.score();
			collectedDiamond++;
		}

		public void interactWith(Key key) {
			key.collect();

		}

		public void interactWith(Ghost ghost) {
			if (ghost.getAfraid()) {
				ghost.isEaten();

				// une chance sur 10 de faire apparaitre un coeur
				if (RandomGenerator.getInstance().nextInt(10) == RandomGenerator.getInstance().nextInt(10)) {
					getOwnerArea().registerActor(new Heart(getOwnerArea(), null, ghost.getRefuge()));
				}
				score += ghost.score();
			} else {
				eaten = true;
			}
		}

		public void interactWith(Heart heart) {
			heart.collect();
			hp += heart.heal();
		}

		public void interactWith(Crown crown) {
			crown.collect();
			hasCrown = true;
		}

	}

}
