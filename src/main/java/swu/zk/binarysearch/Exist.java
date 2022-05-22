package swu.zk.binarysearch;

import java.util.Arrays;

/**
 * @Classname Exist
 * @Description 在一个有序数组中，找某个数是否存在
 * @Date 2022/5/22 15:58
 * @Created by brain
 */
public class Exist {

    public static boolean isExist(int[] arr, int value) {
        if (arr == null || arr.length == 0) return false;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > value){
                right = mid - 1;
            }else if (arr[mid] < value){
                left = mid + 1;
            }else {
                return true;
            }
        }
        return false;
    }

    // for test
    public static boolean test(int[] sortedArr, int num) {
        for(int cur : sortedArr) {
            if(cur == num) {
                return true;
            }
        }
        return false;
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
            if (test(arr, value) != isExist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
