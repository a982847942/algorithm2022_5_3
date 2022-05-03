package swu.zk.sort;

import swu.zk.util.ArrayUtil;
import swu.zk.util.TimeUtil;

import java.util.Arrays;

/**
 * @Classname InsertionSort
 * @Description 插入排序
 * @Date 2022/5/3 9:12
 * @Created by brain
 */
public class InsertionSort {

    private static void insertionSort(int[] originArr) {
        if (originArr == null || originArr.length <= 1) return;
        doInsertionArr(originArr);
    }

    private static void doInsertionArr(int[] originArr) {
        for (int i = 1; i < originArr.length; i++) {
            for (int j = i; j > 0 && originArr[j] < originArr[j - 1]; j--) {
                //注意这里将比较放在循环内部 和 循环结束条件中有什么区别？？？
//                if (originArr[j] < originArr[j - 1]) {
//                    ArrayUtil.swap(originArr, j - 1, j);
//                }
                ArrayUtil.swap(originArr,j - 1,j);
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
                insertionSort(arr1);
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
