package com.farrout.Pong.input;

import java.util.HashMap;

public class KeyMap {

	//HashMap is overkill for pong, but I might consider using keyMap later, in case I want players to be able to change their key mappings in other games
//	private HashMap<Integer, Value> KVPairs;
	private Value value;
	
	
	//Mutually exclusive values
	public enum Value {
		UP, DOWN, NONE
	}
	int upCode, downCode;
	
	/**
	 * Creates a KeyMap such that the given input key for up sets this maps value to up, and down sets to down
	 */
	public KeyMap(int upCode, int downCode) {

//		KVPairs = new HashMap<Integer, Value>();
//		KVPairs.put(upCode, Value.UP);
//		KVPairs.put(downCode, Value.DOWN);
		
		this.upCode = upCode;
		this.downCode = downCode;
		
		value = Value.NONE;
	}

	public Value getValue() {
		return value;
	}
	
	public void update() {
		
		//Up pressing takes precedence, if we are pressing down and we then press up, go up. I dont want to bother making it more complex, or having it stop.
		if (Keyboard.keys[upCode]) {
			value = Value.UP;
		} else if (Keyboard.keys[downCode]) {
			value = Value.DOWN;
		} else {
			value = Value.NONE;
		}
		
		
//		Value key = KVPairs.get(keyCode);
//		value = key != null ? key : Value.NONE;
		
	}
	
}
