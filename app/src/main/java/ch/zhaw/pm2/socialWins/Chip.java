package ch.zhaw.pm2.socialWins;

import java.awt.Color;

public class Chip {

	private Color color;
	
	public Chip(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Chip clone() {
		return new Chip(getColor());
	}
}
