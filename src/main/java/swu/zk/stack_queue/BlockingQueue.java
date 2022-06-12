package swu.zk.stack_queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname BlockingQueue
 * @Description
 * 阻塞队列
 * @Date 2022/6/11 20:54
 * @Created by brain
 */
public class BlockingQueue<E> {
    private int takeIndex;
    private int putIndex;
    private int count;
    private Object[] items;

    private final ReentrantLock lock;
    /** Condition for waiting takes */
    private Condition notFull;
    /** Condition for waiting puts */
    private Condition notEmpty;

    public BlockingQueue(int capacity) {
        this(capacity,false);
    }
    public BlockingQueue(int capacity,boolean fair) {
        items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        }finally {
            lock.unlock();
        }
    }

    private E dequeue() {
        E item = (E)items[takeIndex];
        if (++takeIndex == items.length){
            takeIndex = 0;
        }
        count--;
        notFull.signal();
        return item;
    }
    public void put(E e) throws InterruptedException {
        if (e == null)throw new NullPointerException();
        lock.lockInterruptibly();
        try{
            while (count == items.length){
                notFull.await();
            }
            enqueue(e);
        }finally {
            lock.unlock();
        }
    }

    public boolean offer(E e) {
        if (e == null) throw  new NullPointerException();
        final ReentrantLock lock = this.lock;
       lock.lock();
       try {
           if (count == items.length){
               return false;
           }else {
               enqueue(e);
               return true;
           }
       }finally {
           lock.unlock();
       }
    }

    private void enqueue(E e) {
        final Object[] items = this.items;
        items[putIndex] = e;
        if (++putIndex == items.length){
            putIndex = 0;
        }
        count++;
        /**
         * 可能抛出IllegalMonitorStateException异常
         */
        notEmpty.signal();
    }


    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>(3);
        for (int i = 0; i < 5; i++) {
            queue.put(i);
        }
    }
}
