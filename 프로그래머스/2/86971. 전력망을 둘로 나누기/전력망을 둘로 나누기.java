import java.util.*;

class PowerGraph {
    private final int nodeCount;
    private final List<Integer>[] adj;

    public PowerGraph(int n) {
        this.nodeCount = n;
        this.adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int v1, int v2) {
        adj[v1].add(v2);
        adj[v2].add(v1);
    }

    public List<Integer> getNeighbors(int u) {
        return adj[u];
    }

    public int getNodeCount() {
        return nodeCount;
    }
}

class PowerGridAnalyzer {
    private final PowerGraph graph;
    private int minDiff = Integer.MAX_VALUE;

    public PowerGridAnalyzer(PowerGraph graph) {
        this.graph = graph;
    }

    public int solve() {
        calculateSubtreeSize(1, -1);
        return minDiff;
    }


    private int calculateSubtreeSize(int current, int parent) {
        int count = 1; 

        for (int neighbor : graph.getNeighbors(current)) {
            if (neighbor == parent) continue;

            int childSize = calculateSubtreeSize(neighbor, current);
            count += childSize;
        }

        int otherGroupSize = graph.getNodeCount() - count;
        int currentDiff = Math.abs(count - otherGroupSize);
        
        this.minDiff = Math.min(this.minDiff, currentDiff);

        return count;
    }
}

class Solution {
    public int solution(int n, int[][] wires) {
        PowerGraph graph = new PowerGraph(n);
        for (int[] wire : wires) {
            graph.addEdge(wire[0], wire[1]);
        }

        PowerGridAnalyzer analyzer = new PowerGridAnalyzer(graph);
        return analyzer.solve();
    }
}