package swu.zk.tree;

/**
 * @Classname ThreadedBT
 * @Description TODO
 * @Date 2022/5/9 11:42
 * @Created by brain
 */
public class ThreadedBT {
    private static class Node {
        Node left;
        Node right;
        Integer value;
        int lTag;
        int rTag;

        public Node(Integer value) {
            this.value = value;
        }
    }

    private static Node preNode = null;

    private static void inThread(Node head) {
        if (head != null) {
            inThread(head.left);
            if (head.left == null) {
                head.left = preNode;
                head.lTag = 1;
            }
            if (preNode != null && preNode.right == null) {
                preNode.right = head;
                preNode.rTag = 1;
            }
            preNode = head;
            inThread(head.right);
        }
    }

    private static void createInThread(Node head) {
        if (head != null) {
            inThread(head);
            preNode.right = null;
            preNode.rTag = 1;
        }
    }

    private static Node nextNode(Node cur) {
        if (cur.rTag == 0) return firstNode(cur.right);
        return cur.right;
    }

    private static Node firstNode(Node node) {
        while (node.lTag == 0) node = node.left;
        return node;
    }

    private static void preInThread(Node head) {
        if (head == null) return;
        createInThread(head);
        Node cur = firstNode(head);
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = nextNode(cur);
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        preInThread(head);
    }

}
