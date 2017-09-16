package bounce;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import jig.Collision;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


/**
 * this is a special class that represents a ball that can break multiple blocks
 * 
 * @author peculiaryak
 *
 */
public class PowerUp extends Entity {
	private Vector velocity;
	
	public PowerUp(float x, float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getSpriteSheet(BounceGame.BREAKOUT_PIECES_RSC, 1280 ,800).getSubImage(18, 130, 12, 12));
		velocity = new Vector(0f, 0f);
	}
	
	/**
	 * set the velocity of the power up
	 * @param y the y value for the velocity
	 */
	public void setVelocity() {
		velocity = new Vector(0f, -1f);
	}
	
	/**
	 * velocity getter
	 * 
	 * @return Vector the velocity
	 */
	public Vector getVelocity() {
		return velocity;
	}

	/*
	 * checks collision of the blocks and the powerup ball
	 * 
	 * @param game
	 * Holds the state of the game.
	 * 
	 * @param bricks
	 * the bricks in the game
	 */
	public boolean checkCollision(Brick[][] bricks, BounceGame game) {
		for (int i=0; i < bricks.length; i++) {
			for (int j=0; j < bricks[i].length; j++) {
				// check for alive bricks only
				if (bricks[i][j].isAlive()) {
					Collision isPen = this.collides(bricks[i][j]); 
					if (isPen != null) {
						this.destroyBricks(j, game);	
						return true;
					}
				}
			}
		}
		if (this.getCoarseGrainedMinY() <=0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Destroys all bricks in the given row.
	 * 
	 * @param j columns to remove bricks from
	 * 
	 * * @param game
	 * Holds the state of the game.
	 */
	private void destroyBricks(int j, BounceGame game) {
		int score;
		game.explosions.add(new Bang(this.getX(), this.getY()));
		for (int i=0; i < game.bricks.length; i++) {
			if (game.bricks[i][j].isAlive()) {
				score = game.bricks[i][j].nukeBrick();
				game.incrementScore(score);
			}
		}
		
	}

	/*
	 * sets the velocity of the powerup
	 */
	public void fire() {
		this.setVelocity();
	}

	/*
	 * @param container
	 * A generic game container that handles the game loop, fps recording and managing the input system 
	 * 
	 * @param game
	 * Holds the state of the game.
	 * 
	 * @returns boolean. True to allow the play state to delete the element else false
	 */
	public boolean update(BounceGame game, GameContainer container, int delta) {
		Input input = container.getInput();
		translate(velocity.scale(delta));
		// get power-up
		if (this.velocity.getX() == 0 && this.getVelocity().getY() == 0) {
			game.powerUp.setPosition(game.paddle.getX() - 15, game.paddle.getY() - 15);
		}

		if (input.isKeyDown(Input.KEY_P)) {
			this.fire();
		}
		
		
		if (this.checkCollision(game.bricks, game)) {
			return true;
		}
		return false;
		
	}
	
}
