package swu.zk.binarysearch;

import java.util.Arrays;

/**
 * @Classname RightEqualIndex
 * @Description 返回给定值在有序数组中最有一次出现的下标
 * @Date 2022/5/22 23:51
 * @Created by brain
 */
public class RightEqualIndex {
    public static int rightEqualIndex(int[] arr, int value) {
        if (arr == null || arr.length == 0) return -1;
        int left = 0;
        int len = arr.length - 1;
        int right = len;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > value) {
                right = mid - 1;
            } else if (arr[mid] < value) {
                left = mid + 1;
            } else {
                if (mid == len || arr[mid + 1] != value) return mid;
                left = mid + 1;
            }
        }
        return -1;
    }

    // for test
    public static int test(int[] sortedArr, int num) {
        int len = sortedArr.length - 1;
        for (int i = len; i >= 0 ; i--) {
            if (sortedArr[i] == num) return i;
        }
        return -1;
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != rightEqualIndex(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
