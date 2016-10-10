package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.Game;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Ball extends Mob {

	private double theta;	//Direction in degrees
	private double speed = 1;
	private double nx, ny;
	
	public Ball(int x, int y, int color) {
//		this.color = color;
//		position = new Vector2d(x, y);
//		dimension = new Vector2i(5, 5);
//		heading = new Vector2d(0,0);
//		theta = Math.PI / 3;
//		updateHeading();
		this(x, y, color, Math.PI / 3);
	}
	
	public Ball(int x, int y, int color, double theta) {
		this.color = color;
		position = new Vector2d(x, y);
		dimension = new Vector2i(5, 5);
		heading = new Vector2d(0,0);
		this.theta = theta;
		updateHeading();
	}
	
	public Ball() {
		
		this(5, 50, 0x22CC33);
	}
	
	public void updateHeading() {
		//theta = (theta + Math.PI / 4);
		nx = speed * Math.cos(theta);
		ny = speed * Math.sin(theta);
		
	}
	
	public void move(double nx, double ny) {
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
		
		//invert this.ny or w/e
		/*
		if (collide(0, ny)) {

			ny *= -1;
		} else {
			position.y += ny;
			
		}*/

		
	}
	
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
			if (board.elemCollide(xci, yci)) collide = true;
		 	
		}
		return collide;
		
	}
	
	private int abs(Double d) {
		if (d < 0) return -1;
		return 1;
	}
	
	//Collision and basically ALL of this stuff needs to go in the move method. we should just give it the xd, yd (the delta for our current position) and let it figure the rest out.
	public void update() {
		move(nx, ny);
	}
	
	public void render(Screen screen) {
		screen.renderMob(this);
//		screen.renderPoint((int) position.x, (int) position.y, 0xFF00FF);
		screen.renderPoint(madx1, mady1, 0xFFFFFF);
		screen.renderPoint(madx1, mady2, 0xFFFFFF);
		screen.renderPoint(madx2, mady1, 0xFFFFFF);
		screen.renderPoint(madx2, mady2, 0xFFFFFF);
	}
		
}