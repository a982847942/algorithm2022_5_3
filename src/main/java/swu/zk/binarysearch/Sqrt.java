package swu.zk.binarysearch;

import java.util.HashMap;

/**
 * @Classname Sqrt
 * @Description
 * @Date 2022/5/23 11:06
 * @Created by brain
 */
public class Sqrt {

    /**
     * 只对整数进行求平方根，且只保留整数部分
     * 1.袖珍计算器算法
     * 2.二分法
     * 3.牛顿迭代法
     *
     * @param n
     * @return
     */
    public static int sqrt1(int n) {
        if (n == 0 || n == 1) return n;
        int ans = (int) Math.exp(0.5 * Math.log(n));
        return (long) (ans + 1) * (ans + 1) <= n ? (ans + 1) : ans;
    }

    public static int sqrt2(int n) {
        if (n == 0 || n == 1) return n;
        int left = 0;
        int right = n;
        int ans = -1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (mid * mid <= n) {
                ans = mid;
                left = mid + 1;
            } else if (mid * mid > n) {
                right = mid - 1;
            }
        }
        return ans;
    }

    public static int sqrt3(int x) {
        if (x == 0) {
            return 0;
        }

        double C = x, x0 = x;
        while (true) {
            double xi = 0.5 * (x0 + C / x0);
            if (Math.abs(x0 - xi) < 1e-7) {
                break;
            }
            x0 = xi;
        }
        return (int) x0;
    }


    public static int test(int n) {
        return (int) Math.sqrt(n);
    }

    public static double s(double n, int precision) {
        double low = 0d;
        double high = n;
        double mid = (high + low) / 2.0;
        double p = Math.pow(10, -6);
        while (Math.abs(mid * mid - n) > p) {
            if (mid * mid > n) {
                high = mid;
            } else {
                low = mid;
            }
            mid = (high + low) / 2.0;
        }
        return mid;
    }


    public static void main(String[] args) {
//        int testTime = 100_0000;
//        int minValue = 0;
//        int maxValue = 10001;
//        for (int i = 0; i < testTime; i++) {
//            int temp = (int)(Math.random()*(maxValue - minValue));
//            if (sqrt3(temp) != test(temp)){
//                System.out.println("Fucking fucked!");
//                System.out.println(temp);
//            }
//        }
//        System.out.println("Nice!");
    }
}
