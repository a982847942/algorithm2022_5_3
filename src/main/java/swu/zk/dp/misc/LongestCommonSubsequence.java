package swu.zk.dp.misc;

/**
 * @Classname LongestCommonSubsequence
 * @Description
 *
 * @Date 2022/6/17 22:03
 * @Created by brain
 */
public class LongestCommonSubsequence {
    public static int longestCommonSubsequence(String str1,String str2){
        if (str1.length() == 0 || str2.length() == 0)return 0;
        int n1 = str1.length();
        int n2 = str2.length();
        int[][] dp = new int[n1][n2];
        dp[0][0] = str1.charAt(0) == str2.charAt(0) ? 1 : 0;
        for (int i = 1; i < n2; i++) {
            dp[0][i] = str1.charAt(0) == str2.charAt(i) ? 1 : dp[0][i-1];
        }
        for (int i = 1; i < n1; i++) {
            dp[i][0] = str1.charAt(i) == str2.charAt(0) ? 1 : dp[i-1][0];
        }
        for (int i = 1; i < n1; i++) {
            char temp = str1.charAt(i);
            for (int j = 1; j < n2; j++) {
                if (temp == str2.charAt(j)){
                    dp[i][j] = dp[i-1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j],dp[i][j - 1]);
                }
            }
        }
        return dp[n1-1][n2-1];
    }

    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("abcdef", "adf"));
    }
}
