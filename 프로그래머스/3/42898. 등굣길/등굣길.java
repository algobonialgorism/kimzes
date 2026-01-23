class Solution {
    public int solution(int m, int n, int[][] puddles) {
        final int MOD = 1_000_000_007;
        
        boolean[][] isPuddle = new boolean[n + 1][m + 1];
        for (int[] p : puddles) {
            isPuddle[p[1]][p[0]] = true;
        }

        int[] dp = new int[m + 1];
        
        dp[1] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i == 1 && j == 1) continue;

                if (isPuddle[i][j]) {
                    dp[j] = 0;
                } else {
                    dp[j] = (dp[j] + dp[j-1]) % MOD;
                }
            }
        }

        return dp[m];
    }
}