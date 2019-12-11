package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
/**
 * Represent a spaceship in the game
 *
 */
class SpaceShip extends Item {

	/*
	 * the objective of the ship
	 */
	private Item objective;
	/*
	 * The speed of the ship
	 */
	private int speed;
	/*
	 * The attack power of the ship
	 */
	private int attackPower;
	/*
	 * A boolean which indicates if the ship has reach his objective or not
	 */
	private boolean done;
	/*
	 * The team of the ship
	 */
	private int team;
	/*
	 * The type of the ship
	 */
	private int type;
	
	

	/**
	 * Private constructor of the ship
	 * Constructs a ship identified by a center, a width, a speed and a team
	 * @param x : the abscissa of the ship
	 * @param y : the ordinate of the ship
	 * @param w : the width of the ship
	 * @param s : the speed of the ship
	 * @param t : the team of the ship
	 */
	private SpaceShip(double x, double y, int w, int s, int t) {
		super(x, y, w);
		objective = this;
		this.speed = s;
		this.attackPower = 1;
		this.done = false;
		this.team = t;
	}
	
	
	/**
	 * Constructor of the ship
	 * Construct a ship, identical to the parameter
	 * @param s : the ship to "retype"
	 */
	public SpaceShip(SpaceShip s) {
		super(s.getLocation().getX(), s.getLocation().getY(), s.getWidth());
		objective = this;
		this.speed = s.speed;
		this.attackPower = s.attackPower;
		this.team = s.team;
	}
	
	
	/**
	 * Method createSpaceShip
	 * Allows the creation of a ship
	 * @param x : the abscissa of the ship
	 * @param y : the ordinate of the ship
	 * @param type : the type of the ship
	 * @param t : the team of the ship
	 * @return a ship
	 */
	public static SpaceShip createSpaceShip (double x, double y, int type, int t) {
		SpaceShip s;
		
		if(type == 0) {
			s = new SpaceShip(x,y,10,4,t);
			s.type = type;
		}else {
			s = new SpaceShip(x,y,10,6,t);
			s.type = type;
		}
		
		return s;
	}
	
	
	/**
	 * Mutator of the objective
	 * @param o : the new objective of the ship
	 */
	public void setObjective(Item o) {
		this.objective = o;
	}

	
	/**
	 * Method squareDistance
	 * Calculate the sum of the square of the coordinate of two points
	 * @param p1 the first point
	 * @param p2 the second point
	 * @return the result of the sum
	 */
	private static double squareDistance(Point2D p1, Point2D p2) {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return dx * dx + dy * dy;
	}

	
	@Override
	public boolean contains(Point2D p) {
		return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);
	}

	
	@Override
	public void move() {
		if (!objective.contains(this.center)) {
			double newx = center.getX();
			double newy = center.getY();
			if (newx > objective.getLocation().getX()) {
				newx--;
			} else {
				newx++;
			}
			if (newy > objective.getLocation().getY()) {
				newy--;
			} else {
				newy++;
			}
			center.setLocation(newx, newy);
		} else if(objective != this){
			done = true;
			
			Planet p = (Planet)objective;
			
			if(p.getTeam() == this.team) {
				p.getStock().add(this);
			} else {
				if(p.getStock().size() > 0) {
					p.getStock().remove(0);
				} else {
					p.setTeam(this.team);
					p.getStock().add(this);
				}
			}
			
			objective = this;
		}
	}

	
	@Override
	public void draw(Graphics2D arg0) {
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		if(this.team == 1) {
			arg0.setColor(Color.green);
		}else if(this.team == 2) {
			arg0.setColor(Color.pink);
		}
		arg0.fillRect(x - w / 2, y - w / 2, w, w);
	}
	
	
	/**
	 * Accessor of the attribute done
	 * @return the value of the boolean done
	 */
	public boolean isDone() {
		return this.done;
	}
	
	
	/**
	 * Mutator of the attribute done
	 * @param done : the new value of the attribute
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	
	
	/**
	 * Mutator of the team
	 * @param t : the new team of the ship
	 */
	public void setTeam(int t) {
		this.team = t;
	}
	
	
	/**
	 * Accessor of the type
	 * @return the type of the ship
	 */
	public int getType() {
		return this.type;
	}
	
	
	/**
	 * Method getProdTime
	 * Return the time needed to produce a ship, given by it type
	 * @param type : the type of the ship
	 * @return the time needed to produce a ship, multiplied by 10 to fit the milliseconds of the timer
	 */
	public static int getProdTime(int type) {
		int ret;
		
		if(type == 0) {
			ret = 100;
		}else {
			ret = 250;
		}
		
		return ret;
	}

}
