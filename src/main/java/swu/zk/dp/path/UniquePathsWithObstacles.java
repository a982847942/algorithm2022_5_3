package swu.zk.dp.path;

/**
 * @Classname UniquePathsWithObstacles
 * @Description
 * 这是 LeetCode 上的「63. 不同路径 II」，难度为 Medium。
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：2
 * 解释：
 * 3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 * @Date 2022/6/13 17:39
 * @Created by brain
 */
public class UniquePathsWithObstacles {
    /**
     *dp[i][j] 表示 0,0到i,j有多少种方法
     */
    private static int uniquePathsWithObstacles1(int[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0] == 1 ? 0 : 1;
        for (int i = 1; i < n; i++) {
            dp[0][i] = grid[0][i] == 1 ? 0 : dp[0][i- 1];
        }
        for (int i = 1; i < m; i++) {
            dp[i][0] = grid[i][0] == 1 ? 0 : dp[i - 1][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1){
                    dp[i][j] = 0;
                }else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 使用空间压缩
     * @param grid
     * @return
     */
    private static int uniquePathsWithObstacles3(int[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0] == 1 ? 0 : 1;
        for (int i = 1; i < n; i++) {
            dp[i] = grid[0][i] == 1 ? 0 : dp[i-1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1){
                    dp[j] = 0;
                }else {
                    if (j != 0){
                        dp[j] = dp[j] + dp[j - 1];
                    }
                }
            }
        }
        return dp[n - 1];
    }

    /**
     * dp[i][j] 表示 i,j 到 目标位置有多少种方法
     * @param grid
     * @return
     */
    private static int uniquePathsWithObstacles2(int[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[m- 1][n - 1] = grid[m - 1][n - 1] == 1 ? 0 : 1;
        for (int i = n - 2; i >= 0; i--) {
            dp[m - 1][i] = grid[m - 1][i]  == 1 ? 0 : dp[m-1][i + 1];
        }
        for (int i = m - 2; i >= 0 ; i--) {
            dp[i][n - 1] = grid[i][n - 1] == 1 ? 0 : dp[i + 1][n - 1];
        }
        for (int i = m - 2; i >=  0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (grid[i][j] == 1){
                    dp[i][j] = 0;
                }else {
                    dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[][] arr = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println(uniquePathsWithObstacles1(arr));
        System.out.println(uniquePathsWithObstacles2(arr));
        System.out.println(uniquePathsWithObstacles3(arr));
    }
}
