package swu.zk.dp.knapsack;

import java.math.BigInteger;

/**
 * @Classname ProfitableSchemes
 * @Description 这是 LeetCode 上的「879. 盈利计划」，难度为「困难」。
 * Tag : 「动态规划」、「容斥原理」、「数学」、「背包问题」、「多维背包」
 * 集团里有 n名员工，他们可以完成各种各样的工作创造利润。
 * 第 i种工作会产生 profit[i] 的利润，它要求 group[i]名成员共同参与。
 * 如果成员参与了其中一项工作，就不能参与另一项工作。
 * 工作的任何至少产生minProfit利润的子集称为「盈利计划」。并且工作的成员总数最多为n。
 * 有多少种计划可以选择？
 * 因为答案很大，所以 返回结果模  的值。
 * 输入：n = 5, minProfit = 3, group = [2,2], profit = [2,3]
 * 输出：2
 * 解释：至少产生 3 的利润，该集团可以完成工作 0 和工作 1 ，或仅完成工作 1 。
 * 总的来说，有两种计划。
 * <p>
 * 转化为背包问题：
 * 将每个任务看作一个「物品」，完成任务所需要的人数看作「成本」，完成任务得到的利润看作「价值」。
 * 其特殊在于存在一维容量维度需要满足「不低于」，而不是常规的「不超过」。这需要我们对于某些状态作等价变换。
 * @Date 2022/6/21 15:55
 * @Created by brain
 */
public class ProfitableSchemes {
    static int mod = (int) 1e9 + 7;

    /**
     * dp[i][j][k] 表示考虑前i个工作 人数不超过j 利润至少为k的方案数
     */
    public static int profitableSchemes(int n, int min, int[] gs, int[] ps) {
        int m = gs.length;
        int[][][] dp = new int[m + 1][n + 1][min + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[0][i][0] = 1;
        }

        for (int i = 1; i < m + 1; i++) {
            int temp = gs[i - 1];
            int curP = ps[i - 1];
            for (int j = 0; j < n + 1; j++) {

                for (int k = 0; k < min + 1; k++) {
                    //不选择第i个工作
                    dp[i][j][k] = dp[i - 1][j][k];
                    /**
                     * 考虑第i个工作
                     * Math.max(0,k - curP) 意思就是 有可能k < curP 即当前任务已经可以达到minprofit了
                     * 那么前i-1个任务 不需要任何利润就能完成指标。
                     * 这里就体现为 k - curP <= 0  因为前i-1个任务 最小利润也是0 因此对于负数就用0来更新。
                     */
                    if (j >= temp) {
                        dp[i][j][k] += dp[i - 1][j - temp][Math.max(0, k - curP)];
                        dp[i][j][k] %= mod;
                    }
                }
            }
        }

        return dp[m][n][min];
    }

    /**
     *空间压缩
     */
    public static int profitableSchemes2(int n, int minProfit, int[] group, int[] profit) {
        int m = group.length;
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = 1;
        }

        for (int i = 0; i < m; i++) {
            int temp = group[i];
            int curP = profit[i];
            for (int j = n; j >= 0; j--) {
                for (int k = minProfit; k >= 0 ; k--) {
                    if (j - temp >= 0){
                        dp[j][k] += dp[j - temp][Math.max(0,k - curP)];
                        dp[j][k] %= mod;
                    }
                }
            }
        }
        return dp[n][minProfit];
    }

    public static void main(String[] args) {
        int n = 10, minProfit = 5;
        int[] group = {2,3,5};
        int[] profit = {6,7,8};
        System.out.println(profitableSchemes(n, minProfit, group, profit));
        System.out.println(profitableSchemes2(n, minProfit, group, profit));
        System.out.println(profitableSchemes3(n, minProfit, group, profit));
    }


    static int N = 105;
    static BigInteger[][] f = new BigInteger[2][N];
    static BigInteger[][][] g = new BigInteger[2][N][N];
    static BigInteger mod1 = new BigInteger("1000000007");

    public static int profitableSchemes3(int n, int min, int[] gs, int[] ps) {
        int m = gs.length;

        for (int j = 0; j <= n; j++) {
            f[0][j] = new BigInteger("1");
            f[1][j] = new BigInteger("0");
        }
        for (int j = 0; j <= n; j++) {
            for (int k = 0; k <= min; k++) {
                g[0][j][k] = new BigInteger("1");
                g[1][j][k] = new BigInteger("0");
            }
        }

        for (int i = 1; i <= m; i++) {
            int a = gs[i - 1], b = ps[i - 1];
            int x = i & 1, y = (i - 1) & 1;
            for (int j = 0; j <= n; j++) {
                f[x][j] = f[y][j];
                if (j >= a) {
                    f[x][j] = f[x][j].add(f[y][j-a]);
                }
            }
        }
        if (min == 0) return (f[m&1][n]).mod(mod1).intValue();

        for (int i = 1; i <= m; i++) {
            int a = gs[i - 1], b = ps[i - 1];
            int x = i & 1, y = (i - 1) & 1;
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k < min; k++) {
                    g[x][j][k] = g[y][j][k];
                    if (j - a >= 0 && k - b >= 0) {
                        g[x][j][k] = g[x][j][k].add(g[y][j-a][k-b]);
                    }
                }
            }
        }

        return f[m&1][n].subtract(g[m&1][n][min-1]).mod(mod1).intValue();
    }
}
