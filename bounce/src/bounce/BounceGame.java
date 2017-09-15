package bounce;

import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A Simple Game of Bounce.
 * 
 * The game has three states: StartUp, Playing, and GameOver, the game
 * progresses through these states based on the user's input and the events that
 * occur. Each state is modestly different in terms of what is displayed and
 * what input is accepted.
 * 
 * In the playing state, our game displays a moving rectangular "ball" that
 * bounces off the sides of the game container. The ball can be controlled by
 * input from the user.
 * 
 * When the ball bounces, it appears broken for a short time afterwards and an
 * explosion animation is played at the impact site to add a bit of eye-candy
 * additionally, we play a short explosion sound effect when the game is
 * actively being played.
 * 
 * Our game also tracks the number of bounces and syncs the game update loop
 * with the monitor's refresh rate.
 * 
 * Graphics resources courtesy of qubodup:
 * http://opengameart.org/content/bomb-explosion-animation
 * 
 * Sound resources courtesy of DJ Chronos:
 * http://www.freesound.org/people/DJ%20Chronos/sounds/123236/
 * 
 * Breakout pieces courtesy of Buch:
 * https://opengameart.org/content/breakout-set
 * 
 * Slpash Screen Photo by Josh Calabrese on Unsplash
 * https://unsplash.com/photos/zcYRw547Dps
 * 
 * @author wallaces
 * @author peculiar_yak
 *  
 */
public class BounceGame extends StateBasedGame {
	
	public static final int SPLASHSCREEN = 0;
	public static final int STARTUPSTATE = 1;
	public static final int PLAYINGSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int MAX_LEVELS = 3;
	
	public static final String BREAKOUT_PIECES_RSC = "bounce/resource/breakout_pieces.png";
	public static final String BALL_BALLIMG_RSC = "bounce/resource/ball.png";
	public static final String BALL_BROKENIMG_RSC = "bounce/resource/brokenball.png";
	public static final String GAMEOVER_BANNER_RSC = "bounce/resource/gameover.png";
	public static final String STARTUP_BANNER_RSC = "bounce/resource/PressSpace.png";
	public static final String BANG_EXPLOSIONIMG_RSC = "bounce/resource/explosion.png";
	public static final String BANG_EXPLOSIONSND_RSC = "bounce/resource/explosion.wav";
	public static final String SPLASH_SCREEN_RSC = "bounce/resource/splashscreen.jpg";
	public static final int GAME_START_BOUNDARY = 60;

	public final int ScreenWidth;
	public final int ScreenHeight;
	
	private int score = 0;
	private int life = 30;
	private int highScore = 0;
	private int level = 1;

	ArrayList<Ball> ball = new ArrayList<Ball>();
	Paddle paddle;
	ArrayList<Bang> explosions;
	Brick[][] bricks;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public BounceGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		explosions = new ArrayList<Bang>(10);
		FileStore storage = new FileStore();
		this.highScore = storage.getHighScore();
				
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new SplashScreenState());
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		
		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.
		ResourceManager.loadSound(BANG_EXPLOSIONSND_RSC);	

		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(BALL_BALLIMG_RSC);
		ResourceManager.loadImage(BALL_BROKENIMG_RSC);
		ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		ResourceManager.loadImage(STARTUP_BANNER_RSC);
		ResourceManager.loadImage(BANG_EXPLOSIONIMG_RSC);
		ResourceManager.loadImage(BREAKOUT_PIECES_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_RSC);
		
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new BounceGame("Breakout!", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setShowFPS(false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Increments the score if a collision happens
	 */
	public void incrementScore() {
		this.score += 1;
	}
	
	/**
	 * reduce the life if the ball goes below the screen
	 */
	public void reduceLives() {
		this.life -= 1;
	}
	
	/**
	 * HighScore getter
	 * @return the high score
	 */
	public int getHighScore() {
		return this.highScore;
	}

	/**
	 * Player life getter
	 * @return the total number of lives left
	 */
	public int getLife() {
		return this.life;
	}
	
	/**
	 * The Player's score getter
	 * @return an integer that represents the Player's current score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * 
	 * Score setter
	 */
	public void setScore() {
		this.score = 0;
		
	}

	/**
	 * 
	 * Life setter
	 */
	public void setLife() {
		this.life = 3;
	}

	/**
	 * 
	 * High score setter
	 */
	public void updateHighScore() {
		FileStore storage = new FileStore();
		this.highScore = storage.getHighScore();
	}
	
	/**
	 * 
	 * Level setter
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * 
	 * Level getter
	 */
	public int getLevel() {
		return this.level;
	}
	
}
