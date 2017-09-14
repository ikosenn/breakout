package bounce;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
		container.setSoundOn(false);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		BounceGame bg = (BounceGame)game;
		
		bg.ball.render(g);
		bg.paddle.render(g);
		g.drawString("Score: " + bg.getScore(), 5, 10);
		g.drawString("Lives: " + bg.getLife(), 105, 10);
		g.drawString("High Score: " + bg.getHighScore(), 205, 10);
		
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

		BounceGame bg = (BounceGame)game;
		
		bg.ball.update(bg, delta);
		bg.paddle.update(container, bg);
		
		for (Iterator<Bang> i = bg.explosions.iterator(); i.hasNext();) {
			if (!i.next().isActive()) {
				i.remove();
			}
		}

	}

	@Override
	public int getID() {
		return BounceGame.PLAYINGSTATE;
	}	
}