package swu.zk.dp.misc;

/**
 * @Classname BobDie
 * @Description 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 * @Date 2022/6/19 10:22
 * @Created by brain
 */
public class BobDie {
    public static double livePossibility(int row, int col, int k, int N, int M) {
        return (double) (process(row, col, k, N, M)) / Math.pow(4, k);
    }

    private static int process(int row, int col, int k, int N, int M) {
        if (row < 0 || col < 0 || row >= N || col >= M) return 0;
        if (k == 0) return 1;
        int ans = 0;
        ans += process(row + 1, col, k - 1, N, M);
        ans += process(row - 1, col, k - 1, N, M);
        ans += process(row, col + 1, k - 1, N, M);
        ans += process(row, col - 1, k - 1, N, M);
        return ans;
    }

    public static double dp(int row, int col, int k, int N, int M) {
        long[][][] dp = new long[k + 1][N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[0][i][j] = 1;
            }
        }

        for (int rest = 1; rest < k + 1; rest++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    dp[rest][i][j]  = pick(dp,i + 1, j, N, M,rest - 1);
                    dp[rest][i][j] += pick(dp,i - 1, j, N, M,rest - 1);
                    dp[rest][i][j] += pick(dp,i, j + 1, N, M,rest - 1);
                    dp[rest][i][j] += pick(dp,i, j - 1, N, M,rest - 1);
                }
            }
        }
        return dp[k][row][col] / Math.pow(4,k);
    }
    public static long pick(long[][][] dp, int row, int col, int N, int M, int rest) {
        if (row < 0 || row >= N || col < 0 || col >= M) {
            return 0;
        }
        return dp[rest][row][col];
    }


    public static void main(String[] args) {
        System.out.println(livePossibility(6, 6, 10, 50, 50));
        System.out.println(dp(6, 6, 10, 50, 50));
    }
}
