package puzzlesolver.server;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

import puzzlesolver.core.ConcurrentHashmapPuzzle;

public class SerializableConcurrentHashmapPuzzle extends
		ConcurrentHashmapPuzzleAdapter implements Serializable {

	public SerializableConcurrentHashmapPuzzle(ExecutorService executor) {
		super(executor);
	}
	
}
