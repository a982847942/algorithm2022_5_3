package swu.zk.dp.path;

/**
 * @Classname MinPathSum
 * @Description 这是 LeetCode 上的「64. 最小路径和」，难度为 Medium。
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 * @Date 2022/6/13 17:59
 * @Created by brain
 */
public class MinPathSum {
    /**
     * dp[i][j] 表示 0,0到i,j的最小代价
     *
     * @param grid
     * @return
     */
    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 空间压缩
     *
     * @param grid
     * @return
     */
    public static int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    dp[j] = dp[j] + grid[i][j];
                } else {
                    dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
                }
            }
        }
        return dp[n - 1];
    }


    /**
     * dp[i][j] 表示i,j到目标位置的最小路径代价
     *
     * @param grid
     * @return
     */
    public static int minPathSum3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = grid[m - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            dp[m - 1][i] = dp[m - 1][i + 1] + grid[m - 1][i];
        }
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = dp[i + 1][n - 1] + grid[i][n - 1];
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + grid[i][j];
            }
        }
        return dp[0][0];
    }


    public static void main(String[] args) {
        int[][] arr = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
//        System.out.println(minPathSum(arr));
//        System.out.println(minPathSum2(arr));
//        System.out.println(minPathSum3(arr));
        System.out.println(minPathSum4(arr));
    }

    static int m;
    static int n;

    public static int minPathSum4(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int[][] dp = new int[m][n];
        int[] g = new int[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else {
                    int top = i - 1 >= 0 ? dp[i - 1][j] + grid[i][j] : Integer.MAX_VALUE;
                    int left = j - 1 >= 0 ? dp[i][j - 1] + grid[i][j] : Integer.MAX_VALUE;
                    dp[i][j] = Math.min(top, left);
                    g[getIdx(i, j)] = top < left ? getIdx(i - 1, j) : getIdx(i, j - 1);
                }
            }
        }

        int[][] path = new int[m + n][2];
        path[m + n - 1] = new int[]{m - 1, n - 1};
        int idx = getIdx(m - 1, n - 1);
        for (int i = 1; i < m + n; i++) {
            path[m + n - 1 - i] = parseIdx(g[idx]);
            idx = g[idx];
        }
        // 顺序输出位置
        for (int i = 1; i < m + n; i++) {
            int x = path[i][0], y = path[i][1];
            System.out.print("(" + x + "," + y + ") ");
        }
        System.out.println(" ");
        return dp[m - 1][n - 1];
    }


    static int[] parseIdx(int idx) {
        return new int[]{idx / n, idx % n};
    }

    static int getIdx(int x, int y) {
        return x * n + y;
    }



    public int minPathSum5(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int[][] f = new int[m][n];
        int[] g = new int[m * n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    f[i][j] = grid[i][j];
                } else {
                    int bottom = i + 1 < m ? f[i + 1][j] + grid[i][j] : Integer.MAX_VALUE;
                    int right  = j + 1 < n ? f[i][j + 1] + grid[i][j] : Integer.MAX_VALUE;
                    f[i][j] = Math.min(bottom, right);
                    g[getIdx(i, j)] = bottom < right ? getIdx(i + 1, j) : getIdx(i, j + 1);
                }
            }
        }

        int idx = getIdx(0,0);
        for (int i = 1; i <= m + n; i++) {
            if (i == m + n) continue;
            int x = parseIdx(idx)[0], y = parseIdx(idx)[1];
            System.out.print("(" + x + "," + y + ") ");
            idx = g[idx];
        }
        System.out.println(" ");

        return f[0][0];
    }
}
