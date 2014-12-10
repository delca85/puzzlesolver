package puzzlesolver;

public interface PuzzlePiece {
	String getId();
	PuzzlePiece getNorth();
	PuzzlePiece getSouth();
	PuzzlePiece getWest();
	PuzzlePiece getEast();
	void setNorth(PuzzlePiece p);
	void setSouth(PuzzlePiece p);
	void setWest(PuzzlePiece p);
	void setEast(PuzzlePiece p);
	String getNorthId();
	String getSouthId();
	String getEastId();
	String getWestId();
	boolean isNRow();
	boolean isWRow();
	boolean isSRow();
	boolean isERow();
	boolean isNWCorner();
	boolean isSWCorner();
	boolean isSECorner();
	boolean isNECorner();
}
