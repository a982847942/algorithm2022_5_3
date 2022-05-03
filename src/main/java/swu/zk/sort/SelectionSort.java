package swu.zk.sort;

import swu.zk.util.ArrayUtil;
import swu.zk.util.TimeUtil;

import java.util.Arrays;

/**
 * @Classname SelectionSort
 * @Description 选择排序
 * @Date 2022/5/3 9:12
 * @Created by brain
 */
public class SelectionSort {
    private static void selectionSort(int[] originArr){
        if (originArr == null || originArr.length <= 1)return;
        doSelectionSort(originArr);
    }

    private static void doSelectionSort(int[] originArr) {
        for (int i = 0; i < originArr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < originArr.length; j++) {
                minIndex = originArr[minIndex] > originArr[j] ? j :minIndex;
            }
            if (i != minIndex)ArrayUtil.swap(originArr,i,minIndex);
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
                selectionSort(arr1);
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
