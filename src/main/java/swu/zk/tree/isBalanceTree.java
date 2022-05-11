package swu.zk.tree;

/**
 * @Classname isBalanceTree
 * @Description 判断一棵二叉树是否是平衡二叉树
 * 任何一个节点的左子树或者右子树都是「平衡二叉树」（左右高度差小于等于 1）
 * 注意 平衡二叉树的定义并未说平衡二叉树一定是二叉排序树
 * @Date 2022/5/11 20:29
 * @Created by brain
 */
public class isBalanceTree {

    public static boolean isBalanceTree(BinaryTreeNode root){
        if (root == null) return true;
        return doIsBalanceTree(root).isBalanceTree;
    }

    private static Info doIsBalanceTree(BinaryTreeNode root) {
        if (root == null) return new Info(true,0);
        Info leftInfo = doIsBalanceTree(root.left);
        Info rightInfo = doIsBalanceTree(root.right);
        Integer height = Math.max(leftInfo.height,rightInfo.height) + 1;
        boolean isBalanceTree = leftInfo.isBalanceTree && rightInfo.isBalanceTree &&
                (Math.abs((leftInfo.height - rightInfo.height)) <= 1);
        return new Info(isBalanceTree,height);
    }

    private static class Info{
        boolean isBalanceTree;
        int height;

        public Info(boolean isBalanceTree, int height) {
            this.isBalanceTree = isBalanceTree;
            this.height = height;
        }
    }



    public static void main(String[] args) {
        BinaryTreeNode root = BTUtil.createBinaryTree("531##4##86##9##".toCharArray());
        TraversalBT.level(root);
        System.out.println(isBalanceTree(root));
    }
}
