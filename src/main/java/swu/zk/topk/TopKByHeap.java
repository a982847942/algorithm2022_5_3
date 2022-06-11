package swu.zk.topk;

import java.util.PriorityQueue;

/**
 * @Classname TopKByHeap
 * @Description
 * 使用堆解决topk问题
 * @Date 2022/6/10 21:32
 * @Created by brain
 */
public class TopKByHeap {

    public static int topk(int[] arr,int k){
        if (arr == null || k <= 0) return -1;
        return topkByHeap(arr,k);
    }

    /**
     * O(NlogK)
     * 使用自己的heapfiy从下往上建立对 复杂度可以达到O(N + KLogN)
     * @param arr
     * @param k
     * @return
     */
    private static int topkByHeap(int[] arr, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1,o2)->{
            return o2 - o1;
        });
        for (int i = 0; i < k; i++) {
            heap.add(arr[i]);
        }
        for (int i = k; i < arr.length ; i++) {
            if (arr[i] < heap.peek()){
                heap.poll();
                heap.add(arr[i]);
            }
        }
        return heap.peek();
    }

    public static void main(String[] args) {
        int arr[] = {5,6,8,4,9,52,3,64,8,1};
        System.out.println(topk(arr,3));
    }
}
