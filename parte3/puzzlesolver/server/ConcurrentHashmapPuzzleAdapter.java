package puzzlesolver.server;

import java.util.concurrent.ExecutorService;

import puzzlesolver.core.ConcurrentHashmapPuzzle;

public class ConcurrentHashmapPuzzleAdapter extends ConcurrentHashmapPuzzle {
	public ConcurrentHashmapPuzzleAdapter() {
		super(null);
	}

	public ConcurrentHashmapPuzzleAdapter(ExecutorService executor) {
		super(executor);
	}

}
