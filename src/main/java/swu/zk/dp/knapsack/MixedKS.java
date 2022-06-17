package swu.zk.dp.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MixedKS
 * @Description
 * 混合背包
 * 给定物品数量 N 和背包容量 C 。第 i 件物品的体积是v[i] ，价值是 w[i]，可用数量为 s[i] ：
 * 当  s[i]为  -代表是该物品只能用一次
 * 当  s[i]为 0 代表该物品可以使用无限次
 * 当 s[i] 为任意正整数则代表可用 s[i] 次
 * 求解将哪些物品装入背包可使这些物品的费用总和不超过背包容量，且价值总和最大。
 * @Date 2022/6/17 17:46
 * @Created by brain
 */
public class MixedKS {
    public int maxValue(int N, int C, int[] w, int[] v, int[] s) {
        // 构造出物品的「价值」和「体积」列表
        List<Integer> worth = new ArrayList<>();
        List<Integer> volume = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int type = s[i];

            // 多重背包：应用「二进制优化」转换为 0-1 背包问题
            if (type > 0) {
                for (int k = 1; k <= type; k *= 2) {
                    type -= k;
                    worth.add(w[i] * k);
                    volume.add(v[i] * k);
                }
                if (type > 0) {
                    worth.add(w[i] * type);
                    volume.add(v[i] * type);
                }

                // 01 背包：直接添加
            } else if (type == -1) {
                worth.add(w[i]);
                volume.add(v[i]);

                // 完全背包：对 worth 做翻转进行标记
            } else {
                worth.add(-w[i]);
                volume.add(v[i]);
            }
        }

        // 使用「一维空间优化」方式求解三种背包问题
        int[] dp = new int[C + 1];
        for (int i = 0; i < worth.size(); i++) {
            int wor = worth.get(i);
            int vol = volume.get(i);

            // 完全背包：容量「从小到大」进行遍历
            if (wor < 0) {
                for (int j = vol; j <= C; j++) {
                    // 同时记得将 worth 重新翻转为正整数
                    dp[j] = Math.max(dp[j], dp[j - vol] - wor);
                }

                // 01 背包：包括「原本的 01 背包」和「经过二进制优化的完全背包」
                // 容量「从大到小」进行遍历
            } else {
                for (int j = C; j >= vol; j--) {
                    dp[j] = Math.max(dp[j], dp[j - vol] + wor);
                }
            }
        }
        return dp[C];
    }
}
