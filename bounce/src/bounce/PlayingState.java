package bounce;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * This state is active when the Game is being played. In this state, sound is
 * turned on, the bounce counter begins at 0 and increases until 10 at which
 * point a transition to the Game Over state is initiated. The user can also
 * control the ball using the WAS & D keys.
 * 
 * Transitions From StartUpState
 * 
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		BounceGame bg = (BounceGame)game;
		container.setSoundOn(true);
		
		if (bg.getLevel() == 1) {
			bg.ball.add(new Ball(bg.ScreenWidth / 2, 575f, bg.getLevel()));
		} else if (bg.getLevel() == 2 ) {
			bg.ball.add(new Ball(bg.ScreenWidth / 2, 575f, bg.getLevel()));
		} else if (bg.getLevel() == 3 ) {
			bg.ball.add(new Ball((bg.ScreenWidth / 2) - 30, 575f, bg.getLevel()));
			bg.ball.add(new Ball((bg.ScreenWidth / 2) + 30, 575f, bg.getLevel()));
		}
		bg.paddle = new Paddle(bg.ScreenWidth / 2, 590, .0f, .0f);
		bg.bricks = Brick.drawBlocks(12, 4, bg.getLevel());
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		BounceGame bg = (BounceGame)game;
		
		for (Ball ball: bg.ball) {
			ball.render(g);
		}
		bg.paddle.render(g);
		g.drawString("Level: " + bg.getLevel(), 5, 10);
		g.drawString("Score: " + bg.getScore(), 105, 10);
		g.drawString("Lives: " + bg.getLife(), 205, 10);
		g.drawString("High Score: " + bg.getHighScore(), 305, 10);
		
		for (Bang b : bg.explosions)
			b.render(g);
		
		for(int row=0; row < bg.bricks.length; row++) {
            for(int col=0; col < bg.bricks[row].length; col++) {
            		// draw alive bricks only 
            		if (bg.bricks[row][col].isAlive()) {
            			bg.bricks[row][col].render(g);
            		}
            }
        }
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();		
		
		int bricksLeft = 0;
		BounceGame bg = (BounceGame)game;
		
		for (Ball ball: bg.ball) {
			ball.update(bg, container, delta);
		}
		
		bg.paddle.update(container, bg);
		
		for (Iterator<Bang> i = bg.explosions.iterator(); i.hasNext();) {
			if (!i.next().isActive()) {
				i.remove();
			}
		}
		
		for(int row=0; row < bg.bricks.length; row++) {
            for(int col=0; col < bg.bricks[row].length; col++) {
            		// draw alive bricks only 
            		if (bg.bricks[row][col].isAlive()) {
            			bricksLeft += 1;
            		}
            }
        }
		if (bricksLeft == 0 && bg.getLevel() == BounceGame.MAX_LEVELS) {
			game.enterState(BounceGame.GAMEOVERSTATE);
		} else if(bricksLeft == 0) {
			bg.setLevel(bg.getLevel() + 1);
			game.enterState(BounceGame.STARTUPSTATE);
		}
		if (bg.getLife() <= 0) {
			FileStore storage = new FileStore();
			storage.saveHighScore(bg.getScore());
			game.enterState(BounceGame.GAMEOVERSTATE);
		}
		
		// cheat codes
		
		if (input.isKeyDown(Input.KEY_Z)) {
			bg.setLevel(2);
			game.enterState(BounceGame.STARTUPSTATE);
		}
		
		if (input.isKeyDown(Input.KEY_X)) {
			bg.setLevel(3);
			game.enterState(BounceGame.STARTUPSTATE);
		}


	}

	@Override
	public int getID() {
		return BounceGame.PLAYINGSTATE;
	}	
}