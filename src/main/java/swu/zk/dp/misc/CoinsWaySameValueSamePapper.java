package swu.zk.dp.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CoinsWaySameValueSamePapper
 * @Description arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 * 这道题实际上也是一个多重背包问题，物品个数为arr.length
 * 价值和重量为arr[i]  目标为aim
 * 求：恰好满足价值为 4 的方案。
 * 但需要注意的是 如果采用多重背包转化为0-1背包 和 二进制优化等手段时 会产生重复解
 * 使用朴素的多重背包解法：在进行状态转移时是描述了 使用多少件 i 号物品
 * 如果是0-1背包来求的话 比如 1 1 1 aim = 2  则进行状态转移是 dp[1][1] = dp[0][0] + dp[0][1] = 2
 * 意思就是 1 1不同
 * @Date 2022/6/18 13:23
 * @Created by brain
 */
public class CoinsWaySameValueSamePapper {
    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    /**
     * 多重背包
     */
    public static int getMethods(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        Info info = getInfo(coins);
        int[] zhangs = info.zhangs;
        int[] cos = info.coins;
        int n = cos.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i < n + 1; i++) {
            int temp = cos[i - 1];
            for (int j = 0; j < target + 1; j++) {
                for (int k = 0; k <= zhangs[i - 1] && j - k * temp >= 0; k++) {
                    dp[i][j] += dp[i - 1][j - k * temp];
                }
            }
        }
        return dp[n][target];
    }

    public static int getMethods2(int[] coins, int target) {
        Info info = getInfo(coins);
        int[] cs = info.coins;
        int[] zhangs = info.zhangs;
        int n = cs.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i < n + 1; i++) {
            int temp = cs[i - 1];
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                /**
                 * 因为试求 方案数 可以进行累加
                 */
                if (j - temp >= 0) {
                    dp[i][j] += dp[i][j - temp];
                }

                /**
                 * 当前位置 减去物品最大个数所占空间后 仍然不小于0 说明在个数允许范围内 多加了一个
                 *
                 * ... 2   4   6  8  10   i
                 *                8  10   i+1
                 *物品重量价值都为2 个数为2
                 * dp[i+1][10] = dp[i][10] + dp[i][8] + dp[i][6]
                 * dp[i+1][8] = dp[i][8] + dp[i][6] + dp[i][4]
                 * 经过上面计算
                 * dp[i + 1][10] = dp[i][10] + dp[i + 1][8]
                 * 可以看出在物品个数允许范围内 多加了一个dp[i][4]
                 * 4 = 10 - 2 * (2 + 1) >= 0
                 * 因此要减去
                 *
                 * 值得一提的是 如果这里求方案数 改变为在背包容量为target条件下求最大价值
                 * 还能不能这样进行状态转移？？？  答案是不能的
                 * 因为此时
                 * dp[i+1][10] = max(dp[i][10], dp[i][8] + temp ,dp[i][6] + 2*temp)
                 * dp[i+1][8] = max(dp[i][8] ,dp[i][6] * temp , dp[i][4] + 2*temp)
                 * 如果使用dp[i+1][10] = max(dp[i][10],dp[i+1][8])是错误的
                 * 因为不知道dp[i+1][8]是由哪个状态迁移过来的 如果是由dp[i][4] + 2*temp迁移而来
                 * 那么自然就不应该进行转移了
                 * 但是求价值的时候又不关心每个物品是否相同，因此可以使用0-1背包转二进制优化
                 * 或者维护窗口求单调队列最值 来进行转移，同样可以省去枚举时间
                 */
                if (j - temp * (zhangs[i - 1] + 1) >= 0) {
                    dp[i][j] -= dp[i - 1][j - temp * (zhangs[i - 1] + 1)];
                }
            }
        }
        return dp[n][target];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 1, 1, 2, 1, 2};
        System.out.println(getMethods(coins, 4));
        System.out.println(getMethods2(coins,4));
    }
}
