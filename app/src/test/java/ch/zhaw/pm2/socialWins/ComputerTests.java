package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import ch.zhaw.pm2.socialWins.strategy.Strategy;
import static org.mockito.Mockito.*;

public class ComputerTests {
	private Board testComputer;
    private @Mock Strategy strategy;
	
	@BeforeEach
	public void setup() {
		strategy = mock(Strategy.class);
	}
	
	
}
