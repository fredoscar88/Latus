package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Paddle extends Mob {

	private double nx, ny;
	private double speed = 1;
	private int width = 24;
	private int height = 24;
	
	public Paddle(int x, int y, int color) {
		this.color = color;
		position = new Vector2d(x, y);
		dimension = new Vector2i(width, height);
		heading = new Vector2d(0,0);
	}
	
	public void render(Screen screen) {
		screen.renderMob(this);
	}
	
	public void update() {
//		System.out.println(position.x + " " + dimension.x + " " + (position.x + dimension.x));
	}
	
	public double bounceOffAngle(double x, double y) {
		Double theta = 0.0;
		double relXPos = x - position.x;
		double relYPos = y - position.y;
		double xHalf = ((double) width) / 2;	//this is not going to be perfect due to using doubles :I
		double yHalf = ((double) height) / 2;	//yeah... lol who'da thunk
		double distFromHalf = 0;
		
		double maxAngle = 5 * Math.PI / 12;	//75
//		double maxAngle = Math.PI / 2;	//90
		
		//Determine which side the x, y are closest to, giving preference to the left/right sides
		//But first we must contain the x and y
		if (contains(x, y)) {
			
			System.out.println(x + " " + (position.x + width) );
			if ((int) x == (int) (position.x + width - 1)) {	//Right edge
				if (relYPos < yHalf) {	//Upper half of right edge
					distFromHalf = yHalf - relYPos;
					
				}
				else if (relYPos > yHalf) {	//Lower half of right edge, or center. The = inclusion can be on either.
					distFromHalf = yHalf - relYPos;
				}
				System.out.println("RIGHT " + distFromHalf + " " + yHalf);
				theta = (distFromHalf / yHalf) * maxAngle;
			} else if ((int) x == (int) (position.x)) {	//Left edge
				if (relYPos < yHalf) {	//Upper half of right edge
					distFromHalf = relYPos - yHalf;	//Flipped, because we are on the other side (measures are all reversed)
				}
				else if (relYPos >= yHalf) {	//Lower half of right edge, or center. The = inclusion can be on either.
					distFromHalf = relYPos - yHalf;
				}
				System.out.println("LEFT " + distFromHalf + " " + yHalf);
				theta = Math.PI + (distFromHalf / yHalf) * maxAngle;
			} else if ((int) y == (int) (position.y + height - 1)) {	//Bottom
				
				if (relXPos < xHalf) {	//Upper half of right edge
					distFromHalf = relXPos - xHalf;
					
				}
				else if (relXPos > xHalf) {	//Lower half of right edge, or center. The = inclusion can be on either.
					distFromHalf = relXPos - xHalf;
				}
				System.out.println("BOTTOM " + distFromHalf + " " + xHalf);
				theta = (distFromHalf / xHalf) * maxAngle - (Math.PI / 2);
				
			} else if ((int) y == (int) (position.y)) {	//TOP
				
				if (relXPos < xHalf) {	//Upper half of right edge
					distFromHalf = xHalf - relXPos;	//again flipped. heh. We should just TODO multiply the one we use for the other y by negative 1. 
					
				}
				else if (relXPos > xHalf) {	//Lower half of right edge, or center. The = inclusion can be on either.
					distFromHalf = xHalf - relXPos;
				}
				System.out.println("TOP " + distFromHalf + " " + xHalf);
				theta = (distFromHalf / xHalf) * maxAngle + (Math.PI / 2);
				
			}
			
			return theta;
		}
		else {
			return 0;
		}
		
	}
	
}
