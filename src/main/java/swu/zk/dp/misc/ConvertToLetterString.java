package swu.zk.dp.misc;

/**
 * @Classname ConvertToLetterString
 * @Description
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 * @Date 2022/6/17 22:23
 * @Created by brain
 */
public class ConvertToLetterString {
    public static int number(String str) {
        if (str == null || str.length() == 0) return 0;
        char[] sc = str.toCharArray();
        return process(sc,0);
    }

    private static int process(char[] str, int cur) {
        if (cur == str.length) return 1;
        if (str[cur] == '0') return 0;
        int temp = str[cur] - '0';
        int ans = process(str,cur + 1);
        if (temp == 1 || (temp == 2 && cur+ 1 < str.length && (str[cur + 1] - '0' <= 6)) ){
            ans += process(str,cur + 2);
        }
        return ans;
    }
    public static int dp(String str) {
        int n = str.length();
        int[] dp = new int[n + 1];
        //考虑 当cur来到 str.length时表示 字符串已经匹配完毕 能够顺利匹配 因此初始化为1
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (str.charAt(i) != '0'){
                int temp = str.charAt(i) - '0';
                dp[i] = dp[i + 1];
                if ( temp == 1 || (temp == 2 && i + 1 < n && (str.charAt(i + 1) - '0' <= 6)) ){
                    dp[i] += dp[i+2];
                }
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(number("7210231231232031203123"));
        System.out.println(dp("7210231231232031203123"));
    }
}
