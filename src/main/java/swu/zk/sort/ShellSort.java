package swu.zk.sort;

import swu.zk.util.ArrayUtil;

import java.util.Arrays;

/**
 * @Classname ShellSort
 * @Description 希尔排序
 * @Date 2022/5/3 9:15
 * @Created by brain
 */
public class ShellSort {


    private static void shellSort(int[] originArr){
        if (originArr == null || originArr.length < 2) return;
        doShellSort(originArr);
    }

    private static void doShellSort(int[] originArr) {
        int count = 0;
        //增量序列有不同划分方法  ，这里采用希尔推荐的方法，事实上这并不是最好的划分
        for (int increment = originArr.length / 2; increment > 0 ; increment /= 2) {
            for (int i = increment; i < originArr.length; i++) {
                int temp = originArr[i];
                int preIndex = i - increment;
                while (preIndex >= 0 && originArr[preIndex] > temp){
                    ArrayUtil.swap(originArr,preIndex,preIndex + increment);
                    preIndex -= increment;
                }
            }
            count++;
            System.out.println("第" + count + "次排序后的结果:" + Arrays.toString(originArr));
        }
    }

    public static void main(String[] args) {
        int[] test = {5,9,8,4,7,1,2,6};
        shellSort(test);
        ArrayUtil.printArray(test);
    }

}
