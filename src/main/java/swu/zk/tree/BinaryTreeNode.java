package swu.zk.tree;

/**
 * @Classname BinaryTree
 * @Description 二叉树结点类型
 * @Date 2022/5/8 10:12
 * @Created by brain
 */
public class BinaryTreeNode {
    BinaryTreeNode left;
    BinaryTreeNode right;
    int value;

    public BinaryTreeNode(int value) {
        this.value = value;
    }

    public BinaryTreeNode() {
    }
}
