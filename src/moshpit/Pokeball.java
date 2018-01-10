package moshpit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

/**
 * Pokeball represents the GUI for Project 3
 * 
 * @author Daniel Jordan
 * @verison 1.0
 */
public class Pokeball {

	/**
	 * The Lists used to store the ImageIcons and Pokemon
	 */
	static List<ImageIcon> myImageList;
	static List<Pokemon> myPokemonList;
	static List<Pokemon> myDummyList;
	
	/**
	 * The int for the spawn timer
	 */
	private int timer;

	/**
	 * The constants to represent the frame width and height
	 */
	public static final int FRAMEHEIGHT = 1000;
	public static final int FRAMEWIDTH = 1920;

	/**
	 * Graphical objects delcared at the class level
	 */
	private JFrame frame;
	private JPanel actionPanel;
	private JPanel playArea;
	private JLabel winLabel;

	/**
	 * JButtons for starting and stopping the timer
	 */
	private JButton startButton;
	private JButton stopButton;

	/**
	 * Timers
	 */
	private Timer pokeAddTimer;
	private Timer pokeMoveTimer;

	/**
	 * Constructor for the Pokeball class loads and displays the frame
	 */
	public Pokeball() {

		frame = new JFrame();
		actionPanel = new JPanel();
		playArea = new JPanel();
		winLabel = new JLabel();
		winLabel.setText("WINNER!!!");
		winLabel.setSize(600, 600);
		winLabel.setVisible(false);
		startButton = new JButton("start");
		startButton.addActionListener(new startButtonHandler());
		stopButton = new JButton("stop");
		stopButton.addActionListener(new stopButtonHandler());

		frame.setLayout(null);

		actionPanel.setSize(FRAMEWIDTH, 40);
		actionPanel.setLocation(0, 0);
		actionPanel.add(startButton);
		actionPanel.add(stopButton);

		playArea.setSize(FRAMEWIDTH, FRAMEHEIGHT - actionPanel.getHeight());
		playArea.setLocation(0, actionPanel.getHeight());
		playArea.setLayout(null);
		playArea.add(winLabel);

		frame.add(actionPanel);
		frame.add(playArea);
		frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myImageList = new ArrayList<>();
		myPokemonList = new ArrayList<>();
		myDummyList = new ArrayList<>();
		
		pokeAddTimer = new Timer(10, new AddTimer());
		pokeMoveTimer = new Timer(50, new MoveTimer());

		makeList();
	}

	/**
	 * The method checkCollisions, takes in a Pokemon as a parameter and checks 
	 * its bounds to the other Pokemon in myPokemonList
	 * @param Pokemon pokemon
	 */
	public void checkCollisions(Pokemon pokemon) {

		Rectangle r3 = pokemon.getBounds();

		for (Pokemon other : myPokemonList) {

			Rectangle r2 = other.getBounds();

			if ((r3.intersects(r2) || r3.contains(r2)) && pokemon != other) {

				pokemon.reverse();
				other.reverse();
				pokemon.incrementBounceCount();
				other.incrementBounceCount();
			}
		}
	}

	/**
	 * The method addActionListener, iterates over the List myPokemonList and 
	 * adds the ClickEvents ActionListener
	 */
	public void addActionListener() {

		for (Pokemon pokemon : myPokemonList) {
			pokemon.addActionListener(new ClickEvents(pokemon));
		}
	}

	/**
	 * The method makeList makes myImageList and myPokemonList from pokemon.txt
	 */
	public void makeList() {
		try {
			File file = new File("pokemon.txt");
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String s = scan.nextLine();
				ImageIcon image = new ImageIcon(s);
				myImageList.add(image);
				Pokemon p = new Pokemon(image);
				myPokemonList.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The private inner class startButtonHandler provides the action listener for
	 * the start button
	 */
	private class startButtonHandler implements ActionListener {
		/**
		 * This method starts the timer
		 * 
		 * @param e
		 *            the action event handled by this method
		 */
		public void actionPerformed(ActionEvent e) {
			pokeAddTimer.start();
			pokeMoveTimer.start();
		}
	}

	/**
	 * The private inner class stopButtonHandler provides the action listener for
	 * the stop button
	 */
	private class stopButtonHandler implements ActionListener {
		/**
		 * This method stops the timer
		 * 
		 * @param e
		 *            the action event handled by this method
		 */
		public void actionPerformed(ActionEvent e) {
			pokeAddTimer.stop();
			pokeMoveTimer.stop();
		}
	}

	/**
	 * The private inner class ClickEvents provides the action listener for
	 * the JButton clicks 
	 */
	private class ClickEvents implements ActionListener {

		private Pokemon pokemonClicks;

		public ClickEvents(Pokemon pokemonClicks) {

			this.pokemonClicks = pokemonClicks;
		}

		public void actionPerformed(ActionEvent e) {

			this.pokemonClicks.incrementClickCount();
			System.out.println(pokemonClicks.getClickCount() + " " + pokemonClicks.getRandomClickCount());

		}

	}

	/**
	 * The private inner class TimerListener provides the action listener for the
	 * swing Timer
	 */
	private class AddTimer implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			timer++;
			if (timer % 100 == 0) {

				if (myImageList.size() > 0) {
					Random rand = new Random();
					int random = 0;
					random = rand.nextInt(myImageList.size());
					Pokemon pokemon = new Pokemon(myImageList.get(random));
					pokemon.addActionListener(new ClickEvents(pokemon));
					myPokemonList.add(pokemon);

					playArea.add(pokemon);

					playArea.paintImmediately(playArea.getVisibleRect());
				} else {
					playArea.setBackground(Color.MAGENTA);
					winLabel.setVisible(true);
				}
			}

		}
	}

	/**
	 * The private inner class MoveTimer provides the action listener for the 
	 * swing Timer
	 */
	private class MoveTimer implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			myDummyList.clear();
			
			for (Pokemon pokemon : myPokemonList) {
				
				myDummyList.add(pokemon);

				pokemon.movePokemon();
				pokemon.bouncePokemon();

				checkCollisions(pokemon);

				if (pokemon.getClickCount() >= pokemon.getRandomClickCount()) {

					playArea.remove(pokemon);
					myDummyList.remove(pokemon);
					myImageList.remove(pokemon.getImageIcon());
				}

				else if (pokemon.getBounceCount() >= pokemon.getRandomBounceCount()) {

					playArea.remove(pokemon);
					myDummyList.remove(pokemon);
				}
			}

			myPokemonList.clear();
			if (myDummyList.size() > 0) {
				for (Pokemon dummies : myDummyList) {

					myPokemonList.add(dummies);

				}
			}
		}
	}
}
