package swu.zk.topk;

import swu.zk.util.ArrayUtil;

/**
 * @Classname TopKByQuickSort
 * @Description
 * 快排解决topk 算法复杂度O(N)
 * @Date 2022/6/10 21:31
 * @Created by brain
 */
public class TopKByQuickSort {

    public static int topk(int[] arr,int k){
        if (arr == null || k <= 0) return -1;
        int[] newArr = ArrayUtil.copyArr(arr);
        return topKByQuickSort(newArr,0,newArr.length - 1,k - 1);
    }

    /**
     * O(N)
     * @param arr
     * @param left
     * @param right
     * @param k
     * @return
     */

    public static int topKByQuickSort(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];
        // 不止一个数  L +  [0, R -L]
        int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
        int[] range = partition(arr, left, right, pivot);
        if (k >= range[0] && k <= range[1]) {
            return arr[k];
        } else if (k < range[0]) {
            return topKByQuickSort(arr, left, range[0] - 1, k);
        } else {
            return topKByQuickSort(arr, range[1] + 1, right, k);
        }
    }

    private static int[] partition(int[] arr, int left, int right, int pivot) {
        int less = left - 1;
        int more = right + 1;
        int cur = left;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }
    private static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayUtil.generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = BFPRT.topk(arr, k);
            int ans2 = TopKByHeap.topk(arr, k);
            int ans3 = topk(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
