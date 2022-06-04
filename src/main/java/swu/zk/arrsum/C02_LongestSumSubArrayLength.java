package swu.zk.arrsum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LongestSumSubArrayLength
 * @Description
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 * 给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度
 * @Date 2022/6/4 15:11
 * @Created by brain
 */
public class C02_LongestSumSubArrayLength {

    public static int getMaxLength(int[] arr,int K){
        if (arr == null || arr.length == 0)return -1;
        Map<Integer,Integer> prefixSumIndex = new HashMap<>();
        prefixSumIndex.put(0,-1);
        int result = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
             sum += arr[i];
            if (prefixSumIndex.containsKey(sum - K)){
                result = Math.max(result,i- prefixSumIndex.get(sum - K));
            }
            if (!prefixSumIndex.containsKey(sum)){
                prefixSumIndex.put(sum,i);
            }
        }
        return result;
    }


    /**
     * for test
     *
     * @param arr
     * @param K
     * @return
     */
    public static int right(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) return -1;
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (isValid(arr, i, j) == K) {
                    res = Math.max(res, j - i + 1);
                }
            }
        }
        return res;
    }

    private static int isValid(int[] arr, int i, int j) {
        int res = 0;
        for (int k = i; k <= j; k++) {
            res += arr[k];
        }
        return res;
    }

    // for test
    public static int[] generatePositiveArray(int size, int value) {
        int[] ans = new int[size];
        for (int i = 0; i != size; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
//        int[] arr = {-1,2,4,0,0,1,3,6};
//        System.out.println(getMaxLength(arr, 0));

        int testTime = 1_0000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generatePositiveArray(maxSize, maxValue);
            int K = (int) (Math.random() * maxValue) + 1;
            int res1 = getMaxLength(arr, K);
            int res2 = right(arr, K);
            if (res1 != res2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(res1);
                System.out.println(res2);
                break;
            }
        }
        System.out.println("test end");
    }
}
