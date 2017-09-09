package bounce;

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
	
	// allow position and velocity to be set for flexibility
	public Paddle(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getSpriteSheet(BounceGame.BREAKOUT_PIECES_RSC, 352 ,200).getSubImage(50, 70, 60, 30));
		velocity = new Vector(vx, vy);
	}
	
	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}
	
}
