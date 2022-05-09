package swu.zk.tree;

import java.util.Scanner;

/**
 * @Classname BTUtil
 * @Description 工具类
 * @Date 2022/5/8 11:11
 * @Created by brain
 */
public class BTUtil {
    private static final Scanner INPUT = new Scanner(System.in);

    /**
     * 边输入边创建
     * @param root
     * @return
     */
    public static BinaryTreeNode createBinaryTree(BinaryTreeNode root) {
        if (root == null) return null;
//        System.out.print("请输入结点值:");
        String value = INPUT.next();
        if ("#".equals(value)) return null;
        root.value = Integer.valueOf(value);
        root.left = createBinaryTree(new BinaryTreeNode());
        root.right = createBinaryTree(new BinaryTreeNode());
        return root;
    }


    /**
     * 通过数组创建
     */
    private static Integer count = 0;

    public static BinaryTreeNode createBinaryTree(char[] arr) {
        if (arr == null || arr.length == 0) return null;
        BinaryTreeNode node = new BinaryTreeNode();
        if (count < arr.length) {
            if ('#' == (arr[count])){
                count++;
                return null;
            }
            node.value = Integer.valueOf(arr[count++] - '0');
            node.left = createBinaryTree(arr);
            node.right = createBinaryTree(arr);
        }
        return node;
    }

    public static void main(String[] args) {
//        BinaryTreeNode root = new BinaryTreeNode();
//        createBinaryTree(root);
//        System.out.println(root.value + " " + root.left.value + " " + root.right.value);

        BinaryTreeNode root = createBinaryTree(new char[]{'1','2','#','#','3','#','#'});
        System.out.println(root.value + " " + root.left.value + " " + root.right.value);
    }
}
