package swu.zk.dp.misc;

import java.util.Arrays;

/**
 * @Classname MinCoinsNoLimit
 * @Description
 * coins是面值数组，其中的值都是正数且没有重复。再给定一个正数target
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 * @Date 2022/6/19 19:43
 * @Created by brain
 */
public class MinCoinsNoLimit {
    public static int minCoins(int[] coins, int target){
        if (target == 0) {
            return 0;
        }
        int n = coins.length;
        int[][] dp = new int[n + 1][target + 1];
        Arrays.fill(dp[0],0x3f3f3f3f);
        dp[0][0] = 0;
        for (int i = 1; i < n + 1; i++) {
            int temp = coins[i - 1];
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - temp >= 0){
                    dp[i][j] = Math.min(dp[i][j],dp[i][j - temp] + 1);
                }
            }
        }
        return dp[n][target] == 0x3f3f3f3f ? -1 : dp[n][target];
    }

    public static int minCoins2(int[] coins, int target){
        int[] dp = new int[target + 1];
        int n = coins.length;
        Arrays.fill(dp,0x3f3f3f3f);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int temp = coins[i];
            for (int j = temp; j < target + 1; j++) {
                dp[j] = Math.min(dp[j],dp[j- temp] + 1);
            }
        }
        return dp[target] == 0x3f3f3f3f ? -1 : dp[target];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = minCoins2(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
