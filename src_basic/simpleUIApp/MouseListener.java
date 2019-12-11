package simpleUIApp;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.simpleUI.KeyPress;
import fr.ubordeaux.simpleUI.MouseHandler;

public class MouseListener implements MouseHandler<Item> {

	ArrayList<Item> dragList = new ArrayList<Item>();
	private int[] idIA;
	public static float prct = 0.1f;

	public MouseListener(int[] idIA) {
		super();
		this.idIA = idIA;
	}

	@Override
	public void mouseClicked(List<Item> arg0, KeyPress arg1) {
		System.out.println("Select " + arg0 + " " + arg1);
		for (Item testItem : arg0) {
			System.out.println("Mouse click " + testItem);
		}
	}

	@Override
	public void mouseDrag(List<Item> arg0, KeyPress arg1) {
		dragList = new ArrayList<Item>(arg0);
		System.out.println("Drag :" + dragList);
	}

	@Override
	public void mouseDragging(List<Item> arg0, KeyPress arg1) {
		if (!arg0.isEmpty())
			System.out.println("Dragging :" + arg0);
	}

	@Override
	public void mouseDrop(List<Item> arg0, KeyPress arg1) {
		System.out.println("Drag& Drop :" + dragList + " => " + arg0 + " using " + arg1.toString());
		for (Item item : dragList) {
			if(!arg0.isEmpty()) {
				if(item instanceof Planet) {
					//Planete de depart
					Planet d = (Planet)item;
					
					boolean isIA = false;
					
					for(int i=0; i<idIA.length && !isIA; i++) {
						if(idIA[i] == d.getTeam()) {
							isIA = true;
						}
					}
					
					if(d.getTeam() != 0 && !isIA) {
						//Planete d'arrivee
						Planet p = (Planet)arg0.get(0);
						
						//Nombre de vaisseaux à supprimer
						int toRemove = (int)(d.getStock().size()*prct);
						
						if (p.getStock().size() > 0 && toRemove == 0) {
							toRemove = 1;
						}
												
						d.setObjective(p, toRemove);
					}	
				}
			}
		}
	}

	@Override
	public void mouseOver(List<Item> arg0, KeyPress arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(List<Item> arg0, KeyPress arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println(arg0 + " using " + arg1.toString() + " wheel rotate " + arg2);
	}

}
