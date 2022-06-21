package swu.zk.dp.knapsack;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname FindTargetSumWays_01
 * @Description 这是 LeetCode 上的「494. 目标和」，难度为「中等」。
 * Tag : 「DFS」、「记忆化搜索」、「背包 DP」、「01 背包」
 * 给你一个整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，
 * 然后串联起来得到表达式 "+2-1" 。返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * @Date 2022/6/21 21:40
 * @Created by brain
 */
public class FindTargetSumWays_01 {
    public static int findTargetSumWays(int[] nums, int target) {
        return process(nums, 0, 0, target);
    }

    private static int process(int[] nums, int cur, int sum, int target) {
        if (cur == nums.length) {
            return sum == target ? 1 : 0;
        }
        int p1 = process(nums, cur + 1, sum + nums[cur], target);
        int p2 = process(nums, cur + 1, sum - nums[cur], target);
        return p1 + p2;
    }

    public static int findTargetSumWays2(int[] nums, int target) {
        return process2(nums, 0, 0, target);
    }

    static Map<String, Integer> cache = new HashMap<>();

    private static int process2(int[] nums, int cur, int sum, int target) {
        String key = cur + "_" + sum;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (cur == nums.length) {
            return sum == target ? 1 : 0;
        }
        int p1 = process2(nums, cur + 1, sum + nums[cur], target);
        int p2 = process2(nums, cur + 1, sum - nums[cur], target);
        cache.put(key, p1 + p2);
        return p1 + p2;
    }

    /**
     * 动态规划
     */
    public static int findTargetSumWays3(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += Math.abs(num);
        }
        if (Math.abs(target) > sum) return 0;
        int[][] dp = new int[n + 1][2 * sum + 1];
        dp[0][sum] = 1;
        for (int i = 1; i < n + 1; i++) {
            int temp = nums[i - 1];
            for (int j = -sum; j <= sum; j++) {

                if (j - temp + sum >= 0) dp[i][j + sum] += dp[i-1][j - temp + sum];
                if (j + temp + sum <= 2*sum) dp[i][j + sum] += dp[i-1][j + temp + sum];
            }
        }
        return dp[n][target + sum];
    }

    public int findTargetSumWays4(int[] nums, int S) {
        int sum = 0;
        for(int num:nums){
            sum += num;
        }
        if(sum < S) return 0;
        if((sum + S) % 2 != 0) return 0;
        int target = (sum + S) /2 ;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int num : nums){
            for(int i = target;i >= num;i--){
                dp[i] = dp[i] + dp[i - num];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] nums = {1};
        int target = 2;
        System.out.println(findTargetSumWays(nums, target));
        System.out.println(findTargetSumWays3(nums, target));
//        System.out.println(findTargetSumWays4(nums, target));
    }
}
