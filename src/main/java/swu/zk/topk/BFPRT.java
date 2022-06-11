package swu.zk.topk;

import swu.zk.util.ArrayUtil;

/**
 * @Classname bfprt
 * @Description
 * bfprt算法
 * @Date 2022/6/10 21:30
 * @Created by brain
 */
public class BFPRT {

    public static int topk(int[] arr,int k){
        if (arr == null || k <= 0) return -1;
        int[] copyArray = ArrayUtil.copyArr(arr);
        return bfprt(copyArray,0,copyArray.length - 1,k - 1);
    }

    /**
     * O(N)
     * 数组arr在left到right范围内返回排序后位于index处的值
     * 1.将 n 个元素划为 [n/5] 组，每组5个，至多只有一组由 n mod 5 个元素组成。
     * 2.寻找这[n/5] 个组中每一个组的中位数，这个过程可以用插入排序。
     * 3.对步骤2中的 [n/5]个中位数，重复步骤1和步骤2，递归下去，直到剩下一个数字。
     * 4.最终剩下的数字即为pivot，把大于它的数全放左边，小于等于它的数全放右边。
     * 5.判断pivot的位置与k的大小，有选择的对左边或右边递归。
     * @param arr
     * @param left
     * @param right
     * @param index
     * @return
     */
    public static int bfprt(int[] arr,int left,int right,int index){
        if (left == right) return arr[left];
        /**
         *   bfprt与快排区别就在此处，对于pivot而言 bfprt是精挑细选 保证了最差是7/10的数据量
         *   避免了快排的线性退化
         *
         */

        int pivot = medianOfMedians(arr,left,right);
        int[] range = partition(arr,left,right,pivot);
        if (index >= range[0] && index <= range[1]){
            return arr[index];
        }else if (index < range[0]){
            return bfprt(arr,left,range[0] - 1,index);
        }else {
            return bfprt(arr,range[1] + 1,right,index);
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int size = right - left + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int mArr[] = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = left + team * 5;
            mArr[team] = getMedian(arr,teamFirst,Math.min(right,teamFirst + 4));
        }
        return bfprt(mArr,0,mArr.length - 1,(mArr.length - 1) / 2);
    }



    private static int getMedian(int[] arr, int left, int right) {
        insertionSort(arr,left,right);
        return arr[(left + right) / 2];
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right ; i++) {
            for (int j = i - 1; j >= left && arr[j] > arr[j + 1] ; j--) {
                swap(arr,j,j+1);
            }
        }
    }


    private static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
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


    public static void main(String[] args) {
        int arr[] = {5,6,8,4,9,52,3,64,8,1};
        System.out.println(topk(arr,3));
    }
}
