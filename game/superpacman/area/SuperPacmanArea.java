/*
 *	Author:      Albert Troussard
 *	Date:        27 nov. 2020
 */
package ch.epfl.cs107.play.game.superpacman.area;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import  ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class SuperPacmanArea extends Area{
	private static final float SCALE =25.f;
	private SuperPacmanBehavior behavior;
	private int numberDiamonds;
	private boolean allDiamondsCollected=false;
	private List<Ghost> ghostList;
	private AreaGraph graph;
	
	@Override
	public abstract String getTitle() ;
	
	public SuperPacmanBehavior getBehavior() {		
		return behavior;
	}

	@Override
	public float getCameraScaleFactor() {		
		return SCALE;
	}
	
	protected void createArea(SuperPacmanBehavior behavior) {
		behavior.registerActors(this);
		this.numberDiamonds=behavior.getNumberDiamonds();
		this.ghostList=behavior.getGhostList();
		this.graph=behavior.getGraph();
	}
	
	
	
	@Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
        	behavior = new SuperPacmanBehavior(window, getTitle());
            setBehavior(behavior);
            this.createArea(behavior);
            return true;
        }
        return false;
    }

	public  abstract DiscreteCoordinates getSpawnPosition() ;
			
	public int getNumberDiamonds() {
		return numberDiamonds;
	}
	public void setAllDiamondsCollected() {
		allDiamondsCollected=true;
	}
	public boolean getAllDiamondsCollected() {
		return allDiamondsCollected;
	}
	public List<Ghost> getGhostList() {
		return ghostList;
	}
	public AreaGraph getGraph() {
		return graph;
	}
	

}
