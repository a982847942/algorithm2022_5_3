package swu.zk.dp.misc;

/**
 * @Classname PalindromeSubsequence
 * @Description 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 * @Date 2022/6/18 8:42
 * @Created by brain
 */
public class PalindromeSubsequence {
    /**
     * DFS递归
     */
    public static int palindromeSubsequence(String str) {
        if (str == null || str.length() == 0) return 0;
        char[] sc = str.toCharArray();
        return process(sc, 0, sc.length - 1);
    }

    /**
     * 考虑L...R上最长回文子串长度
     */
    private static int process(char[] sc, int L, int R) {
        if (L > R) return 0;
        if (L == R) return 1;
        char left = sc[L];
        char right = sc[R];
        int ans = 0;
        //不管left 是否 等于 right  都需要比较p1 p2 p3
        int p1 = process(sc, L + 1, R);
        int p2 = process(sc, L, R - 1);
        int p3 = process(sc, L + 1, R - 1);
        if (left == right) {
            p3 += 2;
        }
        ans = Math.max(Math.max(p1, p2), p3);
        return ans;
    }

    public static int dp(String str) {
        if (str == null || str.length() == 0) return 0;
        char[] sc = str.toCharArray();
        int n = sc.length;
        int[][] dp = new int[n][n];
        dp[n- 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = sc[i] == sc[i + 1] ? 2 : 1;
        }

        for (int L = n - 3; L >= 0; L--) {
            for (int R = L + 2; R < n; R++) {
                int p1 = dp[L + 1][R];
                int p2 = dp[L][R - 1];
                int p3 = 0;
                p3 = sc[L] == sc[R] ? 2 + dp[L + 1][R - 1] : dp[L + 1][R - 1];
                dp[L][R] = Math.max(Math.max(p1,p2),p3);
            }
        }
        return dp[0][n-1];
    }


    public static void main(String[] args) {
        System.out.println(palindromeSubsequence("babadada"));
        System.out.println(dp("babadada"));
    }
}
