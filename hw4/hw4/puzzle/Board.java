package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int[][] contents;

    public Board(int[][] tiles) {
        contents = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                contents[i][j] = tiles[i][j];
            }
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank).
     */
    public int tileAt(int i, int j) {
        if (i >= size() || j >= size() || i < 0 || j < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return contents[i][j];
    }

    /**
     * Returns the board size N.
     */
    public int size() {
        return contents.length;
    }

    /**
     * Returns the neighbors of the current board.
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int blankRow = -1;
        int blankCol = -1;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
        int[][] n = new int[size()][size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                n[i][j] = tileAt(i, j);
            }
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (Math.abs(i - blankRow) + Math.abs(blankCol - j) == 1) {
                    n[blankRow][blankCol] = n[i][j];
                    n[i][j] = 0;
                    Board neighbor = new Board(n);
                    neighbors.enqueue(neighbor);
                    n[i][j] = n[blankRow][blankCol];
                    n[blankRow][blankCol] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int inversions = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                }
                if (tileAt(i, j) != i * size() + j + 1) {
                    inversions += 1;
                }
            }
        }
        return inversions;
    }

    public int manhattan() {
        int total = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                } else {
                    int realPos = tileAt(i, j) - 1;
                    total += Math.abs(realPos / size() - i) + Math.abs(realPos % size() - j);
                }
            }
        }
        return total;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (other.tileAt(i, j) != this.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    /** 
     * Returns the string representation of the board. 
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
