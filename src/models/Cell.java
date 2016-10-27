package models;

/**
 * Representation of a single cell in the game board.
 *
 * @author Jeroen Keppens, King's College London
 */
class Cell {
    private boolean mine = false;
    private boolean flag = false;
    private boolean revealed = false;
    private int adjacent = 0;

    /**
     * Place a mine in this cell.
     */
    void mine() {
        mine = true;
    }

    /**
     * Identify whether this cell contains a mine.
     *
     * @return true iff this cell contains a mine.
     */
    boolean hasMine() {
        return mine;
    }

    /**
     * Place a flag in this cell.
     */
    void flag() {
        flag = true;
    }

    /**
     * Remove a flag from this cell.
     */
    void unflag() {
        flag = false;
    }

    /**
     * Identify whether this cell contains a flag.
     *
     * @return true iff this cell contains a flag.
     */
    boolean hasFlag() {
        return flag;
    }

    /**
     * Change the status of this cell to reveal.
     */
    void reveal() {
        revealed = true;
    }

    /**
     * Identify whether this cell is revealed.
     *
     * @return true iff this cell is revealed.
     */
    boolean revealed() {
        return revealed;
    }

    /**
     * Identify whether this cell has been processed by the player.
     * A cell is processed by the player if it has been clicked by the player,
     * e.g. it was clicked in the game board display, or it has been flagged
     * by the player.
     *
     * @return true iff this cell has neither been flagged nor been revealed.
     */
    boolean blank() {
        return !flag && !revealed;
    }

    /**
     * Sets the number of adjacent cells that contain mines.
     *
     * @param adjacent the number of adjacent mines.
     */
    void setAdjacent(int adjacent) {
        this.adjacent = adjacent;
    }

    /**
     * Increases the number of adjacent mines by 1.
     */
    void incrementAdjacent() {
        adjacent++;
    }

    /**
     * Specifies the number of adjacent cells that contain mines.
     *
     * @return the number of adjacent mines.
     */
    int getAdjacent() {
        return adjacent;
    }

    /**
     * Indicates whether there are adjacent mines.
     *
     * @return true iff there are not adjacent mines.
     */
    boolean noAdjacentMines() {
        return (adjacent == 0);
    }

    /**
     * Returns a text representation of the cell.
     *
     * @return "F" if the cell was flagged, "M" if the cell is not flagged but
     * contains a mine, or a number indicating the number of adjacent mines if
     * the cell is neither flagged nor mined.
     */
    public String toString() {
        if (hasFlag()) {
            return "F";
        } else if (hasMine()) {
            return "M";
        } else {
            return Integer.toString(getAdjacent());
        }
    }
}
