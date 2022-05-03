package swu.zk.sort;

import swu.zk.util.ArrayUtil;

import java.util.Arrays;

/**
 * @Classname HeapSort
 * @Description 堆排序
 * @Date 2022/5/3 9:13
 * @Created by brain
 */
public class HeapSort {

    static class Heap {
        int[] heap;
        int heapSize;
        int limit;

        public Heap(int limit) {
            this.limit = limit;
            heap = new int[limit];
        }

        public void push(int value) {
            if (heapSize == limit) throw new RuntimeException("heap is full");
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        public void heapInsert(int[] heap, int heapIndex) {
            int parentIndex = (heapIndex - 1) >> 1;
            while (parentIndex >= 0 && heap[heapIndex] > heap[parentIndex]) {
                ArrayUtil.swap(heap, parentIndex, heapIndex);
                heapIndex = parentIndex;
                parentIndex = (parentIndex - 1) >> 1;
            }
        }

        public int pop(){
            if (heapSize == 0) throw new RuntimeException("heap is empty");
            int result = heap[0];
            ArrayUtil.swap(heap,0,--heapSize);
            heapfiy(heap,0,heapSize);
            return result;
        }

        public void heapfiy(int[] heap, int index, int heapSize) {
            int leftIndex = (index << 1) + 1;
            while (leftIndex < heapSize ){
                int moreIndex = (leftIndex +1 < heapSize) && heap[leftIndex] < heap[leftIndex + 1] ? leftIndex + 1 : leftIndex;
                moreIndex = heap[index] < heap[moreIndex] ? moreIndex : index;
                if (moreIndex == index) return;
                ArrayUtil.swap(heap,moreIndex,index);
                index = moreIndex;
                leftIndex = (index << 1) + 1;
            }
        }

        public boolean isEmpty(){
            return heapSize == 0;
        }
        public boolean isFull(){
            return heapSize == limit;
        }
    }


    /**
     * 遍历实现的堆 与上面的堆比较
     */
    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }


    private static void heapSort(int[] originArr) {
        if (originArr == null || originArr.length < 2) return;
        //n*log(n)
        for (int i = 0; i < originArr.length; i++) {
            heapInsert(originArr,i);
        }
        int heapSize = originArr.length;
        ArrayUtil.swap(originArr,0,--heapSize);
        //n*log(n)
        while (heapSize > 0){
            heapfiy(originArr,0,heapSize);
            ArrayUtil.swap(originArr,0,--heapSize);
        }
    }

    private static void heapSort2(int[] originArr) {
        if (originArr == null || originArr.length < 2) return;
        for (int i = originArr.length - 1; i >= 0 ; i--) {
            heapfiy(originArr,i,originArr.length);
        }
        int heapSize = originArr.length;
        ArrayUtil.swap(originArr,0,--heapSize);
        //n*log(n)
        while (heapSize > 0){
            heapfiy(originArr,0,heapSize);
            ArrayUtil.swap(originArr,0,--heapSize);
        }
    }

    public static void heapInsert(int[] heap, int heapIndex) {
        int parentIndex = (heapIndex - 1) >> 1;
        while (parentIndex >= 0 && heap[heapIndex] > heap[parentIndex]) {
            ArrayUtil.swap(heap, parentIndex, heapIndex);
            heapIndex = parentIndex;
            parentIndex = (parentIndex - 1) >> 1;
        }
    }

    public static void heapfiy(int[] heap, int index, int heapSize) {
        int leftIndex = (index << 1) + 1;
        while (leftIndex < heapSize ){
            int moreIndex = (leftIndex +1 < heapSize) && heap[leftIndex] < heap[leftIndex + 1] ? leftIndex + 1 : leftIndex;
            moreIndex = heap[index] < heap[moreIndex] ? moreIndex : index;
            if (moreIndex == index) return;
            ArrayUtil.swap(heap,moreIndex,index);
            index = moreIndex;
            leftIndex = (index << 1) + 1;
        }
    }

    private static void testHeap(){
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            Heap my = new Heap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    private static void testHeapSort(){
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtil.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtil.copyArr(arr1);
//            heapSort(arr1);
            heapSort2(arr1);
            Arrays.sort(arr2);
            if (!ArrayUtil.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
    public static void main(String[] args) {
        testHeapSort();
    }
}
