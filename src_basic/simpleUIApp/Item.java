package simpleUIApp;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Any graphical element that will be handle by the application.
 *
 */
abstract class Item {
	/*
	 * Center of the item
	 */
	protected final Point2D center;
	/*
	 * Width of the item
	 */
	private final int width;

	
	
	/**
	 * Generic constructor for an Item
	 * Construct an item identified by a center and a width
	 * @param x : abscissa of the item
	 * @param y : ordinate of the item
	 * @param w : width of the item
	 */
	public Item(double x, double y, int w) {
		center = new Point2D.Double(x, y);
		width = w;
	}

	
	/**
	 * Accessor of the center
	 * @return the center of the item
	 */
	public Point2D getLocation() {
		return center;
	}

	
	/**
	 * Accessor of the width
	 * @return the width of the item
	 */
	public int getWidth() {
		return width;
	}

	
	/**
	 * Method move
	 * Allows the shifting of an item
	 */
	public abstract void move();

	
	/**
	 * Method draw
	 * Manages the drawing of an item
	 * @param arg0
	 */
	public abstract void draw(Graphics2D arg0);

	
	/**
	 * Mutator of the objective
	 * @param o the new objective of the item
	 */
	public abstract void setObjective(Item o);

	
	/**
	 * Method contains
	 * Tests if a point is contained by the item
	 * @param p the point to be tested
	 * @return true if the point is contained by the item, false otherwise
	 */
	public abstract boolean contains(Point2D p);
	
	
	/**
	 * Method contains
	 * Tests if two items collide
	 * @param i the item to be tested
	 * @return true if the two items collide, false otherwise
	 */
	public boolean contains(Item i) {
		boolean ret = true;
		
		if((i.center.getX()-i.width/2)-15 > (this.center.getX()+this.width/2)+15
				|| (i.center.getX()+i.width/2)+15 < (this.center.getX()-this.width/2)-15
				|| (i.center.getY()-i.width/2)-15 > (this.center.getY()+this.width/2)+15
				|| (i.center.getY()+i.width/2)+15 < (this.center.getY()-this.width/2)-15) {
			ret = false;
		}
		
		return ret;
	}
}
