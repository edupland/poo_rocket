package simpleUIApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
/**
 * Represent a Planet in the game.
 * Could belong to a player or be neutral.
 *
 */
public class Planet extends Item {
	
	/*
	 * Ship reserve  
	 */
	private ArrayList<SpaceShip> stock;
	/*
	 * Troop
	 */
	private ArrayList<SpaceShip> armada;
	/*
	 * Take off troop
	 */
	private ArrayList<SpaceShip> takeOff;
	/*
	 * Team of the planet
	 * 0 -> neutral planet
	 * 1 -> planet of the first player
	 * 2 -> planet of the second player
	 */
	private int team;
	/*
	 * Production rate
	 */
	private int tauxprod;
	/*
	 * production timer
	 */
	private int timer;
	/*
	 * Type of ship produced
	 */
	private int typeSpaceShip;

	
	
	/**
	 * Constructor of a planet
	 * Constructs a planet identified by a center, a width and a team
	 * @param x : the abscissa of the planet
	 * @param y : the ordinate of the planet
	 * @param w : the width of the planet
	 * @param t : the team of the planet
	 */
	public Planet(double x, double y, int w, int t) {
		super(x, y, w);
		this.team = t;
		
		this.typeSpaceShip = 0;
		
		Random random = new Random();
		
		this.stock = new ArrayList<SpaceShip>();
		double pos = y - w/2 + 5;
		for(int i=0; i<random.nextInt(20)+5; i++) {
			this.stock.add(SpaceShip.createSpaceShip(x + w / 2 + 5, pos, this.typeSpaceShip, this.team));
			pos += 15;
			if(pos >= y + w/2) {
				pos = (int) y - w/2 + 5;
			}
		}
		
		this.armada = new ArrayList<SpaceShip>();
		this.takeOff = new ArrayList<SpaceShip>();
		
		this.timer = 0;
		
		this.tauxprod = 1;
	}
	
	
	/**
	 * Constructor of a planet
	 * Constructs a planet identified by a center, a width and a team
	 * @param x : the abscissa of the planet
	 * @param y : the ordinate of the planet
	 * @param w : the width of the planet
	 * @param t : the team of the planet
	 * @param st : the ship reserve
	 */
	public Planet(double x, double y, int w, int t, ArrayList<SpaceShip> st) {
		super(x, y, w);
		this.team = t;
		
		if(st.size() != 0) {
			this.stock = new ArrayList<SpaceShip>();
			for(SpaceShip s : st){
				s.setTeam(t);
				this.stock.add(s);
			}
			
			this.typeSpaceShip = this.stock.get(0).getType();
		}else {
			this.stock = new ArrayList<SpaceShip>();
			
			this.typeSpaceShip = 0;
		}
		
		
		this.armada = new ArrayList<SpaceShip>();
		this.takeOff = new ArrayList<SpaceShip>();
		
		this.timer = 0;
		
		this.tauxprod = 1;
	}

	
	/**
	 * Constructor of a planet
	 * Construct a planet, identical to the parameter
	 * @param p : the planet to "retype"
	 */
	public Planet(Planet p) {
		super(p.getLocation().getX(), p.getLocation().getY(), p.getWidth());
		this.team = p.team;
		
		this.stock = new ArrayList<SpaceShip>();
		for(SpaceShip s : p.stock) {
			this.stock.add(new SpaceShip(s));
		}
		
		this.armada = new ArrayList<SpaceShip>();
		this.takeOff = new ArrayList<SpaceShip>();
		
		this.timer = 0;
		
		this.typeSpaceShip = p.typeSpaceShip;
		
		this.tauxprod = p.tauxprod;
	}

	
	@Override
	public void move() {}
	
	
	/**
	 * Mutator of the team
	 * @param t the new team of the planet
	 */
	public void setTeam(int t) {
		this.team = t;
	}
	
	
	/**
	 * Accessor of the team
	 * @return the team of the planet
	 */
	public int getTeam() {
		return this.team;
	}

	
	@Override
	public void draw(Graphics2D arg0) {
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		if(this.team == 0){
			arg0.setColor(Color.black);
			arg0.fillRect(x - w / 2, y - w / 2, w, w);
			arg0.setColor(Color.white);
			arg0.fillRect((x - w / 2)+1, (y - w / 2)+1, w-2, w-2);
		}else if(this.team == 1){
			arg0.setColor(Color.green);
			arg0.fillRect(x - w / 2, y - w / 2, w, w);
		}else {
			arg0.setColor(Color.pink);
			arg0.fillRect(x - w / 2, y - w / 2, w, w);
		}
		arg0.fillRect(x - w / 2, y - w / 2, w, w);
		arg0.setColor(Color.black);
		arg0.setFont(new Font("Courier", Font.PLAIN, 15));
		arg0.drawString(String.valueOf(stock.size()), x-5, y+5);
		
		for(SpaceShip s : armada) {
			s.draw(arg0);
		}
		
		for(SpaceShip s : takeOff) {
			s.draw(arg0);
		}
	}

	
	@Override
	public void setObjective(Item o) {}
	
	
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
		boolean ret = false;
		double w = getWidth()/2;
		
		
		if(squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2)) {
			ret = (this.center.getX() - w <= p.getX() && p.getX() <= this.center.getX()+ w) && (this.center.getY() - w <= p.getY() && p.getY() <= this.center.getY() + w);
		}
		return ret; 
	}
	
	
	/**
	 * Accessor of the ship reserve
	 * @return the address of the reserve
	 */
	public ArrayList<SpaceShip> getStock(){
		return this.stock;
	}
	
	
	/**
	 * Accessor of the planet troop
	 * @return the address of the troop
	 */
	public ArrayList<SpaceShip> getArmada(){
		return this.armada;
	}
	
	
	/**
	 * Accessor of the planet take off troop
	 * @return the address of the take off troop
	 */
	public ArrayList<SpaceShip> getTakeOff(){
		return this.takeOff;
	}
	
	
	/**
	 * Method setObjective
	 * Set the objective of the armada
	 * @param p : the new objective of the armada
	 * @param toRemove : the number of ship to send
	 */
	public void setObjective(Planet p, int toRemove) {
		if(this.armada.isEmpty() && this.takeOff.isEmpty()) {
			if(stock.size() > 0) {
				takeOff.addAll(stock.subList(0, toRemove));
				for(int i=0; i < takeOff.size(); i++) {
					takeOff.get(i).setObjective(p);
				}
				stock.removeAll(takeOff);
			}
			
		}else {
			for(SpaceShip s : takeOff) {
				s.setObjective(p);
			}
			
			for(SpaceShip s : armada) {
				s.setObjective(p);
			}
		}
	}
	
	
	/**
	 * Method arrangeShips
	 * Displays all the ships of the take off troop around the planet for the take off
	 */
	public void arrangeShips() {
		if(!this.takeOff.isEmpty()) {
			int nbShip = this.getWidth()/(takeOff.get(0).getWidth()+5);
			
			int diffY = takeOff.get(0).getWidth()/2;
			int diffX = takeOff.get(0).getWidth()/2;
			
			for(int i=0; i<nbShip && i<this.takeOff.size(); i++) {
				takeOff.get(i).getLocation().setLocation(this.getLocation().getX()-this.getWidth()/2, this.getLocation().getY()-this.getWidth()/2+diffY);
				diffY += takeOff.get(0).getWidth()+5;
				armada.add(takeOff.get(i));
				takeOff.remove(i);
			}
			
			for(int i=0; i<nbShip && i<this.takeOff.size(); i++) {
				takeOff.get(i).getLocation().setLocation(this.getLocation().getX()-this.getWidth()/2+diffX, this.getLocation().getY()-this.getWidth()/2);
				diffX += takeOff.get(0).getWidth()+5;
				armada.add(takeOff.get(i));
				takeOff.remove(i);
			}
		}
	}

	
	/**
	 * Method produce
	 * Produces ships according to production rate of the planet
	 */
	public void produce() {
		
		timer++;
		
		if(timer >= SpaceShip.getProdTime(typeSpaceShip)) {
			for(int i=0; i<this.tauxprod; i++) {
				this.stock.add(SpaceShip.createSpaceShip(this.getLocation().getX(), this.getLocation().getY(), this.typeSpaceShip, this.team));
			}
			timer = 0;
		}
	}

}
