package swu.zk.dp.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MultipleKS
 * @Description 有 N 种物品和一个容量为 C 的背包，每种物品「数量有限」。
 * 第 i 件物品的体积是v[i]，价值是w[i]，数量为s[i]。
 * 问选择哪些物品，每件物品选择多少件，可使得总价值最大。
 * 其实就是在 0-1 背包问题的基础上，增加了每件物品可以选择「有限次数」的特点（在容量允许的情况下）。
 * <p>
 * 输入: N = 2, C = 5, v = [1,2], w = [1,2], s = [2,1]
 * 输出: 4
 * 解释: 选两件物品 1，再选一件物品 2，可使价值最大。
 * @Date 2022/6/16 15:53
 * @Created by brain
 */
public class MultipleKS {
    /**
     * dp[i][j] 表示 在考虑前 i件物品 且容量为j时  能获取的最大价值
     */
    public static int maxValue(int N, int C, int[] s, int[] v, int[] w) {
        int[][] dp = new int[N][C + 1];
        for (int i = 0; i < C + 1; i++) {
            int num = s[0];
            int temp = v[0];
            int cnt = i / temp;
            if (cnt < num) {
                dp[0][i] = cnt * w[0];
            } else {
                dp[0][i] = num * w[0];
            }
        }

        for (int i = 1; i < N; i++) {
            int temp = v[i];
            int cnt = s[i];
            for (int j = 0; j < C + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int k = 0; k <= cnt && j - k * temp >= 0; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * temp] + k * w[i]);
                }
            }
        }
        return dp[N - 1][C];
    }

    public static int maxValue2(int N, int C, int[] s, int[] v, int[] w) {
        int[] dp = new int[C + 1];
        for (int i = 0; i < C + 1; i++) {
            int maxK = Math.min(i / v[0], s[0]);
            dp[i] = maxK * w[0];
        }

        for (int i = 1; i < N; i++) {
            int temp = v[i];
            int cnt = s[i];
            for (int j = C; j >= temp; j--) {
                for (int k = 1; k <= cnt && j - k * temp >= 0; k++) {
                    dp[j] = Math.max(dp[j], dp[j - k * temp] + k * w[i]);
                }
            }
        }
        return dp[C];
    }

    /**
     * 将多重背包转化为0-1背包
     * @return
     */
    public static int maxValue3(int N, int C, int[] s, int[] v, int[] w) {
        // 将多件数量的同一物品进行「扁平化」展开，以 [v, w] 形式存储
        List<int[]> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int cnt = s[i];
            while (cnt-- > 0) {
                arr.add(new int[]{v[i], w[i]});
            }
        }

        // 使用「01 背包」进行求解
        int[] dp = new int[C + 1];
        for (int i = 0; i < arr.size(); i++) {
            int vi = arr.get(i)[0], wi = arr.get(i)[1];
            for (int j = C; j >= vi; j--) {
                dp[j] = Math.max(dp[j], dp[j - vi] + wi);
            }
        }
        return dp[C];
    }

    /**
     * 二进制优化
     */
    public static int maxValue4(int N, int C, int[] s, int[] v, int[] w) {
        // 扁平化
        List<Integer> worth = new ArrayList<>();
        List<Integer> volume = new ArrayList<>();

        // 我们希望每件物品都进行扁平化，所以首先遍历所有的物品
        for (int i = 0; i < N; i++) {
            // 获取每件物品的出现次数
            int val = s[i];
            // 进行扁平化：如果一件物品规定的使用次数为 7 次，
            // 我们将其扁平化为三件物品：1*重量&1*价值、2*重量&2*价值、4*重量&4*价值
            // 三件物品都不选对应了我们使用该物品 0 次的情况、
            // 只选择第一件扁平物品对应使用该物品 1 次的情况、只选择第二件扁平物品对应使用该物品 2 次的情况，
            // 只选择第一件和第二件扁平物品对应了使用该物品 3 次的情况 ...
            for (int k = 1; k <= val; k *= 2) {
                val -= k;
                worth.add(w[i] * k);
                volume.add(v[i] * k);
            }
            if (val > 0) {
                worth.add(w[i] * val);
                volume.add(v[i] * val);
            }
        }

        // 0-1 背包问题解决方案
        int[] dp = new int[C + 1];
        for (int i = 0; i < worth.size(); i++) {
            for (int j = C; j >= volume.get(i); j--) {
                dp[j] = Math.max(dp[j], dp[j - volume.get(i)] + worth.get(i));
            }
        }
        return dp[C];
    }

    /**
     *滑动窗口(单调队列)  优化  省去内循环枚举行为 时间复杂度O(N*C)
     */
    public static int maxValue5(int N, int C, int[] s, int[] v, int[] w) {
        int[] dp = new int[C + 1];
        int[] g = new int[C + 1]; // 辅助队列，记录的是上一次的结果
        int[] q = new int[C + 1]; // 主队列，记录的是本次的结果

        // 枚举物品
        for (int i = 0; i < N; i++) {
            int vi = v[i];
            int wi = w[i];
            int si = s[i];

            // 将上次算的结果存入辅助数组中
            g = dp.clone();

            // 枚举余数 0......vi - 1
            for (int j = 0; j < vi; j++) {
                // 初始化队列，head 和 tail 分别指向队列头部和尾部
                int head = 0, tail = -1;
                // 枚举同一余数情况下，有多少种方案。
                // 例如余数为 1 的情况下有：1、vi + 1、2 * vi + 1、3 * vi + 1 ...
                for (int k = j; k <= C; k+=vi) {
                    dp[k] = g[k];
                    /**
                     * 将不在窗口范围内的值弹出
                     * k是当前背包容量  si * vi表示房钱物品的最大容量
                     * q[head] 是队头 元素 如果小于k - si * vi 说明当前位置k不会依赖于q[head]所代表的的位置
                     * 因此出队
                      */
                    if (head <= tail && q[head] < k - si * vi) head++;

                    /**
                     * 如果队列中存在元素，直接使用队头来更新
                     * q[head] 记录的是 当前窗口内的 最大价值时的容量 j + k*vi
                     * 比如当前k 是 10  q[head]是2  vi是4
                     * 则代表此时 应当使用dp[10] = Math.max(dp[10],dp[2] + (10 - 2) / 4 * w[i])
                     * 即此时选用2件重量为vi的物品 使得价值达到最大
                      */
                    if (head <= tail) dp[k] = Math.max(dp[k], g[q[head]] + (k - q[head]) / vi * wi);

                    /**
                     * 因为是窗口维护的是 最大值 使用单调递减队列
                     * 因此如果当前值比对尾值更大，队尾元素没有存在必要，队尾出队
                     *
                     * 这里需要说明的是 我们的窗口维护的是最大值结构，因此每时每刻都要保证窗口内的值是单调不增的，这样就可以
                     * 以均摊O(1)的复杂度来获取当前窗口的最大值，省去枚举行为
                     * g(k) >= g[q[tail]] + (k - q[tail]) / vi * wi;
                     * g[k] - k / vi * wi >= g[q[tail]] - q[tail] / vi * wi;
                      */
//                    while (head <= tail && g[q[tail]] - (q[tail] - j) / vi * wi <= g[k] - (k - j) / vi * wi) tail--;
                    while (head <= tail && g[q[tail]] - q[tail]  / vi * wi <= g[k] - k  / vi * wi) tail--;
                    // 将新下标入队
                    q[++tail] = k;
                }
            }
        }
        return dp[C];
    }


    public static void main(String[] args) {
        int N = 2, C = 5;
        int[] v = {1, 2};
        int[] w = {1, 2};
        int[] s = {2, 1};
        System.out.println(maxValue(N, C, s, v, w));
        System.out.println(maxValue2(N, C, s, v, w));
        System.out.println(maxValue3(N, C, s, v, w));
        System.out.println(maxValue5(N, C, s, v, w));
    }

}
