package swu.zk.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname ISCBT
 * @Description 判断给定的二叉树是否是完全二叉树
 * @Date 2022/5/10 17:09
 * @Created by brain
 */
public class ISCBT {

    /**
     * 层序遍历  讨论左右孩子的各种可能情况
     * @param root
     * @return
     */
    public static boolean isCBT(BinaryTreeNode root){
        if (root == null) return true;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean flag = true;
        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                BinaryTreeNode node = queue.poll();
                if (!flag && (node.left != null || node.right != null)) return false;
                if ((node.left == null && node.right != null)) return false;
                if ((node.left != null && node.right == null) || (node.left == null && node.right == null))flag = false;
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return true;
    }


    /**
     * 使用二叉树的递归套路来解决  该思路是一种通用的思路 实质是二叉树的后续遍历
     * 因此时间复杂度也为O(n)
     * CBT依赖于左子树 与 右子树的相关信息
     * 1.左子树 右子树都为FBT且高度相等 或者左子树高度 = 右子树高度 + 1
     * 2.左子树 右子树都为CBT  且左子树高度 = 右子树高度 + 1
     * @param root
     * @return
     */
   public static boolean isCBT2(BinaryTreeNode root){
        if (root == null) return true;
        return doIsCBT2(root).isCBT;
   }

    private static Info doIsCBT2(BinaryTreeNode root) {
       if (root == null) return new Info(true,true,0);
        Info leftInfo = doIsCBT2(root.left);
        Info rightInfo = doIsCBT2(root.right);
        boolean isFBT = false;
        isFBT = leftInfo.isFBT && rightInfo.isFBT && (leftInfo.height == rightInfo.height);

        boolean isCBT = false;
        if (isFBT){
            isCBT = true;
        }else {

            if (leftInfo.isCBT && rightInfo.isCBT){
                if (leftInfo.isFBT && rightInfo.isFBT && (leftInfo.height == rightInfo.height + 1)){
                    isCBT = true;
                }
                if (leftInfo.isCBT && rightInfo.isFBT && (leftInfo.height == rightInfo.height + 1)){
                    isCBT = true;
                }
                if (leftInfo.isFBT && rightInfo.isCBT && (leftInfo.height == rightInfo.height)){
                    isCBT = true;
                }
            }
        }
        return new Info(isCBT,isFBT,Math.max(leftInfo.height,rightInfo.height) + 1);
    }

    private static class Info{
       boolean isCBT;
       boolean isFBT;
       int height;

       public Info(boolean isCBT, boolean isFBT, int height) {
           this.isCBT = isCBT;
           this.isFBT = isFBT;
           this.height = height;
       }
   }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = BTUtil.generateRandomBT(maxLevel, maxValue);
            if (isCBT(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

//        BinaryTreeNode root = BTUtil.createBinaryTree("1238###4##56##7##".toCharArray());
//        TraversalBT.level(root);
//        System.out.println(isCBT(root));
    }
}
