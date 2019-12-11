package simpleUIApp;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import fr.ubordeaux.simpleUI.Application;
import fr.ubordeaux.simpleUI.ApplicationRunnable;
import fr.ubordeaux.simpleUI.Arena;
import fr.ubordeaux.simpleUI.TimerRunnable;
import fr.ubordeaux.simpleUI.TimerTask;

public class Run implements ApplicationRunnable<Item> {

	private int width;
	private int height;
	private ArrayList<IA> IAs;

	public Run(int width, int height, ArrayList<IA> IAs) {
		this.width = width;
		this.height = height;
		this.IAs = IAs;
	}

	@Override
	public void run(final Arena<Item> arg0, Collection<Item> arg1) {
		int[] isIAs = new int[IAs.size()];
		
		for(int i=0; i<IAs.size(); i++) {
			isIAs[i] = IAs.get(i).getTeamId();
		}
		
		MouseListener mouseHandler = new MouseListener(isIAs);

		/*
		 * We build the graphical interface by adding the graphical component
		 * corresponding to the Arena - by calling createComponent - to a JFrame.
		 */
		final JFrame frame = new JFrame("Test Arena");

		/*
		 * This is our KeyHandler that will be called by the Arena in case of key events
		 */
		KeyListener keyListener = new KeyListener(frame);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(arg0.createComponent(this.width, this.height, mouseHandler, keyListener));
		frame.pack();
		frame.setVisible(true);

		/*
		 * We initially draw the component
		 */
		arg0.refresh();

		/*
		 * We ask the Application to call the following run function every seconds. This
		 * method just refresh the component.
		 */
		Application.timer(100, new TimerRunnable() {

			public void run(TimerTask timerTask) {
				arg0.refresh();
				
				//Permet de verifier si un joueur n'a plus de planete
				boolean j1 = false;
				boolean j2 = false;
				
				for(Item item : arg1) {
					Planet planet = (Planet)item;
					if(planet.getTeam() == 1) {
						j1 = true;
					}else if(planet.getTeam() == 2) {
						j2 = true;
					}
				}
				
				//Si les deux joueurs peuvent encore jouer
				if(j1 && j2) {
					for(int i=0; i<IAs.size(); i++) {
						IAs.get(i).play();
					}
					for (Item item : arg1) {
						if(item instanceof Planet) {
							Planet p = (Planet)item;
							
							//Les planetes non neutres produisent des vaisseaux
							if(p.getTeam() != 0) {
								p.produce();
							}
							
							p.arrangeShips();
							
							for(int i=0; i < p.getArmada().size(); i++) {
								SpaceShip s = p.getArmada().get(i);
								s.move();
								if(s.isDone()) {
									p.getArmada().remove(s);
									s.setDone(false);
								}
							}
						}
					}
				}else {
					//Le jeu se termine
					timerTask.cancel();
					
					String ret = "";
					
					if(!j1) {
						ret = "IA win !";
					}else if(!j2) {
						ret = "Player win !";
					}
					
					int result = JOptionPane.showOptionDialog(frame, ret,"Result", JOptionPane.DEFAULT_OPTION,
					        JOptionPane.INFORMATION_MESSAGE, null, null, null);
					
					if(result == 0 || result == -1) {
						System.exit(0);
					}
				}				
			}

		});
	}

}
