package swu.zk.sort;

import swu.zk.util.ArrayUtil;
import swu.zk.util.TimeUtil;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Classname QuickSort
 * @Description 快速排序  快排与冒泡的区别
 * @Date 2022/5/3 9:13
 * @Created by brain
 */
public class QuickSort {

    private static void quickSort2(int[] originArr){
        if (originArr == null || originArr.length < 2) return;
        doQucikSort2(originArr,0,originArr.length - 1);
    }

    private static void doQucikSort2(int[] originArr,int left,int right) {
        if (left >= right) return;
        ArrayUtil.swap(originArr,left + (int) ((right - left)*Math.random()),right);
        int[] equalArea = netherlandsFlag(originArr,left,right);
        doQucikSort2(originArr,left,equalArea[0] - 1);
        doQucikSort2(originArr,equalArea[1] + 1,right);
    }

    /**
     * 荷兰国旗问题
     * @param originArr
     * @param left
     * @param right
     * @return
     */
    private static int[] netherlandsFlag(int[] originArr, int left, int right) {
        if (left > right)return new int[]{-1,-1};
        if (left == right) return new int[]{left,left};

        int lowIndex = left - 1;
        int highIndex = right;
        int curIndex = left;
        int temp = originArr[right];
        while (curIndex < highIndex){
            if (originArr[curIndex] < temp){
                ArrayUtil.swap(originArr,++lowIndex,curIndex++);
            }else if (originArr[curIndex] > temp){
               ArrayUtil.swap(originArr,curIndex,--highIndex);
            }else {
                curIndex++;
            }
        }
        ArrayUtil.swap(originArr,highIndex,right);
        return new int[]{lowIndex + 1,highIndex};
    }


    /**
     * 这种划分方法 得到的结果是 左边<= pivot  右边 >= pivot 对于有重复值的数据而言，存在重复行为。
     * 借用荷兰国旗问题的方法，一次划分一个区间。然后再加上随机化操作，合理选取初始值。
     * @param originArr
     */
    private static void quickSort(int[] originArr){
        if (originArr == null || originArr.length < 2) return;
        doQucikSort(originArr,0,originArr.length - 1);
    }

    private static void doQucikSort(int[] originArr, int left, int right) {
        if (left >= right) return;
        int pivot = originArr[left];
        int lessIndex = left;
        int moreIndex = right;
        int curIndex = left;
        while (lessIndex != moreIndex){
            while (originArr[moreIndex] >= pivot && lessIndex < moreIndex) moreIndex--;
            while (originArr[lessIndex] <= pivot && lessIndex < moreIndex) lessIndex++;
            ArrayUtil.swap(originArr,lessIndex,moreIndex);
        }
        originArr[left] = originArr[lessIndex];
        originArr[lessIndex] = pivot;
        doQucikSort2(originArr,left,lessIndex - 1);
        doQucikSort2(originArr,lessIndex + 1,right);
    }


    /**
     * 快速排序的非递归版本
     */
    public static class Op {
        public int l;
        public int r;

        public Op(int left, int right) {
            l = left;
            r = right;
        }
    }


    // 快排3.0 非递归版本
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        ArrayUtil.swap(arr, (int) (Math.random() * N), N - 1);
        int[] equalArea = netherlandsFlag(arr, 0, N - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, el - 1));
        stack.push(new Op(er + 1, N - 1));
        while (!stack.isEmpty()) {
            Op op = stack.pop(); // op.l  ... op.r
            if (op.l < op.r) {
                ArrayUtil.swap(arr, op.l + (int) (Math.random() * (op.r - op.l + 1)), op.r);
                equalArea = netherlandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(op.l, el - 1));
                stack.push(new Op(er + 1, op.r));
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
                quickSort3(arr1);
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
