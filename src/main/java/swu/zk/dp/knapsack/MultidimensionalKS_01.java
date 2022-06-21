package swu.zk.dp.knapsack;

/**
 * @Classname MultidimensionalKS_01
 * @Description 这是 LeetCode 上的「474. 一和零」，难度为 Medium。
 * 给你一个二进制字符串数组 strs和两个整数 m 和 n。
 * 请你找出并返回 strs的最大子集的大小，该子集中 最多 有 m 个 0 和 n 个 1。
 * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的子集 。
 * <p>
 * 输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
 * 输出：4
 * 解释：最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
 * 其他满足题意但较小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"}
 * 不满足题意，因为它含 4 个 1 ，大于 n 的值 3 。
 * <p>
 * 转化为背包问题：
 * 每个字符串的价值都是 1（对答案的贡献都是1），选择的成本是该字符串中 1的数量和 0的数量。
 * 问我们在 1的数量不超过m，0 的数量不超过n的条件下，最大价值是多少。
 * 由于每个字符串只能被选一次，且每个字符串的选与否对应了「价值」和「成本」，求解的问题也是「最大价值」是多少。
 * @Date 2022/6/21 15:09
 * @Created by brain
 */
public class MultidimensionalKS_01 {
    /**
     * dp[i][j][k] 表示 考虑前i个物品 在0的个数不超过m n的个数不超过k 的条件下 的 最大价值(使用的字符串个数)
     * <p>
     * dp[i][j][k] = Math.max(dp[i-1][j][k],dp[i-1][j - count[i][zero]][k - count[i][one]] + 1)
     */
    public static int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] count = new int[len][2];
        int index = 0;
        //预处理每个字符串的0和1个数
        for (String str : strs) {
            int zero = 0, one = 0;
            for (char c : str.toCharArray()) {
                if (c == '1') {
                    one++;
                } else {
                    zero++;
                }
            }
            count[index++] = new int[]{zero, one};
        }
        int[][][] dp = new int[len][m + 1][n + 1];
        //初始化dp  也可以使用哨兵
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                dp[0][i][j] = (i >= count[0][0] && j >= count[0][1]) ? 1 : 0;
            }
        }

        for (int i = 1; i < len; i++) {
            int zero = count[i][0];
            int one = count[i][1];
            for (int j = 0; j < m + 1; j++) {
                for (int k = 0; k < n + 1; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (j >= zero && k >= one) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - zero][k - one] + 1);
                    }
                }
            }
        }
        return dp[len - 1][m][n];
    }


    public static int findMaxForm2(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] count = new int[len][2];
        int index = 0;
        //预处理每个字符串的0和1个数
        for (String str : strs) {
            int zero = 0, one = 0;
            for (char c : str.toCharArray()) {
                if (c == '1') {
                    one++;
                } else {
                    zero++;
                }
            }
            count[index++] = new int[]{zero, one};
        }
        int[][][] dp = new int[len + 1][m + 1][n + 1];
        for (int i = 1; i < len + 1; i++) {
            int zero = count[i - 1][0];
            int one = count[i - 1][1];
            for (int j = 0; j < m + 1; j++) {
                for (int k = 0; k < n + 1; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];

                    if (j >= zero && k >= one) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - zero][k - one] + 1);
                    }
                }
            }
        }
        return dp[len][m][n];
    }

    /**
     * 空间压缩
     */
    public static int findMaxForm3(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] count = new int[len][2];
        int index = 0;
        //预处理每个字符串的0和1个数
        for (String str : strs) {
            int zero = 0, one = 0;
            for (char c : str.toCharArray()) {
                if (c == '1') {
                    one++;
                } else {
                    zero++;
                }
            }
            count[index++] = new int[]{zero, one};
        }
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < len; i++) {
            int zero = count[i][0];
            int one = count[i][1];
            for (int j = m; j >= 0; j--) {
                for (int k = n; k >= 0; k--) {
                    if (j >= zero && k >= one) dp[j][k] = Math.max(dp[j][k], dp[j - zero][k - one] + 1);
                }
            }

        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        String[] strs = {"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;
        System.out.println(findMaxForm(strs, m, n));
        System.out.println(findMaxForm2(strs, m, n));
        System.out.println(findMaxForm3(strs, m, n));
    }
}
