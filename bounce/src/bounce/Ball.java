package bounce;

import jig.Collision;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

/**
 * The Ball class is an Entity that has a velocity (since it's moving). When
 * the Ball bounces off a surface, it temporarily displays a image with
 * cracks for a nice visual effect.
 * 
 */
 class Ball extends Entity {

	private Vector velocity;

	public Ball(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getSpriteSheet(BounceGame.BREAKOUT_PIECES_RSC, 1280 ,800).getSubImage(18, 610, 12, 12));
		velocity = new Vector(vx, vy);
	}

	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}

	/**
	 * Bounce the ball off a surface. This simple implementation, combined
	 * with the test used when calling this method can cause "issues" in
	 * some situations. Can you see where/when? If so, it should be easy to
	 * fix!
	 * 
	 * @param surfaceTangent
	 */
	public void bounce(float surfaceTangent) {
		velocity = velocity.bounce(surfaceTangent);
	}

	/**
	 * Update the Ball based on how much time has passed...
	 * 
	 * @param delta
	 *            the number of milliseconds since the last update
	 */
	public void update(BounceGame bg, final int delta) {
		translate(velocity.scale(delta));
		
		
		// bounce the ball...
		boolean bounced = false;
		if (this.hitPaddle(bg.paddle)) {
			this.bounce(0);
			bounced = true;
		} else if (this.hitBrick(bg.bricks)) {
			bg.incrementScore();
			this.bounce(0);
			bounced = true;
		} else if (this.getCoarseGrainedMaxX() > bg.ScreenWidth
				|| this.getCoarseGrainedMinX() < 0) {
			this.bounce(90);
			bounced = true;
		} else if (this.getCoarseGrainedMaxY() > bg.ScreenHeight
				|| this.getCoarseGrainedMinY() < 0) {
			this.bounce(0);
			bounced = true;
		}
		 
		if (bounced) {
			bg.explosions.add(new Bang(this.getX(), this.getY()));
		}
		
	}
	
	/**
	 *  Checks if the ball hits the paddle. if so bounces the ball. 
	 *  
	 * @param paddle
	 * The entity to which we are checking against
	 * 
	 * @return true if a collision happens else return false
	 */
	public Boolean hitPaddle(Paddle paddle) {
		Collision isPen = this.collides(paddle); 
		if (isPen != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the ball collides with a block. if they collide we 
	 * bounce the ball away and also reduce the "strength" of the block
	 * @param bricks
	 * 
	 * @return true if the ball hits a brick else false
	 */
	public Boolean hitBrick(Brick[][] bricks) {
		for (int i=0; i < bricks.length; i++) {
			for (int j=0; j < bricks[i].length; j++) {
				// check for alive bricks only
				if (bricks[i][j].isAlive()) {
					Collision isPen = this.collides(bricks[i][j]); 
					if (isPen != null) {
						bricks[i][j].reduceStrength();
						// we can't have multiple collisions
						// return when we get one
					
						return true;
					}
				}
			}
		}
		return false;
	}
}
