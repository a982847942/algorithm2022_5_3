package swu.zk.dp.knapsack;

import java.util.Arrays;

/**
 * @Classname CoinChange
 * @Description
 *这是 LeetCode 上的「322. 零钱兑换」，难度为 Medium。
 * 给定不同面额的硬币 coins 和一个总金额 amount。
 * 编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 你可以认为每种硬币的数量是无限的。
 *
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * @Date 2022/6/16 10:44
 * @Created by brain
 */
public class CoinChange {
    /**
     * 这也是一个完全背包问题
     * 背包容量为amount   物品个数为coins.length  重量和价值都是coins[i]
     */
    public static int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];
        for (int i = 0; i < amount + 1; i++) {
            int temp = coins[0];
            if (i == (i / temp * temp)){
                dp[0][i] = i / temp;
            }else {
                /**
                 * 为什么不用Integer.MAX_VALUE  因为这个最大值加上 任何一个正数都会产生溢出
                 * 后续判断时候可能会产生错误(dp[i-1][j - k*temp] + k 溢出)
                 */
//                dp[0][i] = Integer.MAX_VALUE;
                dp[0][i] = 0x3f3f3f3f;
            }
        }

        for (int i = 1; i < n; i++) {
            int temp = coins[i];
            for (int j = 0; j < amount + 1; j++) {
                dp[i][j] = dp[i-1][j];
                for (int k = 0; j - k * temp >= 0; k++) {
                   dp[i][j] = Math.min(dp[i][j],dp[i-1][j - k*temp] + k);
                }
            }
        }
        return dp[n-1][amount] == Integer.MAX_VALUE ? -1 : dp[n-1][amount];
    }


    public static int coinChange2(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        for (int i = 0; i < amount + 1; i++) {
            int temp = coins[0];
            if (i == (i / temp * temp)){
                dp[i] = i / temp;
            }else {
                dp[i] = 0x3f3f3f3f;
            }
        }
        for (int i = 1; i < n; i++) {
            int temp = coins[i];
            for (int j = temp; j < amount + 1; j++) {
                dp[j] = Math.min(dp[j],dp[j - temp] + 1);
            }
        }
        return dp[amount] == 0x3f3f3f3f ? -1 : dp[amount];
    }

    /**
     *哨兵技巧  dp[0] = 0 表示考虑第0个物品 恰好满足容量为0 需要0件物品 其它的都是Integer.maxValue
     */
    public static int coinChange3(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp,0x3f3f3f3f);
        dp[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            int temp = coins[i - 1];
            for (int j = temp; j < amount + 1; j++) {
                dp[j] = Math.min(dp[j],dp[j - temp] + 1);
            }
        }
        return dp[amount] == 0x3f3f3f3f ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {2};
        int amount = 3;
        System.out.println(coinChange(coins, amount));
        System.out.println(coinChange2(coins, amount));
        System.out.println(coinChange3(coins, amount));
    }
}
