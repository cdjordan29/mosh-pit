package moshpit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

/**
 * Pokemon class serves as the Model for Project 3, it creates JButton objects with 
 * images of pokemon on them.
 * 
 * @author Daniel Jordan
 * @version 1.0
 */
public class Pokemon extends JButton {

	protected int x;
	protected int y;
	private int dx, dy;
	private int randomClickCount, randomBounceCount;
	private Random randSpawn, clickRand, bounceRand;
	private int clickCount, bounceCount;
	private final int WIDTH = 85;
	private final int HEIGHT = 77;
	private Rectangle r;
	private ImageIcon p;

	/**
	 * Constructor for the Pokemon class, it constructs a JButton object with an image of a pokemon, which
	 * is read in from the makeList() method in the Pokeball class. 
	 */
	public Pokemon(ImageIcon _p) {

		super(_p);
		r = new Rectangle(x, y, WIDTH, HEIGHT);
		this.dx = 4;
		this.dy = 6;
		this.p = _p;
		//Random for the spawn locations on the a Pokemon in Pokeball(View) 
		this.randSpawn = new Random();
		//Randoms for the random click value (5-20) and random bounce values (1-5) of a Pokemon
		this.clickRand = new Random();
		this.bounceRand = new Random();
		//Assignment of those random values
		this.randomClickCount = clickRand.nextInt(16) + 5;
		this.randomBounceCount = bounceRand.nextInt(5) + 1;
		//Incrementing counts 
		this.clickCount = 0;
		this.bounceCount = 0;
		
		x = randSpawn.nextInt(800);
		y = randSpawn.nextInt(800);
		
		super.setLocation(x, y);
		
		setSize(WIDTH, HEIGHT);
		setForeground(Color.RED);
		setBackground(Color.WHITE);
	}

	/**
	 * This is a private helper method to determine if the Pokemon needs to
	 * reverse course in the X dimension
	 *
	 * @return boolean representing whether or not the Pokemon needs to bounce
	 */
	private boolean bounceX() {
		if (getX() <= 0) {
			return true;
		} else if ((getX() + getWidth()) >= (1920 - 40)) {
			return true;
		}
		return false;
	}

	/**
	 * This is a private helper method to determine if the Pokemon needs to
	 * reverse course in the Y dimension
	 *
	 * @return boolean representing whether or not the Pokemon needs to bounce
	 */
	private boolean bounceY() {
		if (getY() <= 0) {
			return true;
		} else if ((getY() + getHeight() + 20) >= (1000 - 40)) {
			return true;
		}
		return false;
	}

	/**
	 * The method reverse, moves the (x, y) values of the Pokemon backwards when a collision takes place
	 */
	public void reverse() {
		
		dx = dx * -1;
		dy = dy * -1;
		
	}

	/**
	 * The method bouncePokemon, deals with the (x, y) values of the Pokemon after a collision with the wall
	 */
	
	public void bouncePokemon() {

		if (bounceX()) {
			dx = dx * -1;
		}

		if (bounceY()) {
			dy = dy * -1;
		}
		super.setLocation(x, y);
	}

	/**
	 * The method movePokemon, moves the Pokemon to random locations on the screen
	 */
	public void movePokemon() {

		x = x + dx;
		y = y + dy;
		super.setLocation(x, y);
		r.setBounds(x, y, WIDTH, HEIGHT);
	}

	/**
	 * The method incrementClickCount, increments clickCount for the Pokemon
	 */
	public void incrementClickCount(){
		this.clickCount++;
	}
	
	/**
	 * The method incrementBounceCount, increments bounceCount for the Pokemon
	 */
	public void incrementBounceCount(){
		this.bounceCount++;
	}

	/**
	 * Getter method for clickCount
	 * @return int clickCount
	 */
	public int getClickCount(){
		return this.clickCount;
	}
	
	/**
	 * Getter method for bounceCount
	 * @return int bounceCount
	 */
	public int getBounceCount(){
		return this.bounceCount;
	}
	
	/**
	 * Getter method for randomClickCount
	 * @return int randomClickCount
	 */
	public int getRandomClickCount(){
		return this.randomClickCount;
	}
	
	/**
	 * Getter method for randomBounceCount
	 * @return int randomBounceCount
	 */
	public int getRandomBounceCount(){
		return this.randomBounceCount;
	}
	
	/**
	 * Getter method for the Rectangle of the Pokemon
	 * @return Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	/**
	 * Getter method for the ImageIcon of the Pokemon
	 * @return ImageIcon
	 */
	public ImageIcon getImageIcon() {
		return p;
	}

	/**
	 * Getter method for x
	 * @return int x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Getter method for y
	 * @return int y 
	 */
	public int getY() {
		return this.y;
	}

	public void actionPerformed(ActionEvent e) {

	}
}
