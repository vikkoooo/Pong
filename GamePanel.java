package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * GamePanel, most of the functions of the game.
 * 
 * @author viktorlundberg
 *
 */

/**
 * @author viktorlundberg
 *
 */
public class GamePanel extends JPanel implements Runnable
{
	// Game size settings
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555)); // Ping pong table size ratio
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;

	public GamePanel()
	{
		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);

		gameThread = new Thread(this); // thread
		gameThread.start();
	}

	/**
	 * Creates an new instance of Ball and spawns the ball in the middle of the
	 * screen, and randomly along the Y axis
	 */
	public void newBall()
	{
		random = new Random();
		ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER),
				BALL_DIAMETER, BALL_DIAMETER);
	}

	/**
	 * Spawns paddles. paddle1 = left (player 1), paddle2 = right (player 2).
	 */
	public void newPaddles()
	{
		paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH,
				PADDLE_HEIGHT, 2);
	}

	/**
	 * Paints an image, this is what is currently showing. One frame.
	 */
	public void paint(Graphics g)
	{
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);

	}

	/**
	 * Updates the image with new frame according to ball and paddle positions
	 */
	public void draw(Graphics g)
	{
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		Toolkit.getDefaultToolkit().sync(); // adds smoothness
	}

	public void move()
	{
		paddle1.move();
		paddle2.move();
		ball.move();
	}

	/**
	 * Collision settings. One important note is that both Ball and Paddle extends
	 * Rectangle which allows us to use Rectangle.intersects() method to easier
	 * detect collision between ball and paddle
	 */
	public void checkCollision()
	{
		// Bounce ball off top & bottom window edges
		if (ball.y <= 0)
		{
			ball.setYDirection(-ball.yVelocity);
		}
		if (ball.y >= GAME_HEIGHT - BALL_DIAMETER)
		{
			ball.setYDirection(-ball.yVelocity);
		}

		// Bounce ball off paddle1
		if (ball.intersects(paddle1))
		{
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // Adds more speed to ball after collision
			if (ball.yVelocity > 0)
			{
				ball.yVelocity++; // Adds more speed to ball after collision
			}
			else
			{
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}

		// Bounce ball off paddle2
		if (ball.intersects(paddle2))
		{
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // Adds more speed to ball after collision
			if (ball.yVelocity > 0)
			{
				ball.yVelocity++; // Adds more speed to ball after collision
			}
			else
			{
				ball.yVelocity--;
			}
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}

		// Stops paddles at window edges, otherwise paddles can go outside of screen.
		if (paddle1.y <= 0)
		{
			paddle1.y = 0;
		}
		if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
		{
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		if (paddle2.y <= 0)
		{
			paddle2.y = 0;
		}
		if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
		{
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}

		// Give a player one point and create new paddles and ball
		if (ball.x <= 0)
		{
			score.player2++;
			newPaddles();
			newBall();
		}
		if (ball.x >= GAME_WIDTH - BALL_DIAMETER)
		{
			score.player1++;
			newPaddles();
			newBall();
		}

	}

	/**
	 * Game loop. Updates image (moving ball and paddles), checking for collision
	 */
	public void run()
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		boolean running = true;

		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1)
			{
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}

	/**
	 * ActionListener nested class checking if key is pressed
	 * 
	 * @author viktorlundberg
	 *
	 */
	public class AL extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}

		public void keyReleased(KeyEvent e)
		{
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}

}
