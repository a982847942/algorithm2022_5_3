package swu.zk.dp.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname MinimumTotal
 * @Description
 * 这是 LeetCode 上的「120. 三角形最小路径和」，难度为 Medium。
 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
 * 每一步只能移动到下一行中相邻的结点上。
 * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 * 也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * 输出：11
 * 解释：如下面简图所示：
 *    2
 *   3 4
 *  6 5 7
 * 4 1 8 3
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * @Date 2022/6/13 20:43
 * @Created by brain
 */
public class MinimumTotal {
    /**
     * dp[i][j]表示0,0 到i,j的最小代价
     * @param tri
     * @return
     */
    public static int minimumTotal(List<List<Integer>> tri) {
        int n = tri.size();
        int[][] dp = new int[n][n];
        dp[0][0] = tri.get(0).get(0);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                Integer temp = tri.get(i).get(j);
                if (j == 0){
                    dp[i][j] = dp[i - 1][j] + temp;
                }else if (j == i){
                    dp[i][j] = dp[i - 1][j - 1] + temp;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j],dp[i - 1][j - 1]) + temp;
                }
            }
        }
        int ans = dp[n- 1][0];
        for (int i = 1; i < n; i++) {
            ans = Math.min(ans,dp[n - 1][i]);
        }
        return ans;
    }

    /**
     * 空间压缩
     * @param tri
     * @return
     */
    public static int minimumTotal2(List<List<Integer>> tri) {
        int n = tri.size();
        int[] dp = new int[n];
        dp[0] = tri.get(0).get(0);
        for (int i = 1; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                Integer temp = tri.get(i).get(j);
                if (j == 0){
                    dp[j] += temp;
                }else if (j == i){
                    dp[j] = dp[j - 1] + temp;
                }else {
                    dp[j] = Math.min(dp[j],dp[j - 1]) + temp;
                }
            }
        }
        int ans = dp[0];
        for (int i = 1; i < n; i++) {
            ans = Math.min(ans,dp[i]);
        }
        return ans;
    }

    /**
     * 空间压缩 第二种技巧 滚动数组 只需要将用到i的地方换成 i&1 或 i%2即可
     * 这里是将i这一维改为2  也可以将j改为2  同理只需换成j&1 或 j%2即可
     * 实质上就是用上一种空间压缩方式(根据状态依赖调整迭代/循环的方向) 的简化而已，用了两倍的空间，但仍未O(n).
     * @param tri
     * @return
     */
    public int minimumTotal3(List<List<Integer>> tri) {
        int n = tri.size();
        int ans = Integer.MAX_VALUE;
        int[][] f = new int[2][n];
        f[0][0] = tri.get(0).get(0);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i + 1; j++) {
                int val = tri.get(i).get(j);
                f[i & 1][j] = Integer.MAX_VALUE;
                if (j != 0) f[i & 1][j] = Math.min(f[i & 1][j], f[(i - 1) & 1][j - 1] + val);
                if (j != i) f[i & 1][j] = Math.min(f[i & 1][j], f[(i - 1) & 1][j] + val);
            }
        }
        for (int i = 0; i < n; i++) ans = Math.min(ans, f[(n - 1) & 1][i]);
        return ans;
    }

    public static void main(String[] args) {
        List<List<Integer>>  lists = new ArrayList<>();
        lists.add( Arrays.asList(2));
        lists.add( Arrays.asList(3,4));
        lists.add( Arrays.asList(6,5,7));
        lists.add( Arrays.asList(4,1,8,3));
        System.out.println(minimumTotal(lists));
        System.out.println(minimumTotal2(lists));
    }
}
