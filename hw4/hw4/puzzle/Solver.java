package hw4.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

public final class Solver {
    int moves;
    ArrayList<WorldState> solution = new ArrayList<>();

    private class SearchNode {
        WorldState state;
        int distToInitialState;
        int priority;
        SearchNode prev;

        private SearchNode(WorldState s, int d, SearchNode p) {
            state = s;
            distToInitialState = d;
            prev = p;
            priority = state.estimatedDistanceToGoal() + distToInitialState;
        }

        private int priority() {
            return priority;
        }
    }

    public class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return node1.priority() - node2.priority();
        }
    }

    public Solver(WorldState initial) {
        // best first search
        MinPQ<SearchNode> fringe = new MinPQ<>(new SearchNodeComparator());
        fringe.insert(new SearchNode(initial, 0, null));
        while (!fringe.isEmpty()) {
            SearchNode x = fringe.delMin();
            if (x.state.isGoal()) {
                moves = x.distToInitialState;
                for (; x != null; x = x.prev) {
                    solution.add(x.state);
                }
                Collections.reverse(solution);
                break;
            }
            for (WorldState state: x.state.neighbors()) {
                if (x.prev != null && state.equals(x.prev.state)) {
                    continue;
                }
                SearchNode next = new SearchNode(state, x.distToInitialState + 1, x);
                fringe.insert(next);
            }
        }
    }

    public int moves() {
        return moves;
    } 

    public Iterable<WorldState> solution() {
        return solution;
    }
}

