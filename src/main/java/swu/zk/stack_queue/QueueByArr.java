package swu.zk.stack_queue;

import java.util.Arrays;

/**
 * @Classname QueueByArr
 * @Description 数组实现的队列
 * @Date 2022/6/10 12:38
 * @Created by brain
 */
public class QueueByArr<E> {
    /**
     * 为什么这里要多用几个变量？ 实际上只需要一个begin 和 end 和limit完全可以 size只是为了便于解耦
     * 降低编码复杂度
     */
    private int capacity;//队列的最大容量
    private int size;//队列现有元素个数
    private int pollIndex;//队列元素开始位置
    private int pushIndex;//队列元素结束位置
    Object[] arr;

    public QueueByArr() {
        this(0);
    }

    public QueueByArr(int capacity) {
        if (capacity < 0) throw new RuntimeException("The capacity can not be less than 0");
        if (capacity == 0) capacity = 10;
        this.capacity = capacity;
        arr = new Object[capacity];
    }

    public void push(E value) {
        if (size == capacity) {
            grow();
        }
        size++;
        arr[pushIndex] = value;
        pushIndex = nextIndex(pushIndex);
    }

    private void grow() {
        int newCapacity = this.capacity + (this.capacity >> 1);
        Object[] newArr = new Object[newCapacity];
        int index = 0;
        for (int i = pollIndex; i < capacity ; i++) {
            newArr[index++] = arr[i];
        }
        for (int i = 0; i < pollIndex; i++) {
            newArr[index++] = arr[i];
        }
        arr = newArr;
        pollIndex = 0;
        pushIndex = this.capacity;
        this.capacity = newCapacity;
    }

    public E pop() {
        if (size == 0) throw new RuntimeException("The queue is Empty");
        E result = (E) arr[pollIndex];
        size--;
        pollIndex = nextIndex(pollIndex);
        return result;
    }

    public E peek(){
        if (size == 0) throw new RuntimeException("The queue is Empty");
        E result = (E)arr[pollIndex];
        return result;
    }

    private int nextIndex(int cur) {
        return cur < capacity - 1 ? cur + 1 : 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }

    public static void main(String[] args) {
        QueueByArr<Integer> queue = new QueueByArr<>();
        for (int i = 0; i < 10; i++) {
            queue.push(i + 1);
        }
        System.out.println(queue.pop());
//        System.out.println(queue.size);
//        System.out.println(queue.capacity);
//        System.out.println(queue.pollIndex);
//        System.out.println(queue.pushIndex);
        queue.push(12);
        queue.push(13);
        System.out.println(queue.pop());
        System.out.println("----------------------------------");
        System.out.println(queue.size);
        System.out.println(queue.capacity);
        System.out.println(queue.pollIndex);
        System.out.println(queue.pushIndex);
    }
}
