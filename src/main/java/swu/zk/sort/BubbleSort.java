package swu.zk.sort;

import swu.zk.util.ArrayUtil;
import swu.zk.util.TimeUtil;

import java.util.Arrays;

/**
 * @Classname BubbleSort
 * @Description 冒泡排序
 * @Date 2022/5/3 9:12
 * @Created by brain
 */
public class BubbleSort {

    public static void bubbleSort(int[] originArr) {
        if (originArr == null || originArr.length <= 1) return;
        doBubbleSort(originArr);
    }

    private static void doBubbleSort(int[] originArr) {
        for (int i = originArr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (originArr[j] > originArr[j + 1]) ArrayUtil.swap(originArr, j, j + 1);
            }
        }
    }

    /**
     * 加入flag标记 某些情况下可以加速
     * @param originArr
     */
    private static void doBubbleSort2(int[] originArr) {
        for (int i = originArr.length - 1; i > 0; i--) {
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (originArr[j] > originArr[j + 1]) {
                    flag = true;
                    ArrayUtil.swap(originArr, j, j + 1);
                }
            }
            if (!flag) break;
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
//                bubbleSort(arr1);
                doBubbleSort2(arr1);
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
