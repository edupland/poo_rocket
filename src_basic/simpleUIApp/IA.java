package simpleUIApp;

import java.util.ArrayList;

/**
 * Represent an Artificial Intelligence in the game.
 * Even if she's so basic that we can call her stupid.
 *
 */
public class IA {
	
	/*
	 * the identifier of the IA
	 */
	private int idTeam;
	/*
	 * The list of all the planets of the game
	 */
	private ArrayList<Item> planets;
	
	
	
	/**
	 * Constructor of the AI
	 * Construct a AI identified by it id
	 * @param i : the identifier of the AI
	 * @param p : the list of all the planets
	 */
	public IA(int id, ArrayList<Item> p) {
		this.idTeam = id;
		this.planets = p;
	}
	
	
	/**
	 * Method play
	 * Manages the moves of the AI
	 * Chooses the sending and the destination planets of the action 
	 */
	public void play() {
		Planet toPlay = null;
		Planet destination = null;
		
		for(int i=0; i<planets.size(); i++) {
			Planet current = (Planet)planets.get(i);
			
			//Choix de la planete de depart
			if(current.getTeam() == idTeam && current.getArmada().isEmpty() && current.getTakeOff().isEmpty()) {
				toPlay = current;
			}
			
			//Choix de la planete cible
			if(current.getTeam() != idTeam && (destination == null || (current.getStock().size() < destination.getStock().size()))) {
				destination = current;
			}
		}
		
		if(toPlay != null && destination != null) {
			//Pourcentage de vaisseaux en partance
			float prct = 0.5f;
			//Nombre de vaisseaux à supprimer
			int toRemove = (int)(toPlay.getStock().size()*prct);
			
			if (toPlay.getStock().size() > 0 && toRemove == 0) {
				toRemove = 1;
			}
								
			toPlay.setObjective(destination, toRemove);
		}
	}
	
	
	/**
	 * Accessor of the AI id
	 * @return the id of the AI
	 */
	public int getTeamId() {
		return this.idTeam;
	}
}
