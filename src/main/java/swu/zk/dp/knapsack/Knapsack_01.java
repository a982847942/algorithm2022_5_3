package swu.zk.dp.knapsack;

/**
 * @Classname Knapsack_01
 * @Description 背包问题：
 * 泛指一类「给定价值与成本」，同时「限定决策规则」，在这样的条件下，如何实现价值最大化的问题。
 * 本篇是「背包问题」中的 01背包问题。
 * <p>
 * 「01背包」是指给定物品价值与体积（对应了「给定价值与成本」），
 * 在规定容量下（对应了「限定决策规则」）如何使得所选物品的总价值最大。
 * <p>
 * 问题描述：
 * 有 N 件物品和一个容量是 V 的背包。每件物品有且只有一件。
 * 第 i 件物品的体积是 v[i]，价值是w[i]。
 * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
 * 输入: N = 3, V = 4, v = [4,2,3], w = [4,2,3]
 * 输出: 4
 * 解释: 只选第一件物品，可使价值最大。
 * @Date 2022/6/15 10:35
 * @Created by brain
 */
public class Knapsack_01 {
    /**
     * DFS暴力递归
     * 任意位置有两种选择：
     * 1.要该位置的物品(前提是容量要足够)
     * 2.不要该位置的物品
     * 从第一个物品开始 进行DFS 返回较大值。
     *
     * @return
     */
    public static int maxValue1(int N, int C, int[] v, int[] w) {
        return process(N, C, 0, v, w);
    }

    private static int process(int N, int C, int cur, int[] v, int[] w) {
        if (C < 0 || cur >= N) return 0;
        int p1 = 0;
        int p2 = 0;
        p1 = process(N, C, cur + 1, v, w);
        if (C >= v[cur]) {
            p2 = process(N, C - v[cur], cur + 1, v, w) + w[cur];
        }
        return Math.max(p1, p2);
    }

    /**
     * dp[i][j] 表示 考虑前i个物品 容量为j时 能得到的最大物品价值
     */
    public static int maxValue2(int N, int C, int[] v, int[] w) {
        int[][] dp = new int[N][C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[0][i] = i >= v[0] ? w[0] : 0;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < C + 1; j++) {
                int temp = v[i];
                int curW = w[i];
                //不选择该物品
                dp[i][j] = dp[i - 1][j];
                if (j >= temp) {
                    //上面结果和选择该物品结果进行比较
                    dp[i][j] = Math.max(dp[i - 1][j - temp] + curW, dp[i][j]);
                }
            }
        }
        return dp[N - 1][C];
    }

    /**
     * 空间压缩 滚动数组
     */
    public static int maxValue3(int N, int C, int[] v, int[] w) {
        int[][] dp = new int[2][C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[0][i] = i >= v[0] ? w[0] : 0;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < C + 1; j++) {
                int temp = v[i];

                dp[i & 1][j] = dp[(i - 1) & 1][j];
                if (j >= temp) {
                    dp[i & 1][j] = Math.max(dp[i & 1][j], dp[(i - 1) & 1][j - temp] + w[i]);
                }
            }
        }
        return dp[(N - 1) & 1][C];
    }

    /**
     * 空间压缩 2
     */
    public static int maxValue4(int N, int C, int[] v, int[] w) {
        int[] dp = new int[C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[i] = i >= v[0] ? w[0] : 0;
        }

        for (int i = 1; i < N; i++) {
            int temp = v[i];
            for (int j = C; j >= temp; j--) {
                dp[j] = Math.max(dp[j],dp[j - temp] + w[i]);
            }
        }
        return dp[C];
    }

    /**
     *这里没有使用哨兵 也可以不用初始化
     * 是因为第0个物品 也是从后向前进行的，不用担心计算j时 j -temp 结果的影响
     * 比如第0行开始时  dp都是0  因此 相当于对第0行进行了初始化
     */
    public static int maxValue6(int N, int C, int[] v, int[] w) {
        int[] dp = new int[C + 1];
        for (int i = 0; i < N; i++) {
            int temp = v[i];
            for (int j = C; j >= temp; j--) {
                dp[j] = Math.max(dp[j],dp[j - temp] + w[i]);
            }
        }
        return dp[C];
    }


    /**
     *dp[i][j] 表示考虑第i个物品往后的所有物品在容量为j的时候 能获取的最大价值
     * 再回忆一下路径问题的常用状态定义，以后在遇到类似问题要优先思考一下这两种常用的方式：
     * 1.从出发位置到i,j
     * 2.从i,j到结束位置
     */
    public static int maxValue5(int N, int C, int[] v, int[] w) {
        int[][] dp =new int[N][C + 1];
        for (int i = 0; i < C + 1; i++) {
            dp[N - 1][i] = i >= v[N - 1] ? w[N - 1] : 0;
        }

        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0; j < C + 1; j++) {
                dp[i][j] = dp[i + 1][j];
                int temp = v[i];
                if (j >= temp){
                    dp[i][j] = Math.max(dp[i][j],dp[i + 1][j - temp] + w[i]);
                }
            }
        }
        return dp[0][C];
    }


    public static void main(String[] args) {
        int N = 3, V = 10;
        int[] v = {4, 3, 8};
        int[] w = {4, 3, 8};
        System.out.println(maxValue1(N, V, v, w));
        System.out.println(maxValue2(N, V, v, w));
        System.out.println(maxValue3(N, V, v, w));
        System.out.println(maxValue4(N, V, v, w));
        System.out.println(maxValue5(N, V, v, w));
        System.out.println(maxValue6(N, V, v, w));
    }
}
