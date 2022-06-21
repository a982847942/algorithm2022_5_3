package swu.zk.dp.knapsack;

/**
 * @Classname NumRollsToTarget
 * @Description
 *这是 LeetCode 上的「1155. 掷骰子的N种方法」，难度为「中等」。
 * Tag : 「背包问题」、「动态规划」、「分组背包」
 * 这里有 d 个一样的骰子，每个骰子上都有 f 个面，分别标号为 1,2,...,f。
 * 我们约定：掷骰子的得到总点数为各骰子面朝上的数字的总和。
 * 如果需要掷出的总点数为 target，请你计算出有多少种不同的组合情况（所有的组合情况总共有f的d次方种），
 * 模 1e9+7后返回。
 *
 * 输入：d = 1, f = 6, target = 3
 * 输出：1
 * @Date 2022/6/21 13:12
 * @Created by brain
 */
public class NumRollsToTarget {
    static int mod = (int)1e9+7;
    public static int numRollsToTarget(int d, int f, int t) {
        int[][] dp = new int[d + 1][t + 1];
        dp[0][0] = 1;
        for (int i = 1; i < d + 1; i++) {
            for (int j = 0; j < t + 1; j++) {
                //骰子不可能为0  因此这里不能不使用第i个物品组
//                dp[i][j] = dp[i-1][j];
                for (int k = 1; k <= f; k++) {
                   if (j >= k){
                       dp[i][j] += dp[i-1][j - k];
                       dp[i][j] %= mod;
                   }
                }
            }
        }
        return dp[d][t];
    }

    public static int numRollsToTarget2(int d, int f, int t) {
        int[] dp = new int[t + 1];
        dp[0] = 1;
        for (int i = 0; i < d; i++) {
            for (int j = t; j >= 0; j--) {
                dp[j] = 0;
                for (int k = 1; k <= f; k++) {
                    if (j >= k){
                        dp[j] += dp[j-k];
                        dp[j] %= mod;
                    }
                }
            }
        }
        return dp[t];
    }

    public static void main(String[] args) {
        int d = 30,f = 30,target = 500;
        System.out.println(numRollsToTarget(d, f, target));
        System.out.println(numRollsToTarget2(d, f, target));
    }
}
