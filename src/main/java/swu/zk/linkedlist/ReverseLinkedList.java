package swu.zk.linkedlist;

/**
 * @Classname ReverseLinkedList
 * @Description TODO
 * @Date 2022/5/4 11:10
 * @Created by brain
 */
public class ReverseLinkedList {
    /**
     * 反转单链表
     * @param head
     * @return
     */
    public static ListNode reverseLinkedList(ListNode head) {
        if (head == null) return head;
        ListNode temp = head.next;
        ListNode prev = null;
        ListNode next = null;
        while (temp != null) {
            next = temp.next;
            temp.next = prev;
            prev = temp;
            temp = next;
        }
        head.next = prev;
        return head;
    }

    /**
     * 反转双向链表
     * @param head
     * @return
     */
    public static DoubleListNode reverseDoubleLinkedList(DoubleListNode head){
        if (head == null) return head;
        DoubleListNode temp = head.next;
        DoubleListNode pre = null;
        DoubleListNode next = null;
        while (temp != null){
            next = temp.next;
            temp.next = pre;
            temp.prev = next;
            pre = temp;
            temp = next;
        }
        pre.prev = head;
        head.next = pre;
        return head;
    }

    /**
     * 反转指定范围内的链表
     * @param head
     * @param left
     * @param right
     * @return
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        return null;
    }

    public static void main(String[] args) {
//        ListNode head = BaseOperation.tailCreatLinkedList(new int[]{1, 2, 3, 4, 5, 6});
//        BaseOperation.printLinkedList(reverseLinkedList(head));
        DoubleListNode doubleListNode = BaseOperation.tailCreatDoubleLinkedList(new int[]{1, 2, 3, 4, 5, 6});
        BaseOperation.printDoubleLinkedList(reverseDoubleLinkedList(doubleListNode));
    }
}
