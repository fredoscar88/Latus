package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.util.MathUtils;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Ball extends Mob {

	private double theta;	//Direction in degrees
	private double speed = .5;
	public static final double maxSpeed = .5;
	private double nx, ny;
	public static int size = 5;
	
	public Ball(int x, int y, int color) {
		this(x, y, color, Math.PI / 3, 1);
	}
	
	public Ball(int x, int y, int color, double theta) {
		this(x, y, color, theta, 1);
	}
	
	public Ball(int x, int y, int color, double theta, double speed) {
		this.color = color;
		position = new Vector2d(x, y);
		dimension = new Vector2i(size, size);
		heading = new Vector2d(0,0);
		this.theta = theta;
		this.speed = speed;
		updateHeading();
	}
	
	public Ball() {
		
		this(5, 50, 0x22CC33);
	}
	
	public void updateHeading() {
		//theta = (theta + Math.PI / 4);
		nx = speed * Math.cos(theta);
		ny = speed * -Math.sin(theta);
		//System.out.println(theta * 180/Math.PI);
		
	}
	public void updateTheta() {
		theta = Math.atan2(-ny, nx);
	}
	
	int collisions = 0;
	boolean breakflag;
	public void move(double nx, double ny) {
		double nxx = nx;
		double nyy = ny;
		Paddle p;
		
		while (nx != 0) {	//when nx=0 we aren't moving anymore
			if (Math.abs(nx) > 1) {	//basically, can we move nx 1 closer to 0? we can use (nx - abs(nx) > 0 OR math.abs(nx) > 1
				if (!entCollide(abs(nx), 0)) {	//if we're not colliding into the tiles we're MOVING to
					position.x += abs(nx);
				}
				nx -= abs(nx);	//nx can sometimes be negative- our job tho is to get it closer to 0.
								//if nx is positive sub 1. if negative, sub neg 1 (add 1), both bring it closer to zero.
				
			} else {
				
				if (!entCollide(abs(nx), 0)) {
					position.x += nx;
				} else {
					p = board.paddleAt(((int) position.x) + abs(nx), ((int) position.y) + abs(ny));
					if (p != null) theta = p.bounceOffAngle(((int) position.x) + abs(nx), ((int) position.y) + abs(ny));
//					theta = Math.toRadians(90 + 90 * abs(nx));
					updateHeading();
				}
				nx = 0;
			}
		}
		while (ny != 0 ) {
			if (Math.abs(ny) > 1) {
				if (!entCollide(0, abs(ny))) {
					position.y += abs(ny);
				}
				ny -= abs(ny);	
			} else {
				
				if (!entCollide(0, abs(ny))) {
					position.y += ny;
				} else {
					p = board.paddleAt(((int) position.x) + abs(nx), ((int) position.y) + abs(ny));
					if (p != null) theta = p.bounceOffAngle(((int) position.x) + abs(nx), ((int) position.y) + abs(ny));
					//theta = Math.toRadians(0 + 90 * abs(ny));
					updateHeading();
				}
				ny = 0;
			}
		}
		
		//Reset since we're checking a new collision
		nx = nxx;
		ny = nyy;
		
		while (nx != 0) {	//when nx=0 we aren't moving anymore
			if (Math.abs(nx) > 1) {	//basically, can we move nx 1 closer to 0? we can use (nx - abs(nx) > 0 OR math.abs(nx) > 1
				if (!collide(abs(nx), 0)) {	//if we're not colliding into the tiles we're MOVING to
					position.x += abs(nx);
				}
				nx -= abs(nx);	//nx can sometimes be negative- our job tho is to get it closer to 0.
								//if nx is positive sub 1. if negative, sub neg 1 (add 1), both bring it closer to zero.
				
			} else {
				
				if (!collide(abs(nx), 0)) {
					position.x += nx;
				} else {
					this.nx *= -1;
				}
				nx = 0;
			}
		}
		while (ny != 0) {
			if (Math.abs(ny) > 1) {
				if (!collide(0, abs(ny))) {
					position.y += abs(ny);
				}
				ny -= abs(ny);	
			} else {
				
				if (!collide(0, abs(ny))) {
					position.y += ny;
				} else {
					this.ny *= -1;
				}
				ny = 0;
			}
		}
		
		
//		double entityBounceTheta = board.entCollide(position.x + abs(nxx), position.y + abs(nyy), theta, this);
//		if (theta != entityBounceTheta) {
//			theta = entityBounceTheta;
//			updateHeading();
//		}
	}
	
	//Temporary for viewing collision regions. Does not work perfectly sadly. TODO remove
	//I will say though the collision is working
	int madx1, mady1, madx2, mady2;
	
	public boolean collide(double x, double y) {
		
		boolean collide = false;
		madx1 = (int) Math.ceil((((0 % 2) * (dimension.x - 1)) + position.x) + x);
		madx2 = (int) Math.floor((((1 % 2) * (dimension.x - 1)) + position.x) + x);
		mady1 = (int) Math.ceil((((0 / 2) * (dimension.y - 1)) + position.y) + y - 1);
		mady2 = (int) Math.floor((((2 / 2) * (dimension.y)) + position.y) + y - 1);
		
		for (int c = 0; c < 4; c++) {
			double xc = (((c % 2) * (dimension.x) - 1) + position.x) + x;
			double yc = (((c / 2) * (dimension.y) - 1) + position.y) + y;
			int xci = (int) Math.floor(xc);
			int yci = (int) Math.floor(yc);
			if (c % 2 == 0) xci = (int) Math.ceil(xc);	//when (c % 2 == 0), x is in the left most position
		 	if (c / 2 == 0) yci = (int) Math.ceil(yc);	//when (c / 2 == 0), y is in the up most position WE'D CHANGE THESE TO ONE IF WE USED FLOOR INSTEAD OF CEIL ABOVE
//			int xci = (int) xc;
//			int yci = (int) yc;
			//if (board.elemCollide(xci, yci)) collide = true;
			if (board.elemCollide(xci, yci)) {
				collide = true;
			}
		 	
		}
		return collide;
	}
	public boolean entCollide(double x, double y) {
		
		boolean collide = false;
		madx1 = (int) Math.ceil((((0 % 2) * (dimension.x - 1)) + position.x) + x);
		madx2 = (int) Math.floor((((1 % 2) * (dimension.x - 1)) + position.x) + x);
		mady1 = (int) Math.ceil((((0 / 2) * (dimension.y - 1)) + position.y) + y - 1);
		mady2 = (int) Math.floor((((2 / 2) * (dimension.y)) + position.y) + y - 1);
		
		for (int c = 0; c < 4; c++) {
			double xc = (((c % 2) * (dimension.x) - 1) + position.x) + x;
			double yc = (((c / 2) * (dimension.y) - 1) + position.y) + y;
			int xci = (int) Math.floor(xc);
			int yci = (int) Math.floor(yc);
			if (c % 2 == 0) xci = (int) Math.ceil(xc);	//when (c % 2 == 0), x is in the left most position
		 	if (c / 2 == 0) yci = (int) Math.ceil(yc);	//when (c / 2 == 0), y is in the up most position WE'D CHANGE THESE TO ONE IF WE USED FLOOR INSTEAD OF CEIL ABOVE
//			int xci = (int) xc;
//			int yci = (int) yc;
			//if (board.elemCollide(xci, yci)) collide = true;
			if (board.entCollide(xci, yci, this)) {
				collide = true;
				collisions+=2;
			}
		 	
		}
		return collide;
	}
	
	private int abs(Double d) {
		if (d < 0.0) return -1;
		if (d == 0.0) return 0;
		return 1;
	}
	
	int time;
	//Collision and basically ALL of this stuff needs to go in the move method. we should just give it the xd, yd (the delta for our current position) and let it figure the rest out.
	public void update() {
		move(nx, ny);
		updateTheta();	//updates angle based on any changes to nx, ny
		//if (collisions > 20) remove();
		collisions--;
		if (collisions < 0) collisions = 0;
		time++;
		
		//Update speed after a random amount of collisions TODO change speed update
		if (time % 60 == 0) {
			speed += .1;
			
			if (speed < maxSpeed) {
				speed += .1;
			} else if (speed > maxSpeed) {
				speed = maxSpeed;
			}
			updateTheta();
			updateHeading();
		}
		
	}
	
	public void render(Screen screen) {
		screen.renderMob(this);
		screen.renderPoint((int) position.x, (int) position.y, 0xFF00FF);
//		screen.renderPoint(madx1, mady1, 0xFFFFFF);
//		screen.renderPoint(madx1, mady2, 0xFFFFFF);
//		screen.renderPoint(madx2, mady1, 0xFFFFFF);
//		screen.renderPoint(madx2, mady2, 0xFFFFFF);
	}
		
}
