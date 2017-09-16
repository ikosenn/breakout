package bounce;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.Vector;
import jig.ResourceManager;

/**
 * The Paddle class is an Entity that has a velocity (since it's moving). 
 * It is controlled by using arrow keys
 * 
 */

public class Paddle extends Entity {
	
	private Vector velocity;
	private float max_velocity = 9f; 
	
	// allow position and velocity to be set for flexibility
	public Paddle(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getSpriteSheet(BounceGame.BREAKOUT_PIECES_RSC, 1280 ,800).getSubImage(0, 418, 80, 15));
		velocity = new Vector(vx, vy);
	}
	
	public void setVelocity(float x, float y) {
		// restrict the max velocity of the paddle
		if (x == 0f) {
			velocity = new Vector(x, y);
		} else if (Math.abs(this.velocity.getX()) <= max_velocity) {
			velocity = this.getVelocity().add(new Vector(x, y));
		}
	}

	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * 
	 * @return false if it has reached the bounds of the game otherwise true
	 */
	private boolean checkCollision(char direction, BounceGame game) {
		if (this.getCoarseGrainedMaxX() >= game.ScreenWidth && direction == 'R') {
			this.setPosition(game.ScreenWidth - 40, this.getY());
			return false;
		}
		if (this.getCoarseGrainedMinX() <= 0 && direction == 'L') {
			this.setPosition(0 + 40, this.getY());
			return false;
		}
		return true;
	}
	
	/**
	 * Updates the position of the paddle based on the users input. 
	 * 
	 * @param container
	 * A generic game container that handles the game loop, fps recording and managing the input system 
	 * 
	 * @param game
	 * Holds the state of the game.
	 */
	public void update(GameContainer container, BounceGame game) throws SlickException {
		Input input = container.getInput();
		

		boolean moved = false;
		if ((input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) && this.checkCollision('L', game)) {
			// check if the paddle was moving to the right
			if (this.getVelocity().getX() > 0f) {
				// reset it
				this.setVelocity(0f, 0f);
			}
			this.setVelocity(-.2f, 0f);
			moved = true;
		}
		if ((input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) && this.checkCollision('R', game)){
			// check if the paddle was moving to the left
			if (this.getVelocity().getX() < 0f) {
				// reset it
				this.setVelocity(0f, 0f);
			}
			this.setVelocity(+.2f, 0f);
			moved = true;
		}
		translate(this.getVelocity());
		// reset velocity if no key is pressed
		if (!moved) {
			this.setVelocity(0f, 0f);
		}
			
	}
	
}
