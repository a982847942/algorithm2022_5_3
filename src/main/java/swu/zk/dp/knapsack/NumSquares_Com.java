package swu.zk.dp.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname NumSquares_Com
 * @Description 这是 LeetCode 上的「279. 完全平方数」，难度为 Medium。
 * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。
 * 你需要让组成和的完全平方数的个数最少。
 * 给你一个整数 n ，返回和为 n 的完全平方数的「最少数量」。
 * 「完全平方数」是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
 * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是
 * <p>
 * 输入：n = 12
 * 输出：3
 * 解释：12 = 4 + 4 + 4
 * @Date 2022/6/16 9:59
 * @Created by brain
 */
public class NumSquares_Com {
    public static int numSquares(int n) {
        List<Integer> list = new ArrayList<>();
        int index = 1;
        while (index * index <= n) {
            list.add(index * index);
            index++;
        }

        int num = list.size();
        /**
         * 转化为完全背包问题  物品个数为num 价值和重量为 list.get(i) 背包容量为n
         * dp[i][j] 表示考虑前i个物品 恰好满足容量为j 所用的最少完全平方数个数
         */
        int[][] dp = new int[num][n + 1];
        for (int i = 0; i < n + 1; i++) {
            Integer temp = list.get(0);
            int div = i / temp;
            if (div * temp == i) {
                dp[0][i] = div;
            } else {
                dp[0][i] = Integer.MAX_VALUE;
            }
        }

        for (int i = 1; i < num; i++) {
            Integer temp = list.get(i);
            for (int j = 0; j < n + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int k = 0; j - k * temp >= 0; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - k * temp] + k);
                }
            }
        }
        return dp[num - 1][n];
    }

    public static int numSquares2(int n) {
        List<Integer> list = new ArrayList<>();
        int index = 1;
        while (index * index <= n) {
            list.add(index * index);
            index++;
        }

        int num = list.size();
        int[][] dp = new int[num][n + 1];
        for (int i = 0; i < n + 1; i++) {
            Integer temp = list.get(0);
            dp[0][i] = i / temp;
        }

        for (int i = 1; i < num; i++) {
            Integer temp = list.get(i);
            for (int j = 0; j < n + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - temp >= 0){
                    dp[i][j] = Math.min(dp[i][j],dp[i][j - temp] + 1);
                }
            }
        }
        return dp[num - 1][n];
    }

    public static int numSquares3(int n) {
        List<Integer> list = new ArrayList<>();
        int index = 1;
        while (index * index <= n) {
            list.add(index * index);
            index++;
        }

        int num = list.size();
        int[] dp = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            Integer temp = list.get(0);
            dp[i] = i / temp;
        }

        for (int i = 1; i < num; i++) {
            Integer temp = list.get(i);
            for (int j = temp; j < n + 1; j++) {
                dp[j] = Math.min(dp[j],dp[j - temp] + 1);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(numSquares2(12));
        System.out.println(numSquares3(12));
    }

}
