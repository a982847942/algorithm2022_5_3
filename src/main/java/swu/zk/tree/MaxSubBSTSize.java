package swu.zk.tree;

import java.util.ArrayList;

/**
 * @Classname MaxSubBSTSize
 * @Description 求二叉树中最大二叉搜索子树的大小
 * @Date 2022/5/11 21:52
 * @Created by brain
 */
public class MaxSubBSTSize {


    /**
     * 二叉树 递归套路
     * @param head
     * @return
     */
    public static int maxSubBSTSize2(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSubtreeSize;
    }

    public static class Info2 {
        public int maxBSTSubtreeSize;
        public int allSize;
        public int max;
        public int min;

        public Info2(int m, int a, int ma, int mi) {
            maxBSTSubtreeSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

    public static Info2 process(BinaryTreeNode x) {
        if (x == null) {
            return null;
        }
        Info2 leftInfo = process(x.left);
        Info2 rightInfo = process(x.right);
        int max = x.value;
        int min = x.value;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
            allSize += rightInfo.allSize;
        }
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubtreeSize;
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubtreeSize;
        }
        int p3 = -1;
        boolean leftBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        boolean rightBST = rightInfo == null ? true : (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
        if (leftBST && rightBST) {
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
            boolean rightMinMoreX = rightInfo == null ? true : (x.value < rightInfo.min);
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        return new Info2(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }



    /**
     * 二叉树递归套路  与上面思想一致 只不过判断是否为BST的方式改变
     * @param root
     * @return
     */
    public static Integer maxSubBSTSize(BinaryTreeNode root){
        if (root == null) return 0;
        return doMaxSubBSTSize(root).maxSubBST;
    }

    private static Info doMaxSubBSTSize(BinaryTreeNode root) {
        if (root == null) return null;
        Info leftInfo = doMaxSubBSTSize(root.left);
        Info rightInfo = doMaxSubBSTSize(root.right);
        Integer min = root.value;
        Integer max = root.value;
        Integer maxSubBST = 1;
        boolean isBST = true;
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
        if (leftInfo != null && leftInfo.max >= root.value) isBST = false;
        if (rightInfo != null && rightInfo.min <= root.value) isBST = false;

        if (isBST){
            maxSubBST = leftInfo != null ? maxSubBST + leftInfo.maxSubBST : maxSubBST;
            maxSubBST = rightInfo != null ? maxSubBST + rightInfo.maxSubBST : maxSubBST;
        }else {
            maxSubBST = leftInfo != null ? Math.max(leftInfo.maxSubBST,maxSubBST) : maxSubBST;
            maxSubBST = rightInfo != null ? Math.max(rightInfo.maxSubBST,maxSubBST) : maxSubBST;
        }
        return new Info(min,max,maxSubBST,isBST);
    }

    private static class Info{
        Integer min;
        Integer max;
        Integer maxSubBST;
        boolean isBST;

        public Info(Integer min, Integer max, Integer maxSubBST, boolean isBST) {
            this.min = min;
            this.max = max;
            this.maxSubBST = maxSubBST;
            this.isBST = isBST;
        }
    }


    /**
     * 对每一个结点采用中序遍历的方法 判断是否是BST
     * @param head
     * @return
     */
    public static int maxSubBSTSize1(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }
    public static int getBSTSize(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<BinaryTreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(BinaryTreeNode head, ArrayList<BinaryTreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }




    public static void main(String[] args) {
//        BinaryTreeNode root = BTUtil.createBinaryTree("643##5##2##".toCharArray());
//        System.out.println(maxSubBSTSize(root));

        int maxLevel = 4;
        int maxValue = 100;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            BinaryTreeNode root = BTUtil.generateRandomBT(maxLevel, maxValue);
            if (maxSubBSTSize(root) != maxSubBSTSize2(root)){
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
