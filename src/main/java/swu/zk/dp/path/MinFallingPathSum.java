package swu.zk.dp.path;

/**
 * @Classname MinFallingPathSum
 * @Description 这是 LeetCode 上的「931. 下降路径最小和」，难度为 Medium。
 * 给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的「下降路径」的「最小和」。
 * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。
 * 在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。
 * 具体来说，位置 (row,col) 的下一个元素应当是 (row+1,col-1)、(row+1,col) 或者 (row+1,col+1)。
 * 输入：matrix = [[2,1,3],[6,5,4],[7,8,9]]
 * 输出：13
 * @Date 2022/6/14 9:40
 * @Created by brain
 */
public class MinFallingPathSum {

    /**
     * 时间复杂度O(n3)
     *
     * @param mat
     * @return
     */
    public static int minFallingPathSum(int[][] mat) {
        int n = mat.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, find(mat, i, n));
        }
        return ans;
    }

    /**
     * dp[i][j] 表示 0,curColumn 到 i,j的最小代价
     *注意不能使用空间压缩技巧 因为依赖于左右上三个位置
     * @param mat
     * @param curColumn
     * @param n
     * @return
     */
    private static int find(int[][] mat, int curColumn, int n) {
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = i == curColumn ? mat[0][i] : Integer.MAX_VALUE;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                int temp = mat[i][j];
                if (dp[i - 1][j] != Integer.MAX_VALUE) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + temp);
                if (j - 1 >= 0 && dp[i - 1][j - 1] != Integer.MAX_VALUE)
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + temp);
                if (j + 1 < n && dp[i - 1][j + 1] != Integer.MAX_VALUE)
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1] + temp);
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }
        return ans;
    }


    /**
     * 仔细思考上述方法，是否能够不用枚举起点？
     * dp[i][j]是定义的到达i,j位置的最小路径代价，那么初始化时是否可以对第一行初始化为mat矩阵的初始值。
     * 这样就保证了basecase符合的dp[i][j]的定义，接下来按照上述方法进行状态转移即可。
     *时间复杂度为O(n2)
     * @param mat
     * @return
     */
    public static int minFallingPathSum2(int[][] mat) {
        int n = mat.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = mat[0][i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int temp = mat[i][j];
                dp[i][j] = dp[i - 1][j];
                if (j - 1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]) + temp;
                if (j + 1 < n) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1]) + temp;
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans,dp[n-1][i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] matrix = {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}};
        System.out.println(minFallingPathSum(matrix));
        System.out.println(minFallingPathSum2(matrix));
    }
}
