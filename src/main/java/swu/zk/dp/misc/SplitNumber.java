package swu.zk.dp.misc;

/**
 * @Classname SplitNumber
 * @Description
 * 给定一个正数n，求n的裂开方法数，
 * 规定：后面的数不能比前面的数小
 * 比如4的裂开方法有：
 * 1+1+1+1、1+1+2、1+3、2+2、4
 * 5种，所以返回5
 * @Date 2022/6/19 20:01
 * @Created by brain
 */
public class SplitNumber {
    public static int getWays(int n){
        if (n <= 0) return 0;
        if (n == 1) return 1;
        return process(1,n );
    }

    private static int process(int pre, int rest) {
        if (rest == 0) return 1;
        if (pre > rest) return 0;
        int ways = 0;
        for (int i = pre; i <= rest; i++) {
            ways += process(i,rest - i);
        }
        return ways;
    }

//    public static int dp(int n){
//        if (n <= 0) return 0;
//        if (n == 1) return 1;
//        int[]
//    }

    public static void main(String[] args) {
        System.out.println(getWays(4));
    }
}
