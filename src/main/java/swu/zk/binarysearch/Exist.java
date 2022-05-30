package swu.zk.binarysearch;

import java.util.Arrays;

/**
 * @Classname Exist
 * @Description 在一个有序数组中，找某个数是否存在
 * @Date 2022/5/22 15:58
 * @Created by brain
 */
public class Exist {

    /**
     * 二分法的非递归
     * @param arr
     * @param value
     * @return
     */
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

    /**
     * 二分法的递归实现
     * @param arr
     * @param value
     * @return
     */
    public static boolean isExist2(int[] arr, int value) {
        if (arr == null || arr.length == 0) return false;
        return doExist2(arr,0,arr.length - 1,value);
    }

    private static boolean doExist2(int[] arr, int left, int right, int value) {
        if (left > right) return false;
        int mid = left + ((right - left) >> 1);
        if (arr[mid] == value){
            return true;
        }else if (arr[mid] > value){
            return doExist2(arr,left,mid - 1,value);
        }else {
            return doExist2(arr,mid + 1,right,value);
        }
    }

    /**
     * 有序数组是一个循环有序数组，比如 4，5，6，1，2，3。
     * 如何实现一个求“值等于给定值”的二分查找算法呢？
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        int mid = left + (right-left)/2;

        while(left <= right){
            if(nums[mid] == target){
                return mid;
            }

            if(nums[left] <= nums[mid]){  //左边升序
                if(target >= nums[left] && target <= nums[mid]){//在左边范围内
                    right = mid-1;
                }else{//只能从右边找
                    left = mid+1;
                }

            }else{ //右边升序
                if(target >= nums[mid] && target <= nums[right]){//在右边范围内
                    left = mid +1;
                }else{//只能从左边找
                    right = mid-1;
                }

            }
            mid = left + (right-left)/2;
        }

        return -1;  //没找到
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
            if (test(arr, value) != isExist2(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
