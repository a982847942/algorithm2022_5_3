package swu.zk.sort;

import swu.zk.util.ArrayUtil;
import swu.zk.util.TimeUtil;

import java.util.Arrays;

/**
 * @Classname MergeSort
 * @Description 归并排序
 * @Date 2022/5/3 9:12
 * @Created by brain
 */
public class MergeSort {
    /**
     * 递归版本
     * @param arr
     */
    private static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        doMergeSort(arr, 0, arr.length - 1);
    }

    private static void doMergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + ((right - left) >> 1);
        doMergeSort(arr, left, mid);
        doMergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int curIndex = 0;
        int index1 = left;
        int index2 = mid + 1;
        while (index1 <= mid && index2 <= right) {
            help[curIndex++] = arr[index1] > arr[index2] ? arr[index2++] : arr[index1++];
        }
        while (index1 <= mid) help[curIndex++] = arr[index1++];
        while (index2 <= right) help[curIndex++] = arr[index2++];
        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
    }


    /**
     * 非递归版本
     * @param originArr
     */
    private static void mergeSort2(int[] originArr){
        if (originArr == null || originArr.length < 2) return;
        int length = originArr.length;
        int mergeSize = 1;

        while (mergeSize < length){
            int left = 0;
            while (left + mergeSize < length){
                int mid = left + mergeSize - 1;
                int right = Math.min(mid + mergeSize,length - 1);
                merge(originArr,left,mid,right);
                left = right + 1;
            }
            if (mergeSize > length / 2) break;
            mergeSize <<= 1;
        }
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxValue = 100;
        int maxSize = 100;
        TimeUtil.getTime(()->{
            boolean success = true;
            for (int i = 0; i < testTimes; i++) {
                int[] arr1 = ArrayUtil.generateRandomArray(maxSize, maxValue);
                int[] arr2 = ArrayUtil.copyArr(arr1);
//                mergeSort(arr1);
                mergeSort2(arr1);
                Arrays.sort(arr2);
                if (!ArrayUtil.isEqual(arr1, arr2)){
                    success = false;
                    ArrayUtil.printArray(arr1);
                    ArrayUtil.printArray(arr2);
                    break;
                }
            }
            System.out.println(success ? "Nice!" : "Fucking fucked!");
        });

    }

}
