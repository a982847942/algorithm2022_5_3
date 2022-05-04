package swu.zk.linkedlist;

/**
 * @Classname ReverseLinkedList
 * @Description TODO
 * @Date 2022/5/4 11:10
 * @Created by brain
 */
public class ReverseLinkedList {
    /**
     * 三指针法反转单链表  接收的head不应该是guard结点
     *
     * @param head
     * @return
     */
    public static ListNode reverseLinkedList(ListNode head) {
        if (head == null) return head;
        ListNode temp = head;
        ListNode prev = null;
        ListNode next = null;
        while (temp != null) {
            next = temp.next;
            temp.next = prev;
            prev = temp;
            temp = next;
        }
//        head.next = prev;
        return prev;
    }

    /**
     * 头插法反转单链表
     *
     * @param head
     * @return
     */
    public static ListNode reverseLinkedList2(ListNode head) {
        if (head == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = head;
        ListNode removed = null;
        while (cur != null && cur.next != null) {
            removed = cur.next;
            cur.next = removed.next;
            removed.next = dummy.next;
            dummy.next = removed;
        }
        return dummy.next;
    }

    /**
     * 反转双向链表
     *
     * @param head
     * @return
     */
    public static DoubleListNode reverseDoubleLinkedList(DoubleListNode head) {
        if (head == null) return head;
        DoubleListNode temp = head;
        DoubleListNode pre = null;
        DoubleListNode next = null;
        while (temp != null) {
            next = temp.next;
            temp.next = pre;
            temp.prev = next;
            pre = temp;
            temp = next;
        }
//        pre.prev = head;
//        head.next = pre;
        return pre;
    }

    /**
     * 反转指定范围内的链表
     *
     * @param head  哨兵结点
     * @param left
     * @param right
     * @return
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left <= 0 || right <= 0 || left >= right) return head;
        int count = 1;
        ListNode pre = head;
        //找到left的前一个结点
        while (pre != null && count < left) {
            pre = pre.next;
            count++;
        }
        if (pre == null) throw new RuntimeException("invalid left value");
        //找到right结点
        ListNode rightNode = pre;
        while (rightNode != null && count <= right) {
            rightNode = rightNode.next;
            count++;
        }
        if (rightNode == null) throw new RuntimeException("invalid right value");

        //切除一个子链表
        ListNode leftNode = pre.next;
        ListNode pivotNode = rightNode.next;
        pre.next = null;
        rightNode.next = null;
        reverseLinkedList(leftNode);

        //连接新链表
        pre.next = rightNode;
        leftNode.next = pivotNode;
        return head.next;
    }

    /**
     * 头插法 反转指定范围内的链表
     *
     * @param head  哨兵结点
     * @param left
     * @param right
     * @return
     */
    public static ListNode reverseBetween2(ListNode head, int left, int right) {
        if (head == null || left <= 0 || right <= 0 || left >= right) return head;
        ListNode guardNode = head;
        ListNode curNode = guardNode.next;
        int count = 1;
        while (curNode != null && count < left) {
            guardNode = guardNode.next;
            curNode = curNode.next;
            count++;
        }
        if (curNode == null) throw new RuntimeException("invalid left value");

        ListNode removed = null;
        try {
            for (int i = left; i <= right - left; i++) {
                removed = curNode.next;
                curNode.next = removed.next;
                removed.next = guardNode.next;
                guardNode.next = removed;
            }
        } catch (Exception e) {
            if (removed == null) throw new RuntimeException("invalid right value");
        }
        return head.next;
    }

    /**
     * k个一组 反转链表 递归方法
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return head;
        ListNode startNode = head;
        ListNode endNode = head;
        for (int i = 0; i < k; i++) {
            if (endNode == null) return head;
            endNode = endNode.next;
        }
        ListNode newStart = reverseLinkedList3(startNode, endNode);
        startNode.next = reverseKGroup(endNode, k);
        return newStart;
    }

    /**
     * 配合上面的方法完成k个一组反转链表  不想另外写新方法只需要在上面把endnode链断开即可
     *
     * @param startNode
     * @param endNode
     * @return
     */
    private static ListNode reverseLinkedList3(ListNode startNode, ListNode endNode) {
        ListNode pre = null;
        ListNode next = startNode;
        ListNode temp = startNode;
        while (next != endNode) {
            next = temp.next;
            temp.next = pre;
            pre = temp;
            temp = next;
        }
        return pre;
    }

    /**
     * k个一组 反转链表 非递归方法
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode end = dummy;
        while (end.next != null){
            int count = 0;
            while (count < k && end != null){
                end= end.next;
                count++;
            }
            if (end == null) break;
            //反转链表时的起始结点
            ListNode start = prev.next;
            //保留下一个K单位长度的链表的首节点
            ListNode next = end.next;
            //将链断开
            end.next = null;
            //反转后 再将链接上
            prev.next = reverseLinkedList(start);
            //上一个链的开头反转后变为结尾 链接上下一个链的开头
            start.next = next;
            //更新prev和end
            prev = start;
            end = prev;
        }
        return dummy.next;
    }

    /**
     * k个一组 反转链表 使用头插法 实质上跟上面方法2一样，只是反转链表的策略不同。
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup3(ListNode head, int k) {
        if (head == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;
        while (end.next != null){
            int count = 0;
            while (count < k && end != null) {
                count++;
                end = end.next;
            }
            if (end == null) break;

            //尾插法主要逻辑
            ListNode snap = pre.next;
            while (pre.next != end){
                ListNode curNode = pre.next;
                pre.next = curNode.next;
                curNode.next = end.next;
                end.next = curNode;
            }
            //更新为下一个子链表的起始位置的前一结点
            pre = snap;
            end = snap;
        }
        return dummy.next;
    }


    public static void main(String[] args) {
//        ListNode head = BaseOperation.tailCreatLinkedList(new int[]{1, 2, 3, 4, 5, 6});
//        BaseOperation.printLinkedList(reverseLinkedList(head.next));
//        DoubleListNode doubleListNode = BaseOperation.tailCreatDoubleLinkedList(new int[]{1, 2, 3, 4, 5, 6});
//        BaseOperation.printDoubleLinkedList(reverseDoubleLinkedList(doubleListNode.next));


//        ListNode head = BaseOperation.tailCreatLinkedList(new int[]{1, 2, 3, 4, 5, 6});
//        ListNode reverseNode = reverseBetween2(head, 1, 6);
//        BaseOperation.printLinkedList(reverseNode);

        ListNode head = BaseOperation.tailCreatLinkedList(new int[]{1, 2, 3, 4, 5, 6});
        ListNode node = reverseKGroup3(head.next, 4);
        BaseOperation.printLinkedList(node);
    }
}
