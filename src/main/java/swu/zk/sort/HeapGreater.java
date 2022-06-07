package swu.zk.sort;

import swu.zk.util.ArrayUtil;

import java.util.*;

/**
 * @Classname HeapGreater
 * @Description
 * 加强堆，主要是增加了反向索引表，便于动态调整堆。
 * 比如普通堆只能依次有序弹出堆顶元素，加强堆可以弹出任意元素并完成调整(logN)
 * 同时加强堆还可以完成查看是否包含对应元素等操作(时间复杂度很低)
 * @Date 2022/6/6 16:46
 * @Created by brain
 */
public class HeapGreater<T> {
    //堆
    private ArrayList<T> heap;
    //堆大小
    private int heapSize;
    /**
     * 反向索引表，此时的反向索引表有不足之处
     * 如果T是基础类型的包装类型 可能产生覆盖现象，使用时要仔细考虑
     * 解决办法可以对基础类型进行包装。
     */
    private Map<T,Integer> indexMap;
    //比较器 可根据比较器来选择比较的属性和规则
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> comp) {
        this.comp = comp;
        heap = new ArrayList<>();
        heapSize = 0;
        indexMap = new HashMap<>();
    }


    //判断堆是否为空
    public boolean isEmpty(){
        return heapSize == 0;
    }
    //堆中元素个数
    public int size(){
        return heapSize;
    }
    public boolean contains(T obj){
        return indexMap.containsKey(obj);
    }
    //返回堆顶元素
    public T peek(){
        if (!isEmpty()){
            return heap.get(0);
        }
       return null;
    }
    //向堆中添加一个元素
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj,heapSize);
        heapInsert(heapSize++);
    }
    //弹出堆顶元素
    public T pop() {
        T result = heap.get(0);
        swap(0,--heapSize);
        indexMap.remove(result);
        heap.remove(heapSize);
        heapify(0);
        return result;
    }
    //删除堆中任意一个元素
    public void remove(T obj) {
        Integer index = indexMap.get(obj);
        T replace = heap.get(--heapSize);
        indexMap.remove(obj);
        heap.remove(heapSize);
        if (obj != replace){
            heap.set(index,replace);
            indexMap.put(replace,index);
            resign(replace);
        }
    }
    //调整堆
    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> res = new ArrayList<>();
        heap.forEach(t->{
            res.add(t);
        });
        return res;
    }

    private void heapInsert(int index) {
        int parent = (index - 1) >> 1;
        while (parent >= 0 && comp.compare(heap.get(index),heap.get(parent)) < 0){
            swap(index,parent);
            index = parent;
            parent = (index - 1) >> 1;
        }
    }
    private void heapify(int index) {
        int leftIndex = ((index << 1) | 1);
        while (leftIndex < heapSize){
            int bestIndex = leftIndex + 1 < heapSize &&
                    comp.compare(heap.get(leftIndex + 1),heap.get(leftIndex)) < 0 ? leftIndex + 1 : leftIndex;
            bestIndex = comp.compare(heap.get(index),heap.get(bestIndex)) <= 0 ? index : bestIndex;
            if (bestIndex == index) break;
            swap(index,bestIndex);
            index = bestIndex;
            leftIndex = ((index << 1) | 1);
        }
    }
    private void swap(int i, int j) {
        T obj1 = heap.get(i);
        T obj2 = heap.get(j);
        heap.set(i,obj2);
        heap.set(j,obj1);
        indexMap.put(obj1,j);
        indexMap.put(obj2,i);
    }


    public static void main(String[] args) {
        HeapGreater<Integer> heap = new HeapGreater<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        heap.push(5);
        heap.push(10);
        heap.push(2);
        heap.push(3);
        heap.push(7);
        heap.push(6);
        heap.push(1);
        heap.push(3);
        while (!heap.isEmpty()){
            System.out.println(heap.pop());
        }
    }

}
