package swu.zk.sort;

import swu.zk.util.ArrayUtil;
import swu.zk.util.TimeUtil;

import java.util.Arrays;

/**
 * @Classname CountSort
 * @Description 计数排序
 * @Date 2022/5/3 9:14
 * @Created by brain
 */
public class CountSort {

    /**
     * 只能对非负整数进行排序
     * @param originArr
     */
    private static void countSort(int[] originArr){
        if (originArr == null || originArr.length < 2) return;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < originArr.length; i++) {
            maxValue = Math.max(maxValue,originArr[i]);
        }
        int[] bucket = new int[maxValue + 1];
        for (int i = 0; i < originArr.length; i++) {
            bucket[originArr[i]]++;
        }

        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0){
                originArr[index++] = i;
            }
        }
    }

    /**
     * 可对负整数进行排序
     * @param originArr
     */
    private static void countSort2(int[] originArr){
        if (originArr == null || originArr.length < 2) return;
        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < originArr.length; i++) {
            maxValue = Math.max(maxValue,originArr[i]);
            minValue = Math.min(minValue,originArr[i]);
        }


        int[] bucket = new int[maxValue - minValue + 1];
        for (int i = 0; i < originArr.length; i++) {
            bucket[originArr[i] - minValue]++;
        }

        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0){
                originArr[index++] = i + minValue;
            }
        }
    }

    public static void main(String[] args) {
        int testTimes = 50000;
        int maxValue = 100;
        int maxSize = 100;
        TimeUtil.getTime(()->{
            boolean success = true;
            for (int i = 0; i < testTimes; i++) {
                int[] arr1 = ArrayUtil.generateRandomArray(maxSize, maxValue);
                int[] arr2 = ArrayUtil.copyArr(arr1);
                countSort2(arr1);
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
