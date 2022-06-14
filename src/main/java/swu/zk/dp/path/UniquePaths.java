package swu.zk.dp.path;

import java.util.Arrays;

/**
 * @Classname UniquePaths
 * @Description LeetCode 「62. 不同路径」，难度为 Medium。
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 * @Date 2022/6/13 16:11
 * @Created by brain
 */
public class UniquePaths {

    /**
     * 暴力递归法
     * @param m
     * @param n
     * @return
     */
    private static int uniquePaths1(int m, int n) {
        return process(0, 0, m, n);
    }

    /**
     * 当前位置坐标为curX  curY
     * process(int curX, int curY, int m, int n)
     * 表示在当前位置 到达 目标位置有多少种方法，因为当前位置有两种走法，因此
     * 当前位置结果的 = 两种走法的结果之和
     * 同时需要注意边界值判断 和 baseCase问题、
     */
    private static int process(int curX, int curY, int m, int n) {
        if (curX < 0 || curY < 0 || curX >= m || curY >= n) return 0;
        if (curX == m - 1 && curY == n - 1) return 1;
        return process(curX + 1, curY, m, n) + process(curX, curY + 1, m, n);
    }

    /**
     * 针对上述暴力递归方法，可以看到递归树中有重复求解的过程
     * 每一步变化的只是curX  curY 而且当前位置的解不依赖此位置之前的解(无后效性)，可以使用动态规划
     * dp[i][j] 表示i，j位置到 m,n 有多少种走法。
     * 很显然，第m-1行 和 第n-1列都只有一种走法
     */
    private static int uniquePath2(int m, int n){
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][n - 1] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[m - 1][i] = 1;
        }
        //从右下方开始求解
        for (int i = m- 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
            }
        }
        return dp[0][0];
    }

    /**
     * 使用了空间压缩技巧
     * 因为每一层求解时只依赖于上一层结果，因此不需要保存太多的结果即可完成迭代。
     */
    private static int uniquePath3(int m, int n){
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }
        //dp[n- 1]永远保持 1
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[j] = dp[j + 1] + dp[j];
            }
        }
        return dp[0];
    }

    /**
     * 换一种状态定义
     * dp[i][j] 表示从0,0 到i，j有多少种方法
     */
    private static int uniquePath4(int m, int n){
        int[][] dp = new int[m][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    private static int uniquePath5(int m, int n){
        int[] dp = new int[n];
        Arrays.fill(dp,1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths1(7, 3));
        System.out.println(uniquePath2(7,3));
        System.out.println(uniquePath3(7,3));
        System.out.println(uniquePath4(7,3));
        System.out.println(uniquePath5(7,3));
    }
}
