package puzzlesolver.core;

import java.util.HashMap;

public class ConcurrentHashmapPuzzle extends HashmapPuzzle {
	
	private static void linkSE(IPuzzlePiece t, HashMap<String, IPuzzlePiece> map) throws MissingPiecesException {
		while (!t.isECol()) {
			if (!t.isSRow()) {
				IPuzzlePiece southNeighbour = map.get(t.getSouthId());
				// This is  ~O(1) for realistic inputs
				if (southNeighbour == null) {
					throw new MissingPiecesException();
					// The piece set is incomplete (or northId is wrong)
				}
				t.setSouth(southNeighbour);
			}

			IPuzzlePiece eastNeighbour = map.get(t.getEastId());
			// This is  ~O(1) for realistic inputs
			if (eastNeighbour == null) {
				throw new MissingPiecesException();
				// The piece set is incomplete (or northId is wrong)
			}
			t.setEast(eastNeighbour);
			
			t = t.getEast();
	
		}

		if (!t.isSRow()) {
			IPuzzlePiece southNeighbour = map.get(t.getSouthId());
			// This is  ~O(1) for realistic inputs
			if (southNeighbour == null) {
				throw new MissingPiecesException();
				// The piece set is incomplete (or northId is wrong)
			}
			t.setSouth(southNeighbour);
		}		
	}

	private static void linkColSE (IPuzzlePiece nWCorner, HashMap<String, IPuzzlePiece> map) throws MissingPiecesException {
		assert(nWCorner != null);
		while (!nWCorner.isSRow()) {
			linkSE(nWCorner, map);
			nWCorner = map.get(nWCorner.getSouthId());
		}
		linkSE(nWCorner, map);
	}
	
	public void solve() throws MissingPiecesException {
		assert(NWCorner != null);
		if (solved == false) {
			linkColSE(NWCorner, pieceHashMap);
		}
		solved = true;
	}

}
