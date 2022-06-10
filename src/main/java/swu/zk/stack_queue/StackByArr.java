package swu.zk.stack_queue;

/**
 * @Classname StackByArr
 * @Description 数组实现的栈
 * @Date 2022/6/10 12:38
 * @Created by brain
 */
public class StackByArr {

    private int size;
    private int limit;
    private int[] arr;

    public StackByArr(int limit) {
        this.limit = limit;
        arr = new int[limit];
    }

    public void push(int value) {
        if (size == limit) throw new RuntimeException("栈已满");
        arr[size++] = value;
    }

    public int pop() {
        if (size == 0) throw new RuntimeException("栈已空");
        return arr[--size];
    }

}
