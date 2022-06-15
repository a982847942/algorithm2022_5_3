package swu.zk.dp.knapsack;

/**
 * @Classname CompletelyKS
 * @Description 有 N 种物品和一个容量为 C 的背包，每种物品都有无限件。
 * 第 i 件物品的体积是v[i] ，价值是w[i] 。
 * 求解将哪些物品装入背包可使这些物品的费用总和不超过背包容量，且价值总和最大。
 * 其实就是在 0-1 背包问题的基础上，增加了每件物品可以选择多次的特点（在容量允许的情况下）。
 * 输入: N = 2, C = 5, v = [1,2], w = [1,2]
 * 输出: 5
 * 解释: 选一件物品 1，再选两件物品 2，可使价值最大。
 */
public class CompletelyKS {
    /**
     * dp[i][j]  表示考虑前i个物品 容量为j时  能获得的最大价值
     * 时间复杂度：共有 N * C 个状态需要被转移，复杂度为O(N*C*C)
     *  空间复杂度：O(N*C)
     */
    public static int maxValue(int N, int C, int[] v, int[] w) {
        int[][] dp = new int[N][C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[0][i] = i / v[0] * w[0];
        }
        for (int i = 1; i < N; i++) {
            int temp = v[i];
            for (int j = 0; j < C + 1; j++) {
                /**
                 * 每个物品可以被多次选择
                 */
                for (int k = 0; j - k * temp >= 0; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * temp] + k * w[i]);
                }
            }
        }
        return dp[N - 1][C];
    }

    /**
     * 仔细思考一下会发现，dp[i][j] 依赖于 dp[i - 1][j] 和 dp[i - 1][j-k*v[i]] + k * w[i]
     * 而dp[i][j - v[i]] 依赖于 dp[i - 1][j - v[i]] 和 dp[i - 1][j - k * v[i]] + (k-1) *w[i]
     * 因此在 dp 的 二维网格中 dp[i][j] 实质依赖于 dp[i-1][j] 和 dp[i][j-v[i]] + w[i]
     * 即完全背包问题的递推公式为：
     * dp[i][j] = max(dp[i-1][j],dp[i][j-v[i]] + w[i])
     * 而0-1背包问题的递推公式为：
     * dp[i][j] = max(dp[i-1][j],dp[i - 1][j - v[i]] + w[i])_
     * <p>
     * 当使用空间压缩技巧时 会发现 0-1背包 需要保证j-v[i] 是 i-1(即上一次的值)  因此需要从后向前求解
     * 而完全背包这个问题 j - v[i] 是本次的值  因此需要从前向后求解
     * 时间复杂度：共有 N * C 个状态需要被转移，复杂度为O(N*C)
     * 空间复杂度：O(N*C)
     */
    public static int maxValue2(int N, int C, int[] v, int[] w) {
        int[][] dp = new int[N][C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[0][i] = i / v[0] * w[0];
        }
        for (int i = 1; i < N; i++) {
            int temp = v[i];
            for (int j = 0; j < C + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if (j - temp >= 0){
                    dp[i][j] = Math.max(dp[i][j],dp[i][j - temp] + w[i]);
                }
            }
        }
        return dp[N - 1][C];
    }

    /**
     * 空间优化
     * 时间复杂度：共有 N * C 个状态需要被转移，复杂度为O(N*C)
     * 空间复杂度：O(C)
     */
    public static int maxValue3(int N, int C, int[] v, int[] w) {
        int[] dp = new int[C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[i] = i / v[0] *  w[0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < C + 1; j++) {
                if (j - v[i] >= 0){
                    dp[j] = Math.max(dp[j],dp[j-v[i]] + w[i]);
                }
            }
        }
        return dp[C];
    }

    /**
     *与空间优化技巧一样
     * 只不过加上了哨兵 即第0行 代表哨兵
     * 第一行代表第一个物品。。。一次类推
     * 这样省去了初始化的步骤
     */
    public static int maxValue4(int N, int C, int[] v, int[] w) {
        int[] dp = new int[C + 1];
        for (int i = 1; i < N + 1; i++) {
            for (int j = 0; j < C + 1; j++) {
                if (j - v[i - 1] >= 0){
                    dp[j] = Math.max(dp[j],dp[j-v[i - 1]] + w[i - 1]);
                }
            }
        }
        return dp[C];
    }

    public static void main(String[] args) {
        int N = 2, C = 5;
        int[] v = {1, 2};
        int[] w = {1, 2};
        System.out.println(maxValue(N, C, v, w));
        System.out.println(maxValue2(N, C, v, w));
        System.out.println(maxValue3(N, C, v, w));
        System.out.println(maxValue4(N, C, v, w));
    }
}
