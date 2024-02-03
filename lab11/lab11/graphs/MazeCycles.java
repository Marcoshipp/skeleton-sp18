package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean foundCycle;
    private int[] nodeTo;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        foundCycle = false;
        nodeTo = new int[maze.N() * maze.N()];
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        cycleDetecting(-1, 0);
    }

    // Helper methods go here
    private void cycleDetecting(int u, int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                nodeTo[w] = v;
                cycleDetecting(v, w);
            } else if (w != u) {
                edgeTo[w] = v;
                announce();
                for (int x = v; x != w; x = nodeTo[x]) {
                    edgeTo[x] = nodeTo[x];
                    announce();
                }
                foundCycle = true;
            }
            if (foundCycle) {
                return;
            }
        }
    }
}

