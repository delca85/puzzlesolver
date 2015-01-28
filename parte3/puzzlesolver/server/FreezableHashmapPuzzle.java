package puzzlesolver.server;

import java.util.concurrent.ExecutorService;
import puzzlesolver.core.ConcurrentHashmapPuzzle;

public class FreezableHashmapPuzzle extends ConcurrentHashmapPuzzle {

	public FreezableHashmapPuzzle(ExecutorService executor) {
		super(executor);
		// TODO Auto-generated constructor stub
	}

	public FrozenArrayPuzzle getFrozen() {
		return new FrozenArrayPuzzle(this);
	}
	
}
