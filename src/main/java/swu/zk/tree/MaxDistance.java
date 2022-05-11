package swu.zk.tree;

/**
 * @Classname MaxDistance
 * @Description 给定一棵二叉树 找到二叉树中两个结点之间的最大距离
 * 1.在左子树出现
 * 2.在右子树出现
 * 3.最大距离结点包含左右两个子树
 * @Date 2022/5/11 20:58
 * @Created by brain
 */
public class MaxDistance {

    /**
     * 二叉树的递归套路
     * @param root
     * @return
     */
    public static Integer maxDistance(BinaryTreeNode root){
        if (root == null) return 0;
        return doMaxDistance(root).maxDistance;
    }

    private static Info doMaxDistance(BinaryTreeNode root) {
        if (root == null)return new Info(0,0);
        Info leftInfo = doMaxDistance(root.left);
        Info rightInfo = doMaxDistance(root.right);

        Integer height = Math.max(leftInfo.height,rightInfo.height) + 1;
        Integer maxDistance = Math.max(Math.max(leftInfo.maxDistance,rightInfo.maxDistance),leftInfo.height + rightInfo.height + 1);
        return new Info(height,maxDistance);
    }

    private static class Info{
        Integer height;
        Integer maxDistance;

        public Info(Integer height, Integer maxDistance) {
            this.height = height;
            this.maxDistance = maxDistance;
        }
    }

    public static void main(String[] args) {

    }
}
