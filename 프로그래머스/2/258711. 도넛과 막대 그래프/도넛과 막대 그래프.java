import java.util.*;

class Adj {
    private final int maxId;
    private final int[] inDegree;
    private final int[] outDegree;
    private final boolean[] exists;

    Adj(int[][] edges) {
        this.maxId = computeMaxId(edges);
        this.inDegree = new int[maxId + 1];
        this.outDegree = new int[maxId + 1];
        this.exists = new boolean[maxId + 1];
        buildDegrees(edges);
    }

    private int computeMaxId(int[][] edges) {
        int mx = 0;
        for (int[] e : edges) {
            int a = e[0], b = e[1];
            if (a > mx) mx = a;
            if (b > mx) mx = b;
        }
        return mx;
    }

    private void buildDegrees(int[][] edges) {
        for (int[] e : edges) {
            int a = e[0], b = e[1];
            outDegree[a]++;
            inDegree[b]++;
            exists[a] = true;
            exists[b] = true;
        }
    }

    int getCenter() {
        for (int i = 1; i <= maxId; i++) {
            if (exists[i] && inDegree[i] == 0 && outDegree[i] >= 2) {
                return i;
            }
        }
        return -1; 
    }

    // 막대: (center 제외) outDegree==0인 정점 수
    int getStraightFormNum(int center) {
        int bar = 0;
        for (int i = 1; i <= maxId; i++) {
            if (!exists[i] || i == center) 
                continue;
            if (outDegree[i] == 0) 
                bar++;
        }
        return bar;
    }


    int getEightFormNum(int center) {
        int eight = 0;
        for (int i = 1; i <= maxId; i++) {
            if (!exists[i] || i == center) 
                continue;
            if (outDegree[i] == 2) 
                eight++;
        }
        return eight;
    }

    int getCycleFormNum(int center, int bar, int eight) {
        int total = outDegree[center];
        return total - bar - eight;
    }

    int[] getAnswer() {
        int center = getCenter();
        int bar = getStraightFormNum(center);
        int eight = getEightFormNum(center);
        int donut = getCycleFormNum(center, bar, eight);
        return new int[]{center, donut, bar, eight};
    }
}

class Solution {
    public int[] solution(int[][] edges) {
        Adj adj = new Adj(edges);
        return adj.getAnswer();
    }
}
