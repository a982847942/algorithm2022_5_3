package swu.zk.dp.misc;

/**
 * @Classname SplitSumClosed
 * @Description
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 * @Date 2022/6/19 21:40
 * @Created by brain
 */
public class SplitSumClosed {
    /**
     * 本质仍然是个背包问题
     */
    public static int getNumber(int[] arr){
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int temp : arr) {
            sum += temp;
        }
        int target = sum / 2;
        int n = arr.length;
        /**
         * 背包容量为target  物品个数为 n
         * 物品重量和价值为 arr[i]
         * dp[i][j] 表示考虑前i个物品 背包容量为j时 最大价值
         */
        int[][] dp = new int[n + 1][target + 1];
        for (int i = 1; i < n + 1; i++) {
            int temp = arr[i - 1];
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if (j - temp >= 0){
                    dp[i][j] = Math.max(dp[i][j],dp[i-1][j - temp] + temp);
                }
            }
        }
        return dp[n][target];
    }
    public static int getNumber2(int[] arr){
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        int target = sum / 2;
        int n = arr.length;
        int[] dp = new int[target + 1];
        for (int i = 0; i < n; i++) {
            int temp = arr[i];
            for (int j = target; j >= temp; j--) {
                dp[j] = Math.max(dp[j],dp[j-temp] + temp);
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] arr = {2,3,4,5,1};
        System.out.println(getNumber(arr));
        System.out.println(getNumber2(arr));
    }
}
