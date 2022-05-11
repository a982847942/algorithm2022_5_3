package swu.zk.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname ISFBT
 * @Description 判断一棵二叉树是否是满二叉树
 * @Date 2022/5/11 20:00
 * @Created by brain
 */
public class ISFBT {

    /**
     * 根据高度 与 结点个数满足 n = 2^h - 1来计算
     * @param root
     * @return
     */
    public static boolean isFBT(BinaryTreeNode root){
        if (root == null) return true;
        int height = BTUtil.getBinaryTreeHeight(root);
        int nodes = BTUtil.getBinaryTreeNodes(root);
        return nodes == ((1 << height) - 1);
    }

    public static boolean isFBT2(BinaryTreeNode root){
        if (root == null) return true;
        Info info = doIsFBT2(root);
        return info.nodes == ((1 << info.height) - 1);
    }

    /**
     * 与方法一一样
     * @param root
     * @return
     */
    private static Info doIsFBT2(BinaryTreeNode root) {
        if (root == null) return new Info(0,0);
        Info leftInfo = doIsFBT2(root.left);
        Info rightInfo = doIsFBT2(root.right);
        Integer height = Math.max(leftInfo.height,rightInfo.height) + 1;
        Integer nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info(height,nodes);
    }

    private static class Info{
        Integer height;
        Integer nodes;

        public Info(Integer height, Integer nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    /**
     * 与上面方法思路稍有差异 由此亦可以发现 二叉树递归套路收集的信息不唯一
     * @param root
     * @return
     */

    public static boolean isFBT3(BinaryTreeNode root){
        if (root == null) return true;
        return doIsFBT3(root).isFBT;
    }
    private static Info3 doIsFBT3(BinaryTreeNode root) {
        if (root == null) return new Info3(0,true);
        Info3 leftInfo = doIsFBT3(root.left);
        Info3 rightInfo = doIsFBT3(root.right);
        Integer height = Math.max(leftInfo.height,rightInfo.height) + 1;
        boolean isFBT = leftInfo.isFBT && rightInfo.isFBT && leftInfo.height == rightInfo.height;
        return new Info3(height,isFBT);
    }

    private static class Info3{
        Integer height;
        boolean isFBT;

        public Info3(Integer height, boolean isFBT) {
            this.height = height;
            this.isFBT = isFBT;
        }
    }


    /**
     * 层序遍历
     * @param root
     * @return
     */
    public static boolean isFBT4(BinaryTreeNode root){
        if (root == null )return true;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean flag = false;
        while (!queue.isEmpty()){
            if (flag) return false;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                BinaryTreeNode node = queue.poll();
                if(node.left != null && node.right != null){
                    queue.add(node.left);
                    queue.add(node.right);
                }else if (node.left == null && node.right == null){
                    flag = true;
                }else {
                    return false;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) {
//        BinaryTreeNode root = BTUtil.createBinaryTree("531##4##86##9##".toCharArray());
//        TraversalBT.level(root);
//        System.out.println(isFBT(root));
//        System.out.println(isFBT2(root));
//        System.out.println(isFBT3(root));
//        System.out.println(isFBT4(root));
        int maxLevel = 4;
        int maxValue = 100;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            BinaryTreeNode root = BTUtil.generateRandomBT(maxLevel, maxValue);
            if (isFBT(root) != isFBT4(root)){
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
