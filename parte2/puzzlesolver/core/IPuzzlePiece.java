package puzzlesolver.core;

public interface IPuzzlePiece {
	/**
	 * Returns the ID.
	 */
	String getId();
	char getCharacter();
	/**
	 * Returns a reference to the northern neighbour, /if/ set.
	 * (Might be == null if the puzzle is yet in an "unsolved" state or if the piece has no northern neighbour at all)
	 */
	IPuzzlePiece getNorth();
	IPuzzlePiece getSouth();
	IPuzzlePiece getWest();
	IPuzzlePiece getEast();
	void setNorth(IPuzzlePiece p);
	void setSouth(IPuzzlePiece p);
	void setWest(IPuzzlePiece p);
	void setEast(IPuzzlePiece p);
	/**
	 * Returns the ID of the northern neighbour.
	 */
	String getNorthId();
	String getSouthId();
	String getEastId();
	String getWestId();
	/**
	 * See if the piece belongs to the northern (topmost) row, according to the
	 * neighbours' IDs.
	 */
	boolean isNRow();
	boolean isWCol();
	boolean isSRow();
	boolean isECol();
	/**
	 * See if the piece is the NW corner according to the neighbours' IDs.
	 */
	boolean isNWCorner();
	boolean isSWCorner();
	boolean isSECorner();
	boolean isNECorner();
}
