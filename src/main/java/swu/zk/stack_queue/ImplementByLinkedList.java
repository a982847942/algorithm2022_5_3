package swu.zk.stack_queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Classname ImplementByLinkedList
 * @Description 使用双向链表实现栈 和  队列
 * @Date 2022/6/10 13:16
 * @Created by brain
 */
public class ImplementByLinkedList {
    static class Node<T> {
        T value;
        Node<T> pre;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    static class DoubleLinkedList<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size;

        public void addFromHead(T value) {
            size++;
            Node<T> temp = new Node<>(value);
            if (head == null) {
                head = temp;
                tail = temp;
            } else {
                head.pre = temp;
                temp.next = head;
                head = temp;
            }
        }

        public void addFromBottom(T value) {
            size++;
            Node<T> temp = new Node<>(value);
            if (tail == null) {
                head = temp;
                tail = temp;
            } else {
                tail.next = temp;
                temp.pre = tail;
                tail = temp;
            }
        }

        public T popFromHead() {
            if (head == null) return null;
            size--;
            Node<T> res = head;
            if (head == tail) {
                head = null;
                tail = null;
                return res.value;
            } else {
                head = head.next;
                head.pre = null;
                res.next = null;
                return res.value;
            }

        }

        public T popFromBottom() {
            if (tail == null) return null;
            size--;
            Node<T> res = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.pre;
                tail.next = null;
                res.pre = null;
            }
            return res.value;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public int size() {
            return size;
        }
    }


    public static class MyStack<T> {
        private DoubleLinkedList<T> queue;

        public MyStack() {
            queue = new DoubleLinkedList<>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T pop() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

    public static class MyQueue<T> {
        private DoubleLinkedList<T> queue;

        public MyQueue() {
            queue = new DoubleLinkedList<>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T poll() {
            return queue.popFromBottom();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }


    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }
}
