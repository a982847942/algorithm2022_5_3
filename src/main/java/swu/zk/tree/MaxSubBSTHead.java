package swu.zk.tree;

import java.util.ArrayList;

/**
 * @Classname MaxSubBSTHead
 * @Description 返回二叉树中最大BST的头结点
 * @Date 2022/5/12 10:23
 * @Created by brain
 */
public class MaxSubBSTHead {
    /**
     * 中序遍历
     * @param head
     * @return
     */
    public static BinaryTreeNode maxSubBSTHead1(BinaryTreeNode head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        BinaryTreeNode leftAns = maxSubBSTHead1(head.left);
        BinaryTreeNode rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    public static int getBSTSize(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<BinaryTreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(BinaryTreeNode head, ArrayList<BinaryTreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }


    /**
     *二叉树递归套路
     * @return
     */
    private static BinaryTreeNode maxSubBSTHead(BinaryTreeNode head){
        if (head == null) return head;
        MInfo res = p(head);
        return res.subHead;
    }
    private static class MInfo{
        int max;
        int min;
        BinaryTreeNode subHead;
        int maxSubSize;

        public MInfo(int max, int min, BinaryTreeNode subHead, int maxSubSize) {
            this.max = max;
            this.min = min;
            this.subHead = subHead;
            this.maxSubSize = maxSubSize;
        }
    }
    private static MInfo p(BinaryTreeNode node){
        if (node == null) return null;
        MInfo leftInfo = p(node.left);
        MInfo rightInfo = p(node.right);
        int max = node.value;
        int min = node.value;
        int maxSubSize = 0;
        BinaryTreeNode subHead = null;
        if (leftInfo != null){
            max = Math.max(max,leftInfo.max);
            min = Math.min(min,leftInfo.min);
            maxSubSize = leftInfo.maxSubSize;
            subHead = leftInfo.subHead;
        }
        if (rightInfo != null){
            max = Math.max(max,rightInfo.max);
            min = Math.min(min,rightInfo.min);
            if (rightInfo.maxSubSize > maxSubSize){
                maxSubSize = rightInfo.maxSubSize;
                subHead = rightInfo.subHead;
            }
        }
        boolean leftIsBST = leftInfo == null ? true : node.left == leftInfo.subHead;
        boolean rightIsBST = rightInfo == null ? true : node.right == rightInfo.subHead;
        if (leftIsBST && rightIsBST){
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < node.value);
            boolean rightMinMoreX =  rightInfo == null ? true : (rightInfo.min > node.value);
            if (leftMaxLessX && rightMinMoreX){
                maxSubSize = (leftInfo == null ? 0 : leftInfo.maxSubSize) + (rightInfo == null ? 0 : rightInfo.maxSubSize) + 1;
                subHead = node;
            }
        }
        return new MInfo(max,min,subHead,maxSubSize);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            BinaryTreeNode root = BTUtil.generateRandomBT(maxLevel, maxValue);
            if (maxSubBSTHead(root) != maxSubBSTHead1(root)){
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
