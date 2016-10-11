package com.farrout.Pong.util;

import java.util.Random;

public class MathUtils {

	private MathUtils(){
	}

	
	public static final double RAD75 = 75 * Math.PI / 180;
	public static final double RAD285 = -75 * Math.PI / 180;
	public static final double RAD15 = 15 * Math.PI / 180;
	public static final double RAD345 = -15 * Math.PI / 180;
	
	static Random random = new Random();
	
	public static int random1neg1() {
		return random.nextBoolean() ? -1 : 1;
	}
	
	public static double randDouble(double min, double max) {
		
		Double result = (random.nextDouble()) * (max - min) + min;
		return result;
	}
	
}
