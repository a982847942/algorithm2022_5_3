package swu.zk.dp.path;

/**
 * @Classname MinFallingPathSum2
 * @Description
 * 这是 LeetCode 上的「1289. 下降路径最小和  II」，难度为 Hard。
 * 给你一个整数方阵 arr ，定义「非零偏移下降路径」为：从 arr 数组中的每一行选择一个数字，
 * 且按顺序选出来的数字中，相邻数字不在原数组的同一列。
 * 请你返回非零偏移下降路径数字和的最小值。
 * 输入：arr = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：13
 * 解释：
 * 所有非零偏移下降路径包括：
 * [1,5,9], [1,5,7], [1,6,7], [1,6,8],
 * [2,4,8], [2,4,9], [2,6,7], [2,6,8],
 * [3,4,8], [3,4,9], [3,5,7], [3,5,9]
 * 下降路径中数字和最小的是 [1,5,7] ，所以答案是 13 。
 * @Date 2022/6/14 10:14
 * @Created by brain
 */
public class MinFallingPathSum2 {

    /**
     * dp[i][j] 表示 到i,j的最小路径代价
     * 时间复杂度O(n3)
     * @param arr
     * @return
     */
    public static int minFallingPathSum(int[][] arr){
        int n = arr.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = arr[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                int temp = arr[i][j];

                /**
                 * i,j位置根据上一行中除了同列外的所有数据
                 */
                for (int k = 0; k < n; k++) {
                    if (k != j){
                        dp[i][j] = Math.min(dp[i-1][k] + temp,dp[i][j]);
                    }
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans,dp[n-1][i]);
        }
        return ans;
    }

    /**
     * 仔细考虑一下会发现  我们在求下一行的信息是只需要上一行的两个值、
     * 即最小值 和 次小值(为了正确求出跟最小值同一列的信息)
     * 因此我们在求解每个位置的具体信息时可以对其进行简化，以此来降低复杂度
     * 此方法时间复杂度为O(n2)
     * @param arr
     * @return
     */
    public static int minFallingPathSum2(int[][] arr){
        int n = arr.length;
        int[][] dp = new int[n][n];
        int minIndex1 = -1;
        int minIndex2 = -1;
        for (int i = 0; i < n; i++) {
            int temp = arr[0][i];
            dp[0][i] = temp;
            if (temp < (minIndex1 == -1 ? Integer.MAX_VALUE : dp[0][minIndex1])){
                minIndex2 = minIndex1;
                minIndex1 = i;
            }else if (temp < (minIndex2 == -1 ? Integer.MAX_VALUE : dp[0][minIndex2])){
                minIndex2 = i;
            }
        }

        for (int i = 1; i < n; i++) {
            int tempIndex1 = -1;
            int tempIndex2 = -1;
            for (int j = 0; j < n; j++) {
                int temp = arr[i][j];

                if (j != minIndex1){
                    dp[i][j] = dp[i-1][minIndex1] + temp;
                }else {
                    dp[i][j] = dp[i-1][minIndex2] + temp;
                }

                if (dp[i][j] < (tempIndex1 == -1 ? Integer.MAX_VALUE : dp[i][tempIndex1])){
                    tempIndex2 = tempIndex1;
                    tempIndex1 = j;
                } else if (dp[i][j] < (tempIndex2 == -1 ? Integer.MAX_VALUE : dp[i][tempIndex2])) {
                    tempIndex2 = j;
                }
            }
            minIndex1 = tempIndex1;
            minIndex2 = tempIndex2;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans,dp[n-1][i]);
        }
        return ans;
    }


    public static void main(String[] args) {

//        int[][] arr = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] arr = {{-73,61,43,-48,-36},{3,30,27,57,10},{96,-76,84,59,-15},{5,-49,76,31,-7},{97,91,61,-46,67}};
        System.out.println(minFallingPathSum(arr));
        System.out.println(minFallingPathSum2(arr));
    }
}
