package swu.zk.tree;

import java.util.ArrayList;

/**
 * @Classname LowestAncetor
 * @Description 给定一棵二叉树的头节点head，和另外两个节点a和b。
 * 返回a和b的最低公共祖先
 * @Date 2022/5/11 21:52
 * @Created by brain
 */
public class LowestAncestor {

    /**
     * 二叉树的递归套路
     * @param root
     * @param a
     * @param b
     * @return
     */
    public static BinaryTreeNode lowestAncestor(BinaryTreeNode root, BinaryTreeNode a, BinaryTreeNode b){
        if (root == null)return null;
        return doLowestAncestor(root,a,b).answer;
    }

    private static Info doLowestAncestor(BinaryTreeNode root, BinaryTreeNode a, BinaryTreeNode b) {
        if (root == null) return new Info(false,false,null);
        Info leftInfo = doLowestAncestor(root.left, a, b);
        Info rightInfo = doLowestAncestor(root.right, a, b);
        boolean findA = root == a || leftInfo.findA || rightInfo.findA;
        boolean findB = root == b || leftInfo.findB || rightInfo.findB;
        BinaryTreeNode answer = null;
        if (leftInfo.answer != null) {
            answer = leftInfo.answer;
        } else if (rightInfo.answer != null) {
            answer = rightInfo.answer;
        } else if (findA && findB){
           answer = root;
        }
        return new Info(findA,findB,answer);
    }

    private static class  Info{
        boolean findA;
        boolean findB;
        BinaryTreeNode answer;

        public Info(boolean findA, boolean findB, BinaryTreeNode answer) {
            this.findA = findA;
            this.findB = findB;
            this.answer = answer;
        }
    }






    private static BinaryTreeNode lowestAncestor2(BinaryTreeNode root, BinaryTreeNode a, BinaryTreeNode b) {
        //head 等于a 或 b 或 null就直接返回
        if (root == null || root == a || root == b) return root;
        BinaryTreeNode leftInfo = lowestAncestor2(root.left, a, b);
        BinaryTreeNode rightInfo = lowestAncestor2(root.right, a, b);
        //如果left未发现 信息 就由right决定
        if (leftInfo == null) return rightInfo;
        //如果right未发现 就由left决定
        if (rightInfo == null) return leftInfo;
        //如果left  right 都发现信息 就说明 a b 在两侧
        return root;
    }



    // for test
    public static BinaryTreeNode pickRandomOne(BinaryTreeNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<BinaryTreeNode> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(BinaryTreeNode head, ArrayList<BinaryTreeNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = BTUtil.generateRandomBT(maxLevel, maxValue);
            BinaryTreeNode o1 = pickRandomOne(head);
            BinaryTreeNode o2 = pickRandomOne(head);
            if (lowestAncestor(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
