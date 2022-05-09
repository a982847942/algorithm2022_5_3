package swu.zk.linkedlist;

/**
 * @Classname FindFirstIntersectNdoe
 * @Description TODO
 * @Date 2022/5/7 20:30
 * @Created by brain
 */
public class FindFirstIntersectNode {
    public static ListNode getIntersectNode(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) return null;
        //1.判断链表是否有环
        ListNode loopNode1 = getLoopNode(head1);
        ListNode loopNode2 = getLoopNode(head2);
        //情况一： 两个链表都没有环
        if (loopNode1 == null && loopNode2 == null) {
            return noLoop2(head1, head2);
        }
        //情况二: 两个链表都有环
        //1.两个链表的入环结点相同   2.两个链表入环结点不同  3.两个链表不相交
        if (loopNode1 != null && loopNode2 != null) {
            return bothLoop(loopNode1, head1, loopNode2, head2);
        }

        //情况三：一个有环  一个无环 则必然不可能相交
        return null;
    }

    private static ListNode bothLoop(ListNode loopNode1, ListNode head1, ListNode loopNode2, ListNode head2) {
        ListNode cur1 = null;
        ListNode cur2 = null;
        if (loopNode1 == loopNode2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loopNode1) {
                n++;
                cur1 = cur1.next;
            }

            while (cur2 != loopNode2) {
                n--;
                cur2 = cur2.next;
            }

            cur1 = n >= 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;

            n = Math.abs(n);
            while (n-- != 0) {
                cur1 = cur1.next;
            }

            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loopNode1.next;
            while (cur1 != loopNode1) {
                if (cur1 == loopNode2) return loopNode1;
                cur1 = cur1.next;
            }
            return null;
        }
    }


    /**
     * 此方案不可行
     * 因为这种方法默认两个链表相交，事实上两个链表可能并不相交
     *
     * @param head1
     * @param head2
     * @return
     */
    private static ListNode noLoop1(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        while (cur1 != cur2) {
            cur1 = cur1.next == null ? head2 : cur1.next;
            cur2 = cur2.next == null ? cur1 : cur2.next;
        }
        return cur1;
    }

    private static ListNode noLoop2(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) return null;
        int n = 0;
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }

        if (cur1 != cur2) return null;

        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n-- != 0) {
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    public static ListNode getLoopNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return null;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        //找到相遇点
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        }

        //找到入环结点
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);

        // 0->9->8->6->7->null
        ListNode head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);
    }
}
