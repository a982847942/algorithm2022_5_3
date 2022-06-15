package swu.zk.dp.knapsack;

/**
 * @Classname CanPartition_01
 * @Description 这是 LeetCode 上的「416. 分割等和子集」，难度为 Medium。
 * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * 注意:
 * 每个数组中的元素不会超过 100
 * 数组的大小不会超过 200
 * 输入: [1, 5, 11, 5]
 * 输出: true
 * 解释: 数组可以分割成 [1, 5, 5] 和 [11].
 */
public class CanPartition_01 {


    public static boolean canPartition(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum != (sum / 2 * 2)) return false;
        sum /= 2;
        /**
         * 转化为背包问题
         * 背包容量为sum  物品数量为n  每个物品价值为nums[i] 每个物品重量也是nums[i]
         * dp[i][j]表示考虑前i个物品 容量为j时 最大价值
         */

        int[][] dp = new int[n][sum + 1];
        for (int i = 0; i < sum + 1; i++) {
            dp[0][i] = i >= nums[0] ? nums[0] : 0;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < sum + 1; j++) {
                int temp = nums[i];
                dp[i][j] = dp[i - 1][j];
                if (j >= temp) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - temp] + temp);
                }
            }
        }
        return dp[n - 1][sum] == sum;
    }

    /**
     * 空间压缩  一维数组
     */
    public static boolean canPartition2(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum != (sum / 2 * 2)) return false;
        sum /= 2;
        int[] dp = new int[sum + 1];
        for (int i = 0; i < sum + 1; i++) {
            dp[i] = i >= nums[0] ? nums[0] : 0;
        }

        for (int i = 1; i < n; i++) {
            int temp = nums[i];
            for (int j = sum; j >= temp; j--) {
                dp[j] = Math.max(dp[j], dp[j - temp] + temp);
            }
        }
        return dp[sum] == sum;
    }

    /**
     * 上面的方法有点间接求解的意味，能不能直接求解呢？
     * dp[i][j] 表示 考虑前i个物品 价值能否恰好为 j  这样一来就转化为直接求解了
     * 初始状态为dp[0][num[0]] = true 但是num[0]可能会大于 背包容量(即第一个物品重量大于背包容量)
     * 怎么处理这个问题？ 排序？
     * 其实可以使用哨兵的思想！
     * dp[0] 表示不适用任何物品 的情况  那么对应dp[0][0] = true
     * 有了初始状态 就可以进行状态转移了
     *
     * @param nums
     * @return
     */
    public static boolean canPartition3(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum != (sum / 2 * 2)) return false;
        sum /= 2;

        boolean[][] dp = new boolean[n + 1][sum + 1];
        dp[0][nums[0]] = true;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }

    public static boolean canPartition4(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum != (sum / 2 * 2)) return false;
        sum /= 2;
        boolean[][] dp = new boolean[2][sum + 1];
        dp[0][nums[0]] = true;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                dp[i & 1][j] = dp[(i - 1) & 1][j];
                if (j >= nums[i - 1]) {
                    dp[i & 1][j] = dp[i & 1][j] || dp[(i - 1) & 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n & 1][sum];
    }
    public static boolean canPartition5(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum != (sum / 2 * 2)) return false;
        sum /= 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int i = 1; i < n + 1; i++) {
            for (int j = sum; j >= nums[i - 1] ; j--) {
                dp[j] = dp[j] || dp[j-nums[i - 1]];
            }
        }
        return dp[sum];
    }


    public static void main(String[] args) {
        int[] nums = {1, 5, 11, 5};
        System.out.println(canPartition(nums));
        System.out.println(canPartition2(nums));
        System.out.println(canPartition3(nums));
        System.out.println(canPartition4(nums));
        System.out.println(canPartition5(nums));
    }
}
