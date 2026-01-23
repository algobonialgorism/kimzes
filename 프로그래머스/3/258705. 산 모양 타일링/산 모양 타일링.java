class Solution {
    private static final int MOD = 10007;

    public int solution(int n, int[] tops) {
        long dp0 = 1;
        long dp1 = 0; 

        for (int i = 0; i < n; i++) {
            long ndp0, ndp1;

            if (tops[i] == 0) {

                ndp0 = (dp0 * 2 + dp1) % MOD;
                ndp1 = (dp0 + dp1) % MOD;
            } else {

                ndp0 = (dp0 * 3 + dp1 * 2) % MOD;
                ndp1 = (dp0 + dp1) % MOD;
            }

            dp0 = ndp0;
            dp1 = ndp1;
        }

        return (int)((dp0 + dp1) % MOD);
    }
}
