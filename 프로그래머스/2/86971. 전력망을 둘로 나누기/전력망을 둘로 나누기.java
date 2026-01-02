import java.util.*;

class PowerGraph {
    private final int n;
    private final List<Integer>[] adj;
    private int minDiff;

    public PowerGraph(int n) {
        this.n = n;
        this.minDiff = n;
        this.adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int v1, int v2) {
        adj[v1].add(v2);
        adj[v2].add(v1);
    }

    // 최적화된 해결 메서드: O(N)
    public int getMinDifference() {
        dfs(1, -1); // 1번 노드를 루트로 시작
        return minDiff;
    }

    private int dfs(int curr, int parent) {
        int subtreeSize = 1; // 자기 자신 포함

        for (int next : adj[curr]) {
            if (next != parent) { // 부모로 되돌아가는 것 방지
                subtreeSize += dfs(next, curr);
            }
        }

        // 현재 노드와 부모 사이의 간선을 끊었다고 가정했을 때의 차이 계산
        // 공식: |(전체 노드 - 서브트리) - 서브트리| = |N - 2 * subtreeSize|
        int diff = Math.abs(n - 2 * subtreeSize);
        minDiff = Math.min(minDiff, diff);

        return subtreeSize;
    }
}

class Solution {
    public int solution(int n, int[][] wires) {
        PowerGraph graph = new PowerGraph(n);
        for (int[] wire : wires) {
            graph.addEdge(wire[0], wire[1]);
        }
        return graph.getMinDifference();
    }
}