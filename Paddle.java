package Pong;

import java.awt.*;
import java.awt.event.*;

/**
 * Paddle class
 * 
 * @author viktorlundberg
 */

public class Paddle extends Rectangle
{
	int id;
	int yVelocity;
	int speed = 10;

	/**
	 * Constructor
	 * 
	 * @param x,             coordinates, x dimension
	 * @param y,             coordinates, y dimenson
	 * @param PADDLE_WIDTH,  width of paddle
	 * @param PADDLE_HEIGHT, height of paddle
	 * @param id,            player 1 or player 2
	 */
	public Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id)
	{
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.id = id;
	}

	/**
	 * Depending on which key is pressed, move paddle 1 or paddle 2 up or down.
	 */
	public void keyPressed(KeyEvent e)
	{
		switch (id)
		{
		// player 1
		case 1:
			if (e.getKeyCode() == KeyEvent.VK_W)
			{
				setYDirection(-speed);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_S)
			{
				setYDirection(speed);
				move();
			}
			break;
		case 2:
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				setYDirection(-speed);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				setYDirection(speed);
				move();
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch (id)
		{
		// player 1
		case 1:
			if (e.getKeyCode() == KeyEvent.VK_W)
			{
				setYDirection(0);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_S)
			{
				setYDirection(0);
				move();
			}
			break;
		case 2:
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				setYDirection(0);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				setYDirection(0);
				move();
			}
			break;
		}
	}

	/**
	 * Sets direction of paddle, should it move up or down?
	 * 
	 * @param yDirection, up or down?
	 */
	public void setYDirection(int yDirection)
	{
		yVelocity = yDirection;
	}

	/**
	 * Performs one frame of movement
	 */
	public void move()
	{
		y = y + yVelocity;
	}

	public void draw(Graphics g)
	{
		// player 1
		if (id == 1)
		{
			g.setColor(Color.white);
		}
		if (id == 2)
		{
			g.setColor(Color.white);
		}
		g.fillRect(x, y, width, height);
	}

}
