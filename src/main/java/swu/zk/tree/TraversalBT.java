package swu.zk.tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Classname TraversalBT
 * @Description 遍历二叉树 ： 递归 非递归(先序 中序 后序)  层序  morris遍历
 * @Date 2022/5/8 10:06
 * @Created by brain
 */
public class TraversalBT {
    /**
     * 递归方法遍历二叉树
     *
     * @param root
     */

    public static void template(BinaryTreeNode root) {
        if (root == null) return;
        //先序
        template(root.left);
        //中序
        template(root.right);
        //后序
    }

    public static void pre(BinaryTreeNode root) {
        if (root == null) return;
        System.out.print(root.value + " ");
        pre(root.left);
        pre(root.right);
    }


    public static void in(BinaryTreeNode root) {
        if (root == null) return;
        in(root.left);
        System.out.print(root.value + " ");
        in(root.right);
    }

    public static void pos(BinaryTreeNode root) {
        if (root == null) return;
        pos(root.left);
        pos(root.right);
        System.out.print(root.value + " ");
    }


    /**
     * 非递归法遍历二叉树
     *
     * @param root
     */
    public static void preNoRecursive(BinaryTreeNode root) {
        if (root == null) return;
        Deque<BinaryTreeNode> deque = new LinkedList<>();
        deque.push(root);
        while (!deque.isEmpty()) {
            BinaryTreeNode node = deque.pop();
            System.out.print(node.value + " ");
            if (node.right != null) deque.push(node.right);
            if (node.left != null) deque.push(node.left);
        }
        System.out.println();
    }

    public static void inNoRecursive(BinaryTreeNode root) {
        if (root == null) return;
        Deque<BinaryTreeNode> deque = new LinkedList<>();
        while (!deque.isEmpty() || root != null) {
            if (root != null) {
                deque.push(root);
                root = root.left;
            } else {
                root = deque.pop();
                System.out.print(root.value + " ");
                root = root.right;
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历飞归 双栈法
     * 进栈：根 右 左  --> 出栈：左 右 根
     *
     * @param root
     */
    public static void posNoRecursive(BinaryTreeNode root) {
        if (root == null) return;
        Deque<BinaryTreeNode> deque1 = new LinkedList<>();
        Deque<BinaryTreeNode> deque2 = new LinkedList<>();
        deque1.push(root);
        while (!deque1.isEmpty()) {
            BinaryTreeNode node = deque1.pop();
            deque2.push(node);
            if (node.left != null) deque1.push(node.left);
            if (node.right != null) deque1.push(node.right);
        }
        while (!deque2.isEmpty()) {
            System.out.print(deque2.poll().value + " ");
        }
        System.out.println();
    }

    /**
     * 单栈法
     * 使用两个指针 记录当前访问的结点 和上一个已经访问的结点
     *
     * @param root
     */
    public static void posNoRecursive2(BinaryTreeNode root) {
        if (root == null) return;
        Deque<BinaryTreeNode> deque = new LinkedList<>();
        BinaryTreeNode cur = root;
        BinaryTreeNode pre = null;
        while (cur != null || !deque.isEmpty()) {
            //若当前结点不为空 则优先访问左孩子 让左孩子进栈
            if (cur != null) {
                deque.addLast(cur);
                cur = cur.left;
            } else {
                cur = deque.peekLast();
                //如果当前结点有右孩子 且 未访问过 则访问右孩子
                if (cur.right != null && pre != cur.right) {
                    cur = cur.right;
                } else {
                    //如果当前结点右孩子 为null  或 右孩子已经访问过 则访问当前结点
                    cur = deque.pollLast();
                    System.out.print(cur.value + " ");
                    pre = cur;
                    cur = null;
                }
            }
        }
        System.out.println();
    }

    /**
     * morris 遍历 实现空间复杂度O(1)
     * 规则：
     * 1.如果cur无左孩子，cur向右移动（cur=cur.right）
     * 2.如果cur有左孩子，找到cur左子树上最右的节点，记为mostright
     * 如果mostright的right指针指向空，让其指向cur，cur向左移动（cur=cur.left）
     * 如果mostright的right指针指向cur，让其指向空，cur向右移动（cur=cur.right）
     * 实质是二叉树的线索化！利用空闲指针
     *
     * @param root
     */
    public static void morrisPre(BinaryTreeNode root) {
        if (root == null) return;
        BinaryTreeNode cur = root;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    System.out.print(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;
            } else {
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    public static void morrisIn(BinaryTreeNode root) {
        if (root == null) return;
        BinaryTreeNode cur = root;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                System.out.print(cur.value + " ");
                mostRight.right = null;
            } else {
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    public static void morrisPos(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        BinaryTreeNode cur = head;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(BinaryTreeNode head) {
        BinaryTreeNode tail = reverseEdge(head);
        BinaryTreeNode cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static BinaryTreeNode reverseEdge(BinaryTreeNode from) {
        BinaryTreeNode pre = null;
        BinaryTreeNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }


    public static void main(String[] args) {
//        String s  = "123###45##6##";
//        BinaryTreeNode root = BTUtil.createBinaryTree(s.toCharArray());
//        pre(root);
//        System.out.println();
//        in(root);
//        System.out.println();
//        pos(root);
//        System.out.println();


//        String s = "124##5##36##7##";
//        BinaryTreeNode root = BTUtil.createBinaryTree(s.toCharArray());
//        preNoRecursive(root);
//        inNoRecursive(root);
//        posNoRecursive(root);
//        posNoRecursive2(root);

        String s = "124##5##36##7##";
        BinaryTreeNode root = BTUtil.createBinaryTree(s.toCharArray());
        morrisPre(root);
        morrisIn(root);
        morrisPos(root);
    }
}
