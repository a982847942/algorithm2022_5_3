package swu.zk.tree;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Forest2BT
 * @Description 树转换为二叉树
 * @Date 2022/5/8 10:09
 * @Created by brain
 */
public class Forest2BT {
    private static class ForestNode{
        int value;
        List<ForestNode> children;

        public ForestNode(int value) {
            this.value = value;
        }

        public ForestNode(int value, List<ForestNode> children) {
            this.value = value;
            this.children = children;
        }
    }


    public static BinaryTreeNode encode(ForestNode root){
        if (root == null) return null;
        BinaryTreeNode result = new BinaryTreeNode(root.value);
        result.left = doEncode(root.children);
        return result;
    }

    private static BinaryTreeNode doEncode(List<ForestNode> children) {
        if (children == null) return null;
        BinaryTreeNode head = null;
        BinaryTreeNode cur = null;
        for (ForestNode child : children) {
            BinaryTreeNode node = new BinaryTreeNode(child.value);
            if (head == null){
                head = node;
            }else {
                cur.right = node;
            }
            cur = node;
            cur.left = doEncode(child.children);
        }
        return head;
    }

    public static ForestNode decode(BinaryTreeNode root){
        if (root == null) return null;
        return new ForestNode(root.value,doDecode(root.left));
    }

    private static List<ForestNode> doDecode(BinaryTreeNode root) {
        List<ForestNode> children = new ArrayList<>();
        while (root != null){
            ForestNode node = new ForestNode(root.value);
            node.children = doDecode(root.left);
            children.add(node);
            root = root.right;
        }
        return children;
    }
}
