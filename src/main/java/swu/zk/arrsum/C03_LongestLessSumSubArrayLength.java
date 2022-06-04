package swu.zk.arrsum;

/**
 * @Classname LongestLessSumSubArrayLength
 * @Description
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 * 给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
 * 返回其长度
 * @Date 2022/6/4 16:57
 * @Created by brain
 */
public class C03_LongestLessSumSubArrayLength {

    public static int getMaxLength(int[] arr,int K){
        if (arr == null || arr.length == 0) return -1;
        int len = arr.length;
        int[] minSum = new int[len];
        int[] minSumIndex = new int[len];
        minSum[len - 1] = arr[len - 1];
        minSumIndex[len - 1] = len - 1;
        for (int i = len - 2; i >= 0; i--) {
            if (minSum[i + 1] > 0){
                minSum[i] = arr[i];
                minSumIndex[i] = i;
            }else {
                minSum[i] = minSum[i + 1] + arr[i];
                minSumIndex[i] = minSumIndex[i + 1];
            }
        }

        int end = 0;
        int sum = 0;
        int res = 0;
        for (int i = 0; i < len; i++) {
            while (end < len && sum + minSum[end] <= K){
                sum += minSum[end];
                end = minSumIndex[end] + 1;
            }
            res = Math.max(end - i,res);
            if (end > i){
                sum -= arr[i];
            }else {
                end = i + 1;
            }
        }
        return res;
    }

    /**
     * 时间复杂度O(NlogN)
     * @param arr
     * @param k
     * @return
     */
    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
//        int[] arr = {-1,2,3,0,0,1,5};
//        System.out.println(getMaxLength(arr, 6));
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (getMaxLength(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
