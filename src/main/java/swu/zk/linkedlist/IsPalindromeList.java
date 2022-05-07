package swu.zk.linkedlist;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @Classname IsPalindromeList
 * @Description TODO
 * @Date 2022/5/7 15:00
 * @Created by brain
 */
public class IsPalindromeList {

    /**
     * 使用容器来存储 然后判断遍历判断是否是回文序列
     * 额外空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        Deque<ListNode> deque = new LinkedList<>();
        ListNode temp = head;
        while (temp != null) {
            deque.push(temp);
            temp = temp.next;
        }
        while (head != null) {
            if (head.value != deque.pop().value) return false;
            head = head.next;
        }
        return true;
    }

    /**
     * 同上 额外空间复杂度O(n/2)
     *
     * @param head
     * @return
     */

    public static boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null) return true;
        Deque<ListNode> deque = new LinkedList<>();
        ListNode fast = head;
        ListNode slow = head.next;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        while (slow != null) {
            deque.push(slow);
            slow = slow.next;
        }

        while (!deque.isEmpty()) {
            if (head.value != deque.pop().value) return false;
            head = head.next;
        }
        return true;
    }

    /**
     * 双指针  额外空间复杂度O(1)
     * @param head
     * @return
     */
    public static boolean isPalindrome3(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode slow = head.next;
        ListNode fast = head.next;
        //循环结束后 slow指针指向 中点或下中点
        while (fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        //反转链表
        ListNode newHead = ReverseLinkedList.reverseLinkedList2(slow);
        ListNode guardNdoe = newHead;
        while (newHead != null){
            if (head.value != newHead.value) return false;
            head = head.next;
            newHead = newHead.next;
        }
        //恢复链表
        ReverseLinkedList.reverseLinkedList(guardNdoe);
        return true;
    }


    public static void main(String[] args) {
        ListNode list = BaseOperation.tailCreatLinkedList(new int[]{1, 2, 3, 4, 3, 2, 5});
        System.out.println(isPalindrome3(list.next));
    }
}
