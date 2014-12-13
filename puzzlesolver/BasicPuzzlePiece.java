package puzzlesolver;

public class BasicPuzzlePiece implements PuzzlePiece {
	
	private enum Direction {
		NORTH, 
		SOUTH, 
		WEST, 
		EAST;
	}
	
	private final String kEmptyId = "VUOTO";
	
	private final String[] neighboursId = new String[4];
	private PuzzlePiece[] neighbours = new PuzzlePiece[4];
	private String id;
	private char character;
	
	public BasicPuzzlePiece(String id, char character, String northId, String southId, String westId, String eastId) {
		this.id = id;
		this.character = character;
		
		neighboursId[Direction.NORTH.ordinal()] = northId;
		neighboursId[Direction.SOUTH.ordinal()] = southId;
		neighboursId[Direction.WEST.ordinal()] = westId;
		neighboursId[Direction.EAST.ordinal()] = eastId;
		
		
		for (int i=0; i<4; i++) {
			for (int j=i+1; j<4; j++) {
				if (neighboursId[i].equals(neighboursId[j]) &&
					!neighboursId[i].equals(kEmptyId)) {
					throw new IllegalArgumentException();
				}
			}
		}
	}
	
	public String getId() {
		return id;
	};
	
	public PuzzlePiece getNorth() {
		return neighbours[Direction.NORTH.ordinal()];
	};
	
	public PuzzlePiece getSouth() {
		return neighbours[Direction.SOUTH.ordinal()];
	};
	
	public PuzzlePiece getWest() {
		return neighbours[Direction.WEST.ordinal()];
	};
	
	public PuzzlePiece getEast() {
		return neighbours[Direction.EAST.ordinal()];
	};
	
	public void setNorth(PuzzlePiece p) {
		neighbours[Direction.NORTH.ordinal()] = p;
	};
	public void setSouth(PuzzlePiece p) {
		neighbours[Direction.SOUTH.ordinal()] = p;
	};
	public void setWest(PuzzlePiece p) {
		neighbours[Direction.WEST.ordinal()] = p;
	};
	public void setEast(PuzzlePiece p) {
		neighbours[Direction.EAST.ordinal()] = p;
	};
	
	public String getNorthId() {
		return neighboursId[Direction.NORTH.ordinal()];
	};
	
	public String getSouthId() {
		return neighboursId[Direction.SOUTH.ordinal()];
	};
	
	public String getEastId() {
		return neighboursId[Direction.EAST.ordinal()];
	};
	
	public String getWestId() {
		return neighboursId[Direction.WEST.ordinal()];
	}

	public boolean isNWCorner() {
		return (isNRow() && isWCol());
	}

	public boolean isSWCorner() {
		return (isSRow() && isWCol());
	}

	public boolean isSECorner() {
		return (isSRow() && isECol());
	}

	public boolean isNECorner() {
		return (isNRow() && isECol());
	}

	public boolean isNRow() {
		return (getNorthId().equals(kEmptyId));
	}

	public boolean isSRow() {
		return (getSouthId().equals(kEmptyId));
	}

	public boolean isWCol() {
		return (getWestId().equals(kEmptyId));
	}

	public boolean isECol() {
		return (getEastId().equals(kEmptyId));
	}
	
	public String toString() {
		return "Puzzle piece: "+character;
	}
	public char getCharacter() {
		return character;
	}
}
