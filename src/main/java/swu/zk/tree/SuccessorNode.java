package swu.zk.tree;

/**
 * @Classname SuccessorNode
 * @Description 在二叉树的中序遍历中找到某一个结点的后继结点
 * 中序: 左  中 右
 * 1.该节点无右孩子 则向上找到该节点为父节点的左孩子的第一个结点
 * 2.该节点有右孩子  则找到右孩子为根的二叉树的最左结点
 *
 *该结点结构是一个三叉链表 多了一个parent指针
 * @Date 2022/5/8 10:11
 * @Created by brain
 */
public class SuccessorNode {
    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(int value) {
            this.value = value;
        }
    }


    public static TreeNode getSuccessorNode(TreeNode node){
        if (node == null) return null;
        if (node.right != null){
            return getLeftMostChildren(node.right);
        }else {
            TreeNode parent = node.parent;
            while (parent != null && parent.right == node){
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    private static TreeNode getLeftMostChildren(TreeNode root) {
        if (root == null)return null;
        TreeNode leftMostNode = root;
        while (leftMostNode != null){
            leftMostNode = leftMostNode.left;
        }
        return leftMostNode;
    }
}
