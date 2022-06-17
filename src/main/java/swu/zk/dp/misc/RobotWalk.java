package swu.zk.dp.misc;

/**
 * @Classname RobotWalk
 * @Description 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数。
 * @Date 2022/6/17 17:52
 * @Created by brain
 */
public class RobotWalk {
    public static int ways(int N, int M, int P, int K) {
        return process(N, M, P, K);
    }

    private static int process(int n, int cur, int aim, int rest) {
        if (rest < 0) return 0;
        if (cur == aim && rest == 0) return 1;
        int ans = 0;
        if (cur == 1) {
            ans = process(n, cur + 1, aim, rest - 1);
        } else if (cur == n) {
            ans = process(n, cur - 1, aim, rest - 1);
        } else {
            ans += process(n, cur + 1, aim, rest - 1);
            ans += process(n, cur - 1, aim, rest - 1);
        }
        return ans;
    }

    /**
     * dp[i][j]表示 当前还可以走 i 步 当前位置为 j 有多少种方法可以到达指定位置
     */
    public static int ways2(int N, int M, int P, int K) {
        int[][] dp = new int[K + 1][N + 1];
        dp[0][P] = 1;
        for (int i = 1; i < K + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (j == 1) {
                    dp[i][j] = dp[i - 1][j + 1];
                } else if (j == N) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                }
            }
        }
        return dp[K][M];
    }

    /**
     * 滚动数组
     */
    public static int ways3(int N, int M, int P, int K) {
        int[][] dp = new int[2][N + 1];
        dp[0][P] = 1;
        for (int i = 1; i < K + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (j == 1) {
                    dp[i & 1][j] = dp[(i - 1) & 1][j + 1];
                } else if (j == N) {
                    dp[i & 1][j] = dp[(i - 1) & 1][j - 1];
                } else {
                    dp[i & 1][j] = dp[(i - 1) & 1][j - 1] + dp[(i - 1) & 1][j + 1];
                }
            }
        }
        return dp[K & 1][M];
    }

    public static void main(String[] args) {
        System.out.println(ways(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
