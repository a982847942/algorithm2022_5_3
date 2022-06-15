package swu.zk.dp.path;

import java.util.Arrays;

/**
 * @Classname FindPaths
 * @Description
 * 这是 LeetCode 上的「576. 出界的路径数」，难度为 Medium
 * 给定一个 m*n的网格和一个球。
 * 球的起始坐标为(i,j)，你可以将球移到相邻的单元格内，或者往上、下、左、右四个方向上移动使球穿过网格边界。
 * 但是，你最多可以移动N次。
 * 找出可以将球移出边界的路径数量。答案可能非常大，返回结果 mod 10的9次方 + 7 的值
 * @Date 2022/6/14 21:24
 * @Created by brain
 */
public class FindPaths {
    static int mod = 1000000007;
    static int[][] direction = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    static int[][][] memory;

    public static int findPaths1(int m, int n, int N, int i, int j) {
        memory = new int[m][n][N + 1];
        for (int k = 0; k < m; k++) {
            for (int l = 0; l < n; l++){
                Arrays.fill(memory[k][l],-1);
            }
        }
        return process(m, n, N, i, j) % mod;
    }

    private static int process(int m, int n, int restStep, int curX, int curY) {
        if (curX < 0 || curX >= m || curY < 0 || curY >= n) return 0;
        if (memory[curX][curY][restStep] != -1) return memory[curX][curY][restStep];
        if (restStep > 0) {
            int ans = 0;
            for (int i = 0; i < 4; i++) {
                int nextX = curX + direction[i][0];
                int nextY = curY + direction[i][1];
                ans += process(m, n, restStep - 1, nextX, nextY);
                ans %= mod;
            }
            if (curX == 0) ans += 1;
            if (curY == 0) ans += 1;
            if (curX == m - 1) ans += 1;
            if (curY == n - 1) ans += 1;
            ans %= mod;
            memory[curX][curY][restStep] = ans;
            return ans;
        } else {
            return 0;
        }

        /**
         * 为什么错误？
         * 因为理解题意有问题。restStep为0时候 已经不能再走了，而此时还在范围内，因为应该返回0
         */
//        if (curX < 0 || curX >= m || curY < 0 || curY >= n) return 0;
//        if (restStep > 0) {
//            int ans = 0;
//            for (int i = 0; i < 4; i++) {
//                int nextX = curX + direction[i][0];
//                int nextY = curY + direction[i][1];
//                ans += process(m, n, restStep - 1, nextX, nextY);
//                ans %= mod;
//            }
//            if (curX == 0) ans += 1;
//            if (curY == 0) ans += 1;
//            if (curX == m - 1) ans += 1;
//            if (curY == n - 1) ans += 1;
//            ans %= mod;
//            return ans;
//        } else if (restStep == 0) {
//            int ans = 0;
//            if (curX == 0) ans += 1;
//            if (curY == 0) ans += 1;
//            if (curX == m - 1) ans += 1;
//            if (curY == n - 1) ans += 1;
//            ans %= mod;
//            return ans;
//        } else {
//            return 0;
//        }
    }

    public static void main(String[] args) {
        int m = 2, n = 2, N = 2, i = 0, j = 0;
        System.out.println(findPaths1(m, n, N, i, j));
        System.out.println(findPaths(m, n, N, i, j));
    }


    static int m, n, N;

    public static int findPaths(int _m, int _n, int _N, int _i, int _j) {
        m = _m;
        n = _n;
        N = _N;

        // f[i][j] 代表从 idx 为 i 的位置出发，移动步数不超过 j 的路径数量
        int[][] f = new int[m * n][N + 1];

        // 初始化边缘格子的路径数量
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) add(i, j, f);
                if (i == m - 1) add(i, j, f);
                if (j == 0) add(i, j, f);
                if (j == n - 1) add(i, j, f);
            }
        }

        // 定义可移动的四个方向
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        // 从小到大枚举「可移动步数」
        for (int step = 1; step <= N; step++) {
            // 枚举所有的「位置」
            for (int k = 0; k < m * n; k++) {
                int x = parseIdx(k)[0], y = parseIdx(k)[1];
                for (int[] d : dirs) {
                    int nx = x + d[0], ny = y + d[1];
                    // 如果位置有「相邻格子」，则「相邻格子」参与状态转移
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                        f[k][step] += f[getIndex(nx, ny)][step - 1];
                        f[k][step] %= mod;
                    }
                }
            }
        }

        // 最终结果为从起始点触发，最大移动步数不超 N 的路径数量
        return f[getIndex(_i, _j)][N];
    }

    // 为每个「边缘」格子，添加一条路径
    static void add(int x, int y, int[][] f) {
        int idx = getIndex(x, y);
        for (int step = 1; step <= N; step++) {
            f[idx][step]++;
        }
    }

    // 将 (x, y) 转换为 index
    static int getIndex(int x, int y) {
        return x * n + y;
    }

    // 将 index 解析回 (x, y)
    static int[] parseIdx(int idx) {
        return new int[]{idx / n, idx % n};
    }
}
