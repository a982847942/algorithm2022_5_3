package swu.zk.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CopyListWithRandom
 * @Description
 * @Date 2022/5/7 16:51
 * @Created by brain
 */
public class CopyListWithRandom {

    private static class Node{
        int value;
        Node next;
        Node random;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 使用容器  空间复杂度O(n)
     * @param head
     * @return
     */
    public static Node copyListWithRandom1(Node head) {
        if (head == null) return head;
        Map<Node,Node> nodeMap = new HashMap<>();
        Node cur = head;
        while (cur != null){
            nodeMap.put(cur,new Node(cur.value));
            cur = cur.next;
        }

        cur = head;
        while (cur != null){
            Node node = nodeMap.get(cur);
            node.next = nodeMap.get(cur.next);
            node.random = nodeMap.get(cur.random);
            cur = cur.next;
        }
        return nodeMap.get(head);
    }

    /**
     * 不适用容器 额外空间复杂度O(1)
     * @param head
     * @return
     */
    public static Node copyListWithRandom2(Node head) {
        if (head == null) return head;
        Node cur = head;
        Node next = null;
        //建结点
        while (cur != null){
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }

        //指向random指针
        cur = head;
        Node copyCur = null;
        while (cur != null){
            next = cur.next.next;
            copyCur = cur.next;
            copyCur.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }

        //分离两个链表
        Node resNode = head.next;
        cur = head;
        while (cur != null){
            next = cur.next.next;
            copyCur = cur.next;
            copyCur.next =  next == null ? null : next.next;
            cur.next = next;
            cur = next;
        }
        return resNode;
    }


    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.random == null ? "- " : cur.random.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        head.random = node3;
        node2.random = node4;
        node3.random = node5;
        node4.random = head;
        node5.random = node2;

        Node node = copyListWithRandom2(head);
       printRandLinkedList(node);
    }
}
