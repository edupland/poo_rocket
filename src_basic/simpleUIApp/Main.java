package simpleUIApp;

import java.util.ArrayList;
import java.util.Random;

import fr.ubordeaux.simpleUI.*;

public class Main {
	public static void main(String[] args) {
		Random random = new Random();

		
		ArrayList<Item> play = new ArrayList<Item>();
		IA ia = new IA(2, play);
		
		//Planete joueur 1
		play.add(new Planet(random.nextInt(400-50)+25, random.nextInt(500-50)+25, 50, 1));
		
		//Planete joueur 2
		Planet p2 = new Planet(random.nextInt(400-50)+25, random.nextInt(500-50)+25, 50, 2);
		
		while(play.get(0).contains(p2)) {
			p2 = new Planet(random.nextInt(400-50)+25, random.nextInt(500-50)+25, 50, 2);
		}
		
		play.add(p2);
		
		
		//Planetes neutres
		Planet pn = new Planet(random.nextInt(400-50)+25, random.nextInt(500-50)+25, 50, 0);
		
		//On ajoute 5 planetes neutres
		for(int i = 0; i < 5; i++) {
			//On boucle sur toutes les planetes deja presentes dans l'arraylist
			boolean ok = true;
			while(ok) {
				pn = new Planet(random.nextInt(400-50)+25, random.nextInt(500-50)+25, 50, 0);
				ok = false;
				for(int j = 0; j < play.size(); j++) {
					if(play.get(j).contains(pn)) {
						ok = true;
					}
				}
			}
			play.add(new Planet(pn));
		}
			
		ArrayList<IA> IAs = new ArrayList<IA>();
		IAs.add(ia);
		
		Manager manager = new Manager();
		Run r = new Run(400, 500,IAs);

		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(play, manager, r);
	}
}
