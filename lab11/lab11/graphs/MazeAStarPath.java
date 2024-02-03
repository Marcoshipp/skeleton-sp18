package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    private class SearchNode {
        private int state;
        private int priority;

        private SearchNode(int s, int p) {
            state = s;
            priority = p;
        }

        public int state() {
            return state;
        }

        public int priority() {
            return priority;
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode s, SearchNode t) {
            return s.priority() - t.priority();
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        MinPQ<SearchNode> fringe = new MinPQ(new SearchNodeComparator());
        fringe.insert(new SearchNode(s, h(s)));
        marked[s] = true;
        while (!fringe.isEmpty()) {
            SearchNode v = fringe.delMin();
            int currentState = v.state();
            marked[currentState] = true;
            announce();
            if (currentState == t) {
                targetFound = true;
                return;
            }
            for (int w: maze.adj(currentState)) {
                // relax all edges pointing out from v
                if (marked[w]) {
                    continue;
                }
                int possibleBestDist = distTo[currentState] + edgeTo[w];
                int currentKnownDist = distTo[w];
                if (possibleBestDist + h(currentState) < currentKnownDist) {
                    edgeTo[w] = currentState;
                    fringe.insert (new SearchNode(w, possibleBestDist + h(currentState)));
                    announce();
                    distTo[w] = distTo[currentState] + 1;
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

