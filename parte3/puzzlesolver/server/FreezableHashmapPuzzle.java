package puzzlesolver.server;

import java.util.concurrent.ExecutorService;
import puzzlesolver.core.ConcurrentHashmapPuzzle;

public class FreezableHashmapPuzzle extends ConcurrentHashmapPuzzle {

	public FreezableHashmapPuzzle(ExecutorService executor) {
		super(executor);
	}

	public FrozenArrayPuzzle freeze() {
		return new FrozenArrayPuzzle(this);
	}
	
}
