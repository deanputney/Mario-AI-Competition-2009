package com.reddit.programming.mario;

import ch.idsia.mario.environments.Environment;

public class Sensors {
	private Environment latestObservation;
	private String[][] scene;
	private int[] marioPosition = null;
	public byte[][] levelScene;
	public byte[][] enemiesScene;
	public int fireballsOnscreen;
	
	public void updateReadings(Environment observation) {
		levelScene = observation.getLevelSceneObservation();
//		float[] marioPos = observation.getMarioFloatPos();
//		float[] enemiesPos = observation.getEnemiesFloatPos();
		enemiesScene = observation.getEnemiesObservation();
		
		String[][] scene = new String[Environment.HalfObsWidth*2][Environment.HalfObsHeight*2];
		fireballsOnscreen = 0;

		latestObservation = observation;
		for (int y = 0; y < levelScene.length; ++y)
			for (int x = 0; x < levelScene[0].length; ++x)
				scene[y][x] = asciiLevel(levelScene[y][x]);
		for (int y = 0; y < enemiesScene.length; ++y)
			for (int x = 0; x < enemiesScene[0].length; ++x){
				byte enemy = enemiesScene[y][x];
				if (enemy == BLANK)
					continue;
				if (enemy == MARIO)
					marioPosition = new int[]{y,x};
				if (enemy == FIREBALL)
					fireballsOnscreen++;
				scene[y][x] = asciiEnemy(enemy);
			}

	}
	
	public int[] getMarioPosition() {
		return marioPosition;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String[] sceneRow : scene){
			for(String square : sceneRow)
				sb.append(square + " ");
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public boolean isDangerous(byte enemy) {
		switch(enemy) {
			case MARIO:
			case BLANK:
			case FIREFLOWER:
			case FIREBALL: return false;
			default: return true;
		}
	}
	
	public final static int EMPTY = 0;
	public final static int COIN = 34;
	public final static int SOLID = -10;
	public final static int CANNON = 46;
	public final static int PLATFORM = -11;
	public final static int PIPE = 20;
	public final static int COIN_QUESTIONMARK_BOX = 21;
	public final static int ITEM_QUESTIONMARK_BOX = 22;
	public final static int COIN_BRICK = 17;
	public final static int ITEM_BRICK = 18;
	public final static int BRICK = 16;
	public final static int EDGE_BRICK = -12;
	
	public final static int MARIO = 1;
	private String asciiLevel(byte levelSquare) {
		switch(levelSquare) {
			case EMPTY: return " ";
			case COIN: return "O";
			case CANNON: 
			case EDGE_BRICK:
			case SOLID: return "X";
			case PLATFORM: return "-";
			case PIPE: return "P";
			case BRICK: return "B";
			case COIN_QUESTIONMARK_BOX:
			case ITEM_QUESTIONMARK_BOX:
			case COIN_BRICK:
			case ITEM_BRICK: return "?";
			case MARIO: return "M";
			default: return ""+levelSquare;
		}
	}
	
	public final static int BLANK = -1;
	public final static int GOOMBA = 2;
	public final static int WINGED_GOOMBA = 3;
	public final static int RED_KOOPA_TROOPA = 4;
	public final static int GREEN_KOOPA_TROOPA = 6;
	public final static int GREEN_PARA_TROOPA = 7;
	public final static int BULLET_BILL = 8;
	public final static int PIRANHAPLANT = 12;
	public final static int TROOPA_SHELL = 13;
	public final static int SPIKEY = 9;
	public final static int FIREFLOWER = 15;
	public final static int FIREBALL = 25;
	private String asciiEnemy(byte enemySquare) {
		switch(enemySquare) {
			case MARIO: return "M";
			case GOOMBA: return "G";
			case RED_KOOPA_TROOPA:
			case GREEN_KOOPA_TROOPA: return "K";
			case WINGED_GOOMBA:
			case GREEN_PARA_TROOPA: return "W";
			case TROOPA_SHELL: return "D";
			case SPIKEY: return "S";
			case BULLET_BILL: return "<";
			case PIRANHAPLANT: return "V";
			case FIREFLOWER: return "F";
			case FIREBALL: return "*";
			default: return ""+enemySquare;
		}
	}
}