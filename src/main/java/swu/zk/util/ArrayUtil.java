package swu.zk.util;

import java.util.Arrays;

/**
 * @Classname ArrayUtil
 * @Description Array的一个简单工具类
 * @Date 2022/5/3 10:04
 * @Created by brain
 */
public class ArrayUtil {
    /**
     * 复制一个数组
     * @param originArr
     * @return
     */
    public static int[] copyArr(int[] originArr) {
        if (originArr == null || originArr.length < 1) return originArr;
        int[] result = Arrays.copyOf(originArr, originArr.length);
        return result;
    }

    /**
     * 比较两个数组元素是否一一对应
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) return false;
        if (arr1 == null && arr2 == null) return true;
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    /**
     * 打印数组
     * @param arr
     */
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 生成一个数组 数组的长度为maxSize 数组的最大元素为maxValue
     * @param maxSize
     * @param maxValue
     * @return
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        if (maxSize <= 0) throw new RuntimeException("The array length must be greater than 0");
        int[] result = new int[maxSize];

        for (int i = 0; i < maxSize; i++) {
            result[i] = (int) ((maxValue + 1) * Math.random() - maxValue * Math.random());
        }
        return result;
    }

    /**
     * 交换数组中的两个元素的位置
     * @param originArr
     * @param highIndex
     * @param lowIndex
     */
    public static void swap(int[] originArr, int highIndex, int lowIndex) {
        int temp = originArr[highIndex];
        originArr[highIndex] = originArr[lowIndex];
        originArr[lowIndex] = temp;
    }
}
