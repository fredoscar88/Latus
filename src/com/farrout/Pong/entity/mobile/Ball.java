package com.farrout.Pong.entity.mobile;

import java.io.File;

import com.farrout.Pong.Game;
import com.farrout.Pong.Board.Board;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.graphics.ui.UILabel;
import com.farrout.Pong.graphics.ui.UIPanel;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Ball extends Mob {

	private double theta;	//Direction in degrees
	private double speed;
	private double speedIncrement = .1;
	public static final double maxSpeed = 4;//2;
	private double nx, ny;
	public static int size = 5;
	
	private File hitSound = new File("res/sound/wallhit.wav");
	
	private UILabel speedLabel;
	
	public Ball(int x, int y, int color) {
		this(x, y, color, Math.PI / 3, 2.0);
	}
	
	public Ball(int x, int y, int color, double theta) {
		this(x, y, color, theta, 2.0);
	}
	
	public Ball(int x, int y, int color, double theta, double speed) {
		this.color = color;
		position = new Vector2d(x, y);
		dimension = new Vector2i(size, size);
		heading = new Vector2d(0,0);
		this.theta = theta;
		this.speed = speed;
		updateHeading();
		
		speedLabel = new UILabel(new Vector2i(100, 42), "Ball Speed: ");
		
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
	public void updateTheta(double theta) {
		this.theta = theta;
	}
	
	public void addSpeed(double amt) {
		collisions++;
		if (collisions % 10 == 0) {
			
			if (speed < maxSpeed) {
				speed += amt;
			} else if (speed > maxSpeed) {
				speed = maxSpeed;
			}
			updateTheta();
			updateHeading();
		}
	}
	
	int collisions = 0;
	boolean breakflag;
	public void move(double nx, double ny) {
		
		while (nx != 0) {	//when nx=0 we aren't moving anymore
			if (Math.abs(nx) > 1) {	//basically, can we move nx 1 closer to 0? we can use (nx - abs(nx) > 0 OR math.abs(nx) > 1
				if (!allCollide(abs(nx), 0)) {	//if we're not colliding into the tiles we're MOVING to
					position.x += abs(nx);
				}
				else {
					return;
				}
				nx -= abs(nx);	//nx can sometimes be negative- our job tho is to get it closer to 0.
								//if nx is positive sub 1. if negative, sub neg 1 (add 1), both bring it closer to zero.
				
			} else {
				
				if (!allCollide(abs(nx), 0)) {
					position.x += nx;
				} else {
					return;
				}
				nx = 0;
			}
		}
		while (ny != 0 ) {
			if (Math.abs(ny) > 1) {
				if (!allCollide(0, abs(ny))) {
					position.y += abs(ny);
				} else {
					return;
				}
				ny -= abs(ny);	
			} else {
				
				if (!allCollide(0, abs(ny))) {
					position.y += ny;
				} else {
					return;
				}
				ny = 0;
			}
		}
		
	}
	
	//Temporary for viewing collision regions. Does not work perfectly sadly. TODO remove
	//I will say though the collision is working
	int madx1, mady1, madx2, mady2;
	
	public boolean allCollide(double x, double y) {
		
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

			if (board.entCollide(xci, yci, this)) {

				Paddle p = board.paddleAt(xci, yci);
				if (p != null) updateTheta(p.bounceOffAngle(xci, yci));
				addSpeed(speedIncrement);
				updateHeading();
				Game.playSound(hitSound);
				
				return true;
			}
			if (board.elemCollide(xci, yci)) {
				
				if (x == 0) {
					this.ny *= -1;
				} else if (y == 0) {
					this.nx *= -1;
				}
				updateTheta();
				updateHeading();
				Game.playSound(hitSound);
				
				return true;
			}
		 	
		}
		return false;
	}
	@Deprecated
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
	@Deprecated
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

			if (board.entCollide(xci, yci, this)) {
				collide = true;
			}
		 	
		}
		return collide;
	}
	
	private int abs(Double d) {
		if (d < 0.0) return -1;
		if (d == 0.0) return 0;
		return 1;
	}
	
	//Collision and basically ALL of this stuff needs to go in the move method. we should just give it the xd, yd (the delta for our current position) and let it figure the rest out.
	public void update() {
		move(nx, ny);
		updateTheta();	//updates angle based on any changes to nx, ny
		
		speedLabel.setText("Ball Speed: " + Double.toString(speed) + " " + Double.toString(collisions));
		
	}
	
	public void render(Screen screen) {
		screen.renderMob(this);
		screen.renderPoint((int) position.x, (int) position.y, 0xFF00FF);
		screen.renderPoint(madx1, mady1, 0xFFFFFF);
		screen.renderPoint(madx1, mady2, 0xFFFFFF);
		screen.renderPoint(madx2, mady1, 0xFFFFFF);
		screen.renderPoint(madx2, mady2, 0xFFFFFF);
	}
	
	public void init(Board b, UIPanel uiPanel) {
		super.init(b, uiPanel);
		topPanel.addComponent(speedLabel);
	}
		
}
