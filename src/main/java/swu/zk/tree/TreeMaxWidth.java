package swu.zk.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @Classname TreeMaxWidth
 * @Description 求二叉树最宽一层有多少个结点
 * @Date 2022/5/8 10:09
 * @Created by brain
 */
public class TreeMaxWidth {

    /**
     * 层序遍历 使用Map记录level
     *
     * @param root
     * @return
     */
    public static int maxWidth(BinaryTreeNode root) {
        if (root == null) return 0;
        int curWidth = 0;
        int maxWidth = 0;
        int curLevel = 1;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        Map<BinaryTreeNode, Integer> levelMap = new HashMap<>();
        queue.add(root);
        levelMap.put(root, 1);
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();

            if (levelMap.get(node) != curLevel) {
                curLevel++;
                maxWidth = curWidth > maxWidth ? curWidth : maxWidth;
                curWidth = 0;
            }

            if (node.left != null) {
                queue.add(node.left);
                levelMap.put(node.left, curLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                levelMap.put(node.right, curLevel + 1);
            }
            curWidth++;
        }
        maxWidth = curWidth > maxWidth ? curWidth : maxWidth;
        return maxWidth;
    }

    /**
     * 层序遍历  不使用map
     *使用curEnd和nextEnd指针 代替map 节省空间
     * @param root
     * @return
     */
    public static int maxWidth2(BinaryTreeNode root) {
        if (root == null) return 0;
        BinaryTreeNode curEnd = root;
        BinaryTreeNode nextEnd = root;
        int maxWidth = 0;
        int curWidth = 0;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            BinaryTreeNode node = queue.poll();
            if (node.left != null){
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null){
                queue.add(node.right);
                nextEnd = node.right;
            }

            curWidth++;
            if (node == curEnd){
                maxWidth = curWidth > maxWidth ? curWidth : maxWidth;
                curEnd = nextEnd;
                curWidth = 0;
            }
        }
        maxWidth = curWidth > maxWidth ? curWidth :maxWidth;
        return maxWidth;
    }



    public static void main(String[] args) {
//        BinaryTreeNode root = BTUtil.createBinaryTree("1238###4##56##7##".toCharArray());
//        TraversalBT.level(root);
//        System.out.println(maxWidth(root));
//        System.out.println(maxWidth2(root));

        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = BTUtil.generateRandomBT(maxLevel, maxValue);
            if (maxWidth(head) != maxWidth2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
