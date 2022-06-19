package swu.zk.dp.misc;

/**
 * @Classname CoinsWayNoLimit
 * @Description arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 * @Date 2022/6/18 13:14
 * @Created by brain
 */
public class CoinsWayNoLimit {
    /**
     * dp[i][j]  表示考虑前 i 件物品  容量恰好为 j 的方案数
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
                    dp[i][j] += dp[i][j - temp];
                }
            }
        }
        return dp[n][target];
    }


    public static int getMethods2(int[] coins, int target) {
        int n = coins.length;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            int temp = coins[i];
            for (int j = temp; j < target + 1; j++) {
                dp[j] += dp[j-temp];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] coins = {1,2};
        System.out.println(getMethods(coins,4));
        System.out.println(getMethods2(coins,4));
    }
}
