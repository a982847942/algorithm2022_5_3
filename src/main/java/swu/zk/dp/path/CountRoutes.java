package swu.zk.dp.path;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * @Classname CountRoutes
 * @Description 这是 LeetCode 上的「1575. 统计所有可行路径」，难度为 Hard。
 * 给你一个 互不相同 的整数数组，其中 locations[i]表示第 i个城市的位置。
 * 同时给你 start，finish 和  fuel分别表示出发城市、目的地城市 和 你初始拥有的汽油总量。
 * 每一步中，如果你在城市 i，你可以选择任意一个城市 j，满足 j != i 且0 <= j < locations.length ，并移动到城市 j。
 * 从城市 i移动到 j消耗的汽油量为|locations[i] - locations[j]| ， |x|表示 x的绝对值。
 * 请注意， fuel任何时刻都不能为负，且你可以经过任意城市超过一次（包括 start和 finish）。
 * 请你返回从 start 到 finish 所有可能路径的数目。
 * 由于答案可能很大，请将它对 10的9次方 + 7 取余后返回。
 * @Date 2022/6/14 16:50
 * @Created by brain
 */
public class CountRoutes {
    static int mod = 1000000007;
    static int[][] memory;

    /**
     * DFS方法 递归调用
     * 使用了缓存 memory 时间复杂度为O(n*n*fuel)
     * @return
     */
    public static int countRoutes(int[] locations, int start, int end, int fuel) {
        int n = locations.length;
        int ans = 0;
        memory = new int[n][fuel + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memory[i], -1);
        }
        for (int i = 0; i < n; i++) {
            if (i != start) {
                ans += process(locations, i, end, fuel - Math.abs(locations[i] - locations[start]));
                ans %= mod;
            }
        }
        return start == end ? (ans + 1) % mod : ans;
    }

    /**
     * @param locations
     * @param cur  当前所在位置
     * @param end  目标位置
     * @param fuel  剩余油量
     * @return
     */
    private static int process(int[] locations, int cur, int end, int fuel) {
        if (fuel > 0) {
            if (memory[cur][fuel] != -1) return memory[cur][fuel];
            int ans = 0;
            for (int i = 0; i < locations.length; i++) {
                if (i != cur) {
                    ans += process(locations, i, end, fuel - Math.abs(locations[i] - locations[cur])) ;
                    ans %= mod;
                }
            }
            ans = cur == end ? ans + 1 : ans;
            ans %= mod;
            memory[cur][fuel] = ans;
            return ans;
        } else if (fuel == 0) {
            int ans = cur == end ? 1 : 0;
            memory[cur][fuel] = ans;
            return ans;
        } else {
            return 0;
        }
    }


    /**
     * 动态规划
     * dp[i][j] 表示从当前位置为i 油量为j 到达位置end有多少种方法
     * @param locations
     * @param start
     * @param end
     * @param fuel
     * @return
     */
    public static int countRoutes2(int[] locations, int start, int end, int fuel) {
        int n = locations.length;
        int[][] dp = new int[n][fuel + 1];
        for (int i = 0; i <= fuel; i++) {
            dp[end][i] = 1;
        }

        /**
         * dp[j][i] += dp[k][i - need];
         * 这里j 和 k没有依赖关系 可以取任意城市
         * 但是fuel需要依赖于fuel - need 因此按照fuel从小到大来递推
         */
        for (int i = 0; i <= fuel; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (j != k){
                        int need = Math.abs(locations[j] - locations[k]);
                        if (i >= need){
                            dp[j][i] += dp[k][i - need];
                            dp[j][i] %= mod;
                        }
                    }
                }
            }
        }
        return dp[start][fuel];
    }


    public static void main(String[] args) {
        int[] locations = {1,2,3};
        int start = 0, finish = 2, fuel = 40;
        System.out.println(countRoutes(locations, start, finish, fuel));
        System.out.println(countRoutes2(locations, start, finish, fuel));
    }
}
