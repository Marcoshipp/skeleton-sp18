package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> fringe = new Queue<>();
        marked[s] = true;
        fringe.enqueue(s);
        while (!fringe.isEmpty()) {
            int v = fringe.dequeue();
            announce();
            if (v == t) {
                targetFound = true;
                return;
            }
            for (int w: maze.adj(v)) {
                if (marked[w]) {
                    continue;
                }
                marked[w] = true;
                edgeTo[w] = v;
                fringe.enqueue(w);
                announce();
                distTo[w] = distTo[v] + 1;
            }
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

