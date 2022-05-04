package swu.zk.linkedlist;

/**
 * @Classname BaseOperation
 * @Description 链表基本操作
 * @Date 2022/5/4 9:48
 * @Created by brain
 */
public class BaseOperation {
    /**
     * 头插法建立单链表，带有dummy结点
     *
     * @param arr
     * @return
     */
    public static ListNode headCreatLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(0);
        for (int i = 0; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            node.next = head.next;
            head.next = node;
        }
        return head;
    }

    /**
     * 头插法创建双向链表
     * @param arr
     * @return
     */
    public static DoubleListNode headCreatDoubleLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        DoubleListNode head = new DoubleListNode(0);
        for (int i = 0; i < arr.length; i++) {
            DoubleListNode node = new DoubleListNode(arr[i]);
            node.next = head.next;
            node.prev = head;
            head.next = node;
        }
        return head;
    }

    /**
     * 尾插法创建单链表
     *
     * @param arr
     * @return
     */
    public static ListNode tailCreatLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(0);
        ListNode tail = head;
        for (int i = 0; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            tail.next = node;
            tail = node;
        }
        return head;
    }

    /**
     * 尾插法创建双向链表
     * @param arr
     * @return
     */

    public static DoubleListNode tailCreatDoubleLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        DoubleListNode head = new DoubleListNode(0);
        DoubleListNode tail = head;
        for (int i = 0; i < arr.length; i++) {
            DoubleListNode node =new DoubleListNode(arr[i]);
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        return head;
    }


    /**
     * 打印单链表  head不应该是guard结点
     * @param head
     */

    public static void printLinkedList(ListNode head) {
        if (head == null) return;
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * 打印双向链表
     * @param head
     */
    public static void printDoubleLinkedList(DoubleListNode head) {
        if (head == null) return;
        DoubleListNode temp = head;
        while (temp != null){
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * 删除value为给定的值的结点
     *
     * @param head
     * @param value
     * @return
     */
    public static ListNode deleteGivenValue(ListNode head, int value) {
        if (head == null) return null;
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.value == value) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }




    public static void main(String[] args) {
//        ListNode head = tailCreatLinkedList(new int[]{4,1, 2, 3, 4,4, 5,4, 6});
//        printLinkedList(head);
//        ListNode node = deleteGivenValue(head, 4);
//        printLinkedList(node);
        printDoubleLinkedList(tailCreatDoubleLinkedList(new int[]{1,2,3,4,5,6}));
        printDoubleLinkedList(headCreatDoubleLinkedList(new int[]{1,2,3,4,5,6}));
    }
}
