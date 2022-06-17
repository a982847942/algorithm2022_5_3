package swu.zk.dp.knapsack;

/**
 * @Classname CoinChange2
 * @Description 这是 LeetCode 上的「518. 零钱兑换 II」，难度为 Medium。
 * 给定不同面额的硬币和一个总金额。
 * 写出函数来计算可以凑成总金额的硬币组合数。
 * 假设每一种面额的硬币有无限个。
 * 输入: amount = 5, coins = [1, 2, 5]
 * 输出: 4
 * 解释: 有四种方式可以凑成总金额:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 * @Date 2022/6/16 15:03
 * @Created by brain
 */
public class CoinChange2 {
    public static int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];
        for (int i = 0; i < amount + 1; i++) {
            int temp = coins[0];
            dp[0][i] = (i / temp * temp) == i ? 1 : 0;
        }

        for (int i = 1; i < n; i++) {
            int temp = coins[i];
            for (int j = 0; j < amount + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if (j - temp >= 0){
                    dp[i][j] += dp[i][j - temp];
                }
            }
        }
        return dp[n - 1][amount];
    }

    public static int change2(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        for (int i = 0; i < amount + 1; i++) {
            int temp = coins[0];
            dp[i] = (i / temp * temp == i) ? 1 : 0;
        }
        int n = coins.length;
        for (int i = 1; i < n; i++) {
            int temp = coins[i];
            for (int j = temp; j < amount + 1; j++) {
                dp[j] += dp[j - temp];
            }
        }
        return dp[amount];
    }

    /**
     * 哨兵
     */
    public static int change3(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        int n = coins.length;
        dp[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            int temp = coins[i - 1];
            for (int j = temp; j < amount + 1; j++) {
                dp[j] += dp[j - temp];
            }
        }
        return dp[amount];
    }



    public static void main(String[] args) {
        int amount = 10;
        int[] coins = {10};
        System.out.println(change(amount, coins));
        System.out.println(change2(amount,coins));
        System.out.println(change3(amount,coins));
    }
}
