package swu.zk.dp.knapsack;

/**
 * @Classname PacketKS_01
 * @Description
 * 给定 N 个物品组，和容量为 C 的背包。
 * 第 i个物品组共有 s[i]件物品，其中第 i组的第 j件物品的成本为v[i][j]，价值为w[i][j]。
 * 每组有若干个物品，同一组内的物品最多只能选一个。
 * 求解将哪些物品装入背包可使这些物品的费用总和不超过背包容量，且价值总和最大。
 * 输入：N = 2, C = 9, S = [2, 3], v = [[1,2,-1],[1,2,3]], w = [[2,4,-1],[1,3,6]]
 * 输出：10
 * @Date 2022/6/21 12:52
 * @Created by brain
 */
public class PacketKS_01 {
    public static int maxValue(int N, int C, int[] S, int[][] v, int[][] w) {
        int[][] dp = new int[N + 1][C + 1];
        for (int i = 1; i < N + 1; i++) {
            int temp = S[i - 1];
            for (int j = 0; j < C + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int k = 0; k < temp; k++) {
                    if (j >= v[i - 1][k]){
                        dp[i][j] = Math.max(dp[i][j],dp[i - 1][j - v[i - 1][k]]+ w[i-1][k]);
                    }
                }
            }
        }
        return dp[N][C];
    }

    /**
     *空间压缩
     */
    public static int maxValue2(int N, int C, int[] S, int[][] v, int[][] w) {
      int[] dp = new int[C + 1];
        for (int i = 0; i < N; i++) {
            int temp = S[i];
            for (int j = C; j >= 0; j--) {
                for (int k = 0; k < temp; k++) {
                    if (j >= v[i][k]){
                        dp[j] = Math.max(dp[j],dp[j - v[i][k]] + w[i][k]);
                    }
                }
            }
        }
      return dp[C];
    }

    public static void main(String[] args) {
        int[] S = {2, 3};
        int[][] v = {{1,2,-1},{1,2,3}};
        int[][] w = {{2,4,-1},{1,3,6}};
        System.out.println(maxValue(2, 9, S, v, w));
        System.out.println(maxValue2(2, 9, S, v, w));
    }
}
