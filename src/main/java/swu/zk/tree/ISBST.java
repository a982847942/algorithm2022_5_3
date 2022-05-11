package swu.zk.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Classname ISBST
 * @Description 判断一棵二叉树 是否是搜索二叉树
 * @Date 2022/5/10 21:21
 * @Created by brain
 */
public class ISBST {

    /**
     * 二叉排序树的中序遍历一定是升序序列
     *
     * @param root
     * @return
     */
    public static boolean isBST(BinaryTreeNode root) {
        Queue<Integer> arr = new LinkedList<>();
        doIsBST(root, arr);
        Integer pre = arr.poll();
        while (!arr.isEmpty()) {
            Integer temp = arr.poll();
            if (temp <= pre) return false;
            pre = temp;
        }
        return true;
    }

    private static void doIsBST(BinaryTreeNode root, Queue<Integer> arr) {
        if (root == null) return;
        doIsBST(root.left, arr);
        arr.add(root.value);
        doIsBST(root.right, arr);
    }


    /**
     * 使用二叉树的递归套路
     * 后序遍历后需要收集的信息：
     * 1.isBST
     * 2.minValue
     * 3.maxValue
     * 满足条件的情况为：左 右子树isBST = true 左子树maxValue < curValue < 右子树minValue
     *
     * @param root
     * @return
     */
    public static boolean isBST2(BinaryTreeNode root) {
        if (root == null) return true;
        return doIsBST2(root).isBST;
    }

    private static Info doIsBST2(BinaryTreeNode root) {
        if (root == null) return null;

        Info leftInfo = doIsBST2(root.left);
        Info rightInfo = doIsBST2(root.right);

        boolean isBST = true;
        Integer min = root.value;
        Integer max = root.value;
        if (leftInfo != null){
            min = Math.min(min,leftInfo.min);
            max = Math.max(max,leftInfo.max);
        }
        if (rightInfo != null){
            min = Math.min(min,rightInfo.min);
            max = Math.max(max,rightInfo.max);
        }

        if (leftInfo != null && !leftInfo.isBST) isBST = false;
        if (rightInfo != null && !rightInfo.isBST) isBST = false;
        if (leftInfo != null && root.value <= leftInfo.max) isBST = false;
        if (rightInfo != null && root.value >= rightInfo.min) isBST = false;
        return new Info(isBST,min,max);
    }


    private static class Info {
        boolean isBST;
        Integer min;
        Integer max;

        public Info(boolean isBST, Integer min, Integer max) {
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }


    public static void main(String[] args) {
//        BinaryTreeNode root = BTUtil.createBinaryTree("531##4##86##9##".toCharArray());
//        TraversalBT.level(root);
//        System.out.println(isBST(root));
//        System.out.println(isBST2(root));

        int maxLevel = 4;
        int maxValue = 100;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            BinaryTreeNode root = BTUtil.generateRandomBT(maxLevel, maxValue);
            if (isBST(root) != isBST2(root)){
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
