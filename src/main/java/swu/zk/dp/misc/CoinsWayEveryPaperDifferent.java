package swu.zk.dp.misc;

/**
 * @Classname CoinsWayEveryPaperDifferent
 * @Description arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，
 * 返回组成aim的方法数
 * 例如：arr = {1,1,1}，aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 * 一共就3种方法，所以返回3
 * @Date 2022/6/18 12:53
 * @Created by brain
 */
public class CoinsWayEveryPaperDifferent {
    /**
     * dp[i][j] 表示考虑前 i 个物品，价值恰好为 j 的方案数
     * dp[0][0] 是哨兵
     * 这就是个 0 1背包问题 关键在于初始化时一定要注意 coins[0] 不一定小于target
     */
    public static int getMethods(int[] coins, int target) {
        int n = coins.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;

        for (int i = 1; i < n + 1; i++) {
            int temp = coins[i - 1];
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= temp) {
                    dp[i][j] += dp[i - 1][j - temp];
                }
            }
        }
        return dp[n][target];
    }

    /**
     *空间压缩 并未采用哨兵
     * 0 1背包 从后向前遍历时 不用哨兵也可以完成初始化
     */
    public static int getMethods2(int[] coins, int target) {
        int[] dp = new int[target + 1];
        int n = coins.length;
        dp[0] = 1;
        for (int i = 0; i < n ; i++) {
            int temp = coins[i];
            for (int j = target; j >= temp; j--) {
                dp[j] += dp[j-temp];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] coins = {1, 1, 1};
        System.out.println(getMethods(coins, 2));
        System.out.println(getMethods2(coins, 2));
    }
}
