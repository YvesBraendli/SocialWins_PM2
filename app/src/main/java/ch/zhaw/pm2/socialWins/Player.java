package ch.zhaw.pm2.socialWins;

import java.awt.Color;

import ch.zhaw.pm2.socialWins.strategy.Strategy;

public abstract class Player {
	private String name;
	private Color color;
	
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
}
