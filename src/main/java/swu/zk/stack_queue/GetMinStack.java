package swu.zk.stack_queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @Classname GetMinStack
 * @Description 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 * 1.pop、push、getMin操作的时间复杂度都是 O(1)。
 * 2.设计的栈类型可以使用现成的栈结构。
 * @Date 2022/6/11 20:25
 * @Created by brain
 */
public class GetMinStack {
    private Deque<Integer> stack;
    private Deque<Integer> minStack;

    public GetMinStack() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(Integer value) {
        if (stack.isEmpty()) {
            stack.push(value);
            minStack.push(value);
        } else {
            if (minStack.peekFirst() <= value) {
                minStack.push(minStack.peekFirst());
            } else {
                minStack.push(value);
            }
            stack.push(value);
        }
    }

    public Integer pop() {
        if (!stack.isEmpty()) {
            minStack.pop();
            return stack.pop();
        }
        return null;
    }

    public Integer getMin() {
        if (!minStack.isEmpty()) {
            return minStack.peekFirst();
        }
        return null;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }


    /**
     * push时候对于新值的处理方式不同，可以看到这种方式存储的值更少
     */
    public static class MyStack1 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack1() {
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }

        public void push(int newNum) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            } else if (newNum <= this.getmin()) {
                this.stackMin.push(newNum);
            }
            this.stackData.push(newNum);
        }

        public int pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            int value = this.stackData.pop();
            if (value == this.getmin()) {
                this.stackMin.pop();
            }
            return value;
        }

        public int getmin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }
    }

    public static void main(String[] args) {
        int testTime = 100_0000;
        int maxValue = 100;
        GetMinStack minStack = new GetMinStack();
        MyStack1 stack1 = new MyStack1();
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            double random = Math.random();
            if (random < 0.3 && !minStack.isEmpty()) {
                if (minStack.getMin() != stack1.getmin()) {
                    System.out.println("oops");
                    break;
                }
            } else if (random > 0.6 && !minStack.isEmpty()) {
                Integer p1 = minStack.pop();
                int p2 = stack1.pop();
                if (p1 != p2) {
                    System.out.println("p1:" + p1);
                    System.out.println("p2:" + p2);
                    System.out.println("oops");
                    break;
                }
            } else {
                int temp = (int) (Math.random() * maxValue) + 1;
                minStack.push(temp);
                stack1.push(temp);
            }
        }
        System.out.println("finish");
    }
}
