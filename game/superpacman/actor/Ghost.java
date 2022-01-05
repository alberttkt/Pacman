package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior.SuperPacmanCell;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior.SuperPacmanCellType;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Ghost extends MovableAreaEntity implements Interactor {
	private DiscreteCoordinates refuge;
	private final GhostHandler handler = new GhostHandler();
	private int speedNormal = 8;
	private int speedAfraid = 5;
	private int speed = speedNormal;
	private final int GHOST_SCORE = 500;
	private boolean afraid = false;
	private SuperPacmanPlayer memory;
	private Animation currentAnimation;
	private Animation[] animations;
	private Animation afraidAnimation;
	private Sprite[][] sprites;
	private final int ANIMATION_DURATION = 10;

	private Sprite[] afraidSprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, this, 16, 16);

	public Ghost(Area area, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
		super(area, orientation, coordinates);
		sprites = RPGSprite.extractSprites(spriteName, 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);
		currentAnimation = animations[Orientation.DOWN.ordinal()];
		afraidAnimation = new Animation(ANIMATION_DURATION / 4, afraidSprites);
		refuge = coordinates;
	}

	public void update(float deltaTime) {

		afraid();
		if (isDisplacementOccurs()) {
			currentAnimation.update(deltaTime);
		} else {
			Orientation nextOrientation = getNextOrientation();
			orientate(nextOrientation);
			move(speed);
		}
		super.update(deltaTime);
	}

	public void afraid() {

		if (getAfraid()) {
			currentAnimation = afraidAnimation;
			speed = speedAfraid;
		} else {
			currentAnimation = animations[getOrientation().ordinal()];
			speed = speedNormal;
		}
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());

	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		List<DiscreteCoordinates> FieldOfView = new ArrayList<DiscreteCoordinates>();
		for (int x = -5; x <= 5; x++) {
			for (int y = -5; y <= 5; y++) {
				FieldOfView.add(new DiscreteCoordinates(this.getCurrentMainCellCoordinates().x + x,
						this.getCurrentMainCellCoordinates().y + y));
			}
		}
		return FieldOfView;
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {

		return true;
	}

	@Override
	public boolean isViewInteractable() {

		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);

	}

	public void setAfraid(boolean b) {
		afraid = b;
	}

	public boolean getAfraid() {
		return afraid;
	}

	public void isEaten() {
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		this.setCurrentPosition(refuge.toVector());
		resetMotion();
		this.memory = null;
	}

	public int score() {
		return GHOST_SCORE;
	}

	@Override
	public void draw(Canvas canvas) {
		currentAnimation.draw(canvas);

	}

	protected abstract Orientation getNextOrientation();

	public DiscreteCoordinates getRefuge() {
		return refuge;
	}

	public SuperPacmanPlayer getMemory() {
		return memory;
	}

	private class GhostHandler implements SuperPacmanInteractionVisitor {

		public void interactWith(SuperPacmanPlayer player) {
			memory = player;
			if (!afraid && DiscreteCoordinates.distanceBetween(player.getCurrentMainCell(),
					getCurrentMainCellCoordinates()) <= 1 && !isDisplacementOccurs()) {
				player.setEaten(true);

			}

		}
	}
}
