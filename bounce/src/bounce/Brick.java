package bounce;

import java.util.Random;

import jig.Entity;
import jig.ResourceManager;

public class Brick extends Entity {
	private int strength; // how many times to hit the brick
	private static int height = 15;
	private static int width = 70;
	
	public Brick(int x, int y, String type) {
		super(x, y);
		if (type == "RED") {
			addImageWithBoundingBox(ResourceManager
					.getSpriteSheet(BounceGame.BREAKOUT_PIECES_RSC, 1280 ,800).getSubImage(100, 80, width, height));
			this.strength = 1;
		} else if (type == "BLUE") {
			addImageWithBoundingBox(ResourceManager
					.getSpriteSheet(BounceGame.BREAKOUT_PIECES_RSC, 1280 ,800).getSubImage(480, 320, width, height));
			this.strength = 2;
		}
	}
	
	/*
	 *  checks if the brick should still be displayed
	 *  
	 *  @return boolean
	 *  True if the brick should still be displayed false otherwise.
	 */
	public boolean isAlive() {
		if (this.strength > 0) {
			return true;
		}
		return false;
	}
	
	/*
	 * Instantiates the bricks on the game
	 * 
	 * @param x
	 * How many blocks to draw on the x axis
	 * 
	 * @param y
	 * How many blocks to draw on the y axis
	 *
	 * @return Array[][]
	 * Returns a multi-dimensional array
	 */
	
	public static Brick[][] drawBlocks(int x, int y, int level) {
		Brick[][] brickMaze = new Brick[y][x];
		int blockX = 35;
		String c = "RED";
		int blockY = 60;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				Random random = new Random();
				if (level > 1 ) {
					String a = "BLUE";
					String b = "RED";
					c = random.nextBoolean() ? a : b;
				}
				brickMaze[i][j] = new Brick(blockX, blockY, c);
				blockX += width;
			}
			blockX = 35;
			blockY += height;
		}
		return brickMaze;
		
	}
	
	/**
	 * strength setter
	 */
	public void reduceStrength( ) {
		this.strength -= 1;
	}
}