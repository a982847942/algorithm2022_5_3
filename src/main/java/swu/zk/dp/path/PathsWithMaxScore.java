package swu.zk.dp.path;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname PathsWithMaxScore
 * @Description 这是 LeetCode 上的「1301. 最大得分的路径数目」，难度为 Hard。
 * 给你一个正方形字符数组 board ，你从数组最右下方的字符 'S' 出发。
 * 你的目标是到达数组最左上角的字符 'E' ，数组剩余的部分为数字字符 1,2,...,9 或者障碍 'X'。
 * 在每一步移动中，你可以向上、向左或者左上方移动，可以移动的前提是到达的格子没有障碍。
 * 一条路径的 「得分」 定义为：路径上所有数字的和。
 * 请你返回一个列表，包含两个整数：第一个整数是 「得分」 的最大值，第二个整数是得到最大得分的方案数，
 * 请把结果对 10^9 + 7 取余。
 * 如果没有任何路径可以到达终点，请返回 [0, 0] 。
 * 输入：board = ["E23","2X2","12S"]
 * 输出：[7,1]
 * @Date 2022/6/14 22:37
 * @Created by brain
 */
public class PathsWithMaxScore {
    static int mod = (int) 1e9 + 7;
    static int[][] direction = {{0, -1}, {-1, -1}, {-1, 0}};
    static int[][][] memory;

    /**
     * dp[i][j] 表示从出发位置到 i,j的最大得分
     *
     * @param board
     * @return
     */
    public static int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            char temp = board.get(n - 1).charAt(i);
            dp[n - 1][i] = temp == 'X' ? 0 : dp[n - 1][i + 1] + (board.get(n - 1).charAt(i) - '0');
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (board.get(i).charAt(j) != 'X') {
                    int temp = board.get(i).charAt(j) - '0';
                    if (j == n - 1) {
                        dp[i][j] = dp[i + 1][j] + temp;
                    } else {
                        dp[i][j] = Math.max(Math.max(dp[i + 1][j], dp[i + 1][j + 1]), dp[i][j + 1]) + temp;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        int maxValue = dp[0][0] - ('E' - '0');
        memory = new int[n][n][maxValue + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(memory[i][j], -1);
            }
        }

        int paths = process(0, n, 0, n - 1, n - 1, maxValue, board);
        maxValue = paths == 0 ? 0 : maxValue;
        return new int[]{maxValue, paths};
    }

    public static int process(int start, int end, int curValue, int curX, int curY, int maxValue, List<String> board) {
        if (curX < 0 || curX >= end || curY < 0 || curY >= end) return 0;
        if (board.get(curX).charAt(curY) == 'X') return 0;

        if (curX == start && curY == start) {
            int ans = curValue == maxValue ? 1 : 0;
            return ans;
        }
        char temp = board.get(curX).charAt(curY);
        if (temp == 'S') {
            curValue += 0;
        } else {
            curValue += (temp - '0');
        }
        if (memory[curX][curY][curValue] != -1) return memory[curX][curY][curValue];
        int ans = 0;
        for (int i = 0; i < 3; i++) {
            int nextX = curX + direction[i][0];
            int nextY = curY + direction[i][1];
            ans += process(start, end, curValue, nextX, nextY, maxValue, board);
            ans %= mod;
        }
        memory[curX][curY][curValue] = ans;
        return ans;
    }




    static int n;
    static int INF = Integer.MIN_VALUE;
    /**
     * 上面的方法 在时间复杂度方面 已经达到最优，但是常数时间太大。
     * 因为是两个过程分开求解，因此浪费了很多时间
     * value[i] 表示从出发位置到 i 位置的最大得分
     * path[i] 表示从出发位置到 i 位置的最大得分对应的路径数
     * 时间复杂度：共有 n*n个状态需要被转移，复杂度为O(n2)
     * 空间复杂度：O(n2)
     * @param board
     * @return
     */
    public static int[] pathsWithMaxScore3(List<String> board) {
        n = board.size();
        char[][] ch = new char[n][n];
        for (int i = 0; i < board.size(); i++) {
            ch[i] = board.get(i).toCharArray();
        }
        int[] value = new int[n * n];
        int[] paths = new int[n * n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                //处理开始位置
                if (i == n - 1 && j == n - 1) {
                    value[getIndex(i, j)] = 0;
                    paths[getIndex(i, j)] = 1;
                    continue;
                }

                //处理障碍物
                if (ch[i][j] == 'X') {
                    value[getIndex(i, j)] = INF;
                    paths[getIndex(i, j)] = 0;
                    continue;
                }

                int val = (i == 0 && j == 0) ? 0 : ch[i][j] - '0';
                int curValue = INF;
                int curPath = 0;
                /**
                 * 这里需要注意 因为上面在遇到X时吧 value设置为INF 因此有可能tempValue 为 INF
                 * 此时加上val 会得到一个小于0 但是大于 Integer.MIN_VALUE 的值
                 */
                if (i + 1 < n) {
                    int tempValue = value[getIndex(i + 1, j)] + val;
                    int tempPath = paths[getIndex(i + 1, j)];
                    int[] res = update(curValue, curPath, tempValue, tempPath);
                    curValue = res[0];
                    curPath = res[1];
                }

                if (j + 1 < n) {
                    int tempValue = value[getIndex(i, j + 1)] + val;
                    int tempPath = paths[getIndex(i, j + 1)];
                    int[] res = update(curValue, curPath, tempValue, tempPath);
                    curValue = res[0];
                    curPath = res[1];
                }

                if (i + 1 < n && j + 1 < n) {
                    int tempValue = value[getIndex(i + 1, j + 1)] + val;
                    int tempPath = paths[getIndex(i + 1, j + 1)];
                    int[] res = update(curValue, curPath, tempValue, tempPath);
                    curValue = res[0];
                    curPath = res[1];
                }
//                value[getIndex(i, j)] = curValue < 0 ? INF : curValue;
                value[getIndex(i,j)] = curValue;
                paths[getIndex(i, j)] = curPath;
            }
        }

        int[] res = new int[2];
        res[0] = value[0] == INF ? 0 : value[0];
//        res[1] = paths[0] == INF ? 0 : paths[0];
        res[1] = paths[0];
        return res;
    }

    private static int[] update(int curValue, int curPath, int tempValue, int tempPath) {
        int[] ans = {curValue,curPath};
        if (tempValue > curValue) {
            ans[0] = tempValue;
            ans[1] = tempPath;
        } else if (tempValue == curValue && tempValue != INF) {
            ans[1] += tempPath;
        }
        ans[1] %= mod;
        return ans;
    }

    private static int getIndex(int i, int j) {
        return i * n + j;
    }


    public static void main(String[] args) {
        List<String> board = Arrays.asList("E11345", "X452XX", "3X43X4", "422812", "284522", "13422S");
        int[] res = pathsWithMaxScore(board);
        System.out.println(res[0] + "=======" + res[1]);
        int[] res2 = pathsWithMaxScore3(board);
        System.out.println(res2[0] + "=======" + res2[1]);
//        System.out.println(2 + 'E' - '0');
    }

}
