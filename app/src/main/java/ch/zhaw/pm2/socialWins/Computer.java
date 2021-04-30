package ch.zhaw.pm2.socialWins;

import java.awt.Color;

import ch.zhaw.pm2.socialWins.strategy.ComputerAdvancedStrategy;
import ch.zhaw.pm2.socialWins.strategy.ComputerBeginnerStrategy;
import ch.zhaw.pm2.socialWins.strategy.ComputerIntermediateStrategy;
import ch.zhaw.pm2.socialWins.strategy.Strategy;

public class Computer extends Player {
	private Strategy strategy;

	public Computer(String name, Color color, int level) {
		super(name, color);
		setStrategy(level);
	}

	private void setStrategy(int level) {
		switch (level) {
		case 1:
			strategy = new ComputerBeginnerStrategy();
			break;
		case 2: 
			strategy = new ComputerIntermediateStrategy();
			break;
		case 3:
			strategy = new ComputerAdvancedStrategy();
			break;
			
		default:
			// throw new exception, invalid level.
		}
	}
}
