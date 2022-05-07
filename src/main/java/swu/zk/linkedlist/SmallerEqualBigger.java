package swu.zk.linkedlist;

import swu.zk.util.ArrayUtil;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Classname SmallerEqualBigger
 * @Description TODO
 * @Date 2022/5/7 15:36
 * @Created by brain
 */
public class SmallerEqualBigger {
    /**
     * 可以使用快排的partition过程
     * 额外空间复杂度O(n)
     *
     * @param head
     * @param pivot
     */
    public static ListNode smallerEqualBigger(ListNode head, int pivot) {
        if (head == null || head.next == null) return head;
        Deque<ListNode> deque = new LinkedList<>();
        ListNode temp = head;
        while (head != null) {
            deque.push(head);
            head = head.next;
        }
        ListNode[] arr = deque.toArray(new ListNode[0]);
        arrPartition(arr, pivot);
        int i = 0;
        for (; i < arr.length - 1; i++) {
            arr[i].next = arr[i + 1];
        }
        arr[i].next = null;
        return arr[0];
    }

    private static void arrPartition(ListNode[] arr, int pivot) {
        int less = -1;
        int more = arr.length;
        int cur = 0;
        while (cur != more) {
            if (arr[cur].value > pivot) {
                swap(arr, cur, --more);
            } else if (arr[cur].value < pivot) {
                swap(arr, cur++, ++less);
            } else {
                cur++;
            }
        }
    }

    private static void swap(ListNode[] arr, int index1, int index2) {
        ListNode temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * 指针 额外空间复杂度O(1)
     *
     * @param head
     * @param pivot
     * @return
     */
    public static ListNode smallerEqualBigger2(ListNode head, int pivot) {
        if (head == null || head.next == null) return head;
        ListNode smallHead = null;
        ListNode equalHead = null;
        ListNode thanHead = null;
        ListNode smallTail = null;
        ListNode equalTail = null;
        ListNode thanTail = null;
        ListNode temp = null;

        while (head != null) {
            temp = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (smallHead == null) {
                    smallHead = smallTail = head;
                } else {
                    smallTail.next = head;
                    smallTail = head;
                }
            } else if (head.value == pivot) {
                if (equalHead == null) {
                    equalHead = equalTail = head;
                } else {
                    equalTail.next = head;
                    equalTail = head;
                }
            } else {
                if (thanHead == null) {
                    thanHead = thanTail = head;
                } else {
                    thanTail.next = head;
                    thanTail = head;
                }
            }
            head = temp;
        }

        if (smallTail != null) {
            smallTail.next = equalHead;
            equalTail = equalTail == null ? smallTail : equalTail;
        }
        if (equalTail != null){
            equalTail.next = thanHead;
        }

        return smallHead != null ? smallHead : (equalHead == null ? thanHead : equalHead);
    }


    public static void main(String[] args) {
        ListNode head = BaseOperation.tailCreatLinkedList(new int[]{1, 2, 5, 3, 2, 4, 6, 2, 1, 8});
        ListNode newHead = smallerEqualBigger2(head.next, 3);
        BaseOperation.printLinkedList(newHead);
    }
}
