package swu.zk.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname SerializeAndReconstructTree
 * @Description 序列化与反序列化二叉树
 * 反序列化可以根据先序 后序和层序 来进行，但是中序不可以唯一确定二叉树(即使加上虚节点占位)
 * 如果没有虚节点则先序和中序 后序和中序结合可以唯一确定二叉树  先序和后序结合不可以
 * 2     2
 * |       \
 * 1         1
 * |           \
 * 3            3   先序后序 一样
 * @Date 2022/5/8 10:08
 * @Created by brain
 */
public class SerializeAndReconstructTree {
    /**
     * 先序序列化二叉树
     *
     * @param root
     * @return
     */
    public static Queue<String> preSerial(BinaryTreeNode root) {
        Queue<String> ans = new LinkedList<>();
        doPreSerial(root, ans);
        return ans;
    }

    private static void doPreSerial(BinaryTreeNode root, Queue<String> ans) {
        if (root == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(root.value));
            doPreSerial(root.left, ans);
            doPreSerial(root.right, ans);
        }
    }

    /**
     * 中序序列化二叉树
     *
     * @param root
     * @return
     */
    public static Queue<String> inSerial(BinaryTreeNode root) {
        Queue<String> ans = new LinkedList<>();
        doInSerial(root,ans);
        return ans;
    }

    private static void doInSerial(BinaryTreeNode root, Queue<String> ans) {
        if (root == null){
            ans.add(null);
        }else {
            doInSerial(root.left,ans);
            ans.add(String.valueOf(root.value));
            doInSerial(root.right,ans);
        }
    }

    /**
     * 后序序列化二叉树
     * @param root
     * @return
     */
    public static Queue<String> postSerial(BinaryTreeNode root) {
        Queue<String> ans = new LinkedList<>();
        doPostSerial(root,ans);
        return ans;
    }

    private static void doPostSerial(BinaryTreeNode root, Queue<String> ans) {
        if (root == null){
            ans.add(null);
        }else {
            doPostSerial(root.left,ans);
            doPostSerial(root.right,ans);
            ans.add(String.valueOf(root.value));
        }
    }

    /**
     * 层序序列化二叉树
     * @param root
     * @return
     */
    public static Queue<String> levelSerial(BinaryTreeNode root) {
        Queue<String> ans = new LinkedList<>();
        if (root == null){
            ans.add(null);
        }else {
            ans.add(String.valueOf(root.value));
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()){
                BinaryTreeNode node = queue.poll();
                if (node.left != null){
                    ans.add(String.valueOf(node.left.value));
                    queue.add(node.left);
                }else {
                    ans.add(null);
                }

                if (node.right != null){
                    ans.add(String.valueOf(node.right.value));
                    queue.add(node.right);
                }else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    /**
     * 通过前序序列构建二叉树
     * @param preList
     * @return
     */
    public static BinaryTreeNode buildByPre(Queue<String> preList){
        if (preList == null || preList.size() == 0) return null;
        return doBuildByPre(preList);
    }

    private static BinaryTreeNode doBuildByPre(Queue<String> preList) {
        String nodeValue = preList.poll();
        if (nodeValue == null) return null;
        BinaryTreeNode node = new BinaryTreeNode(Integer.valueOf(nodeValue));
        node.left = doBuildByPre(preList);
        node.right = doBuildByPre(preList);
        return node;
    }

    /**
     * 通过后序序列构建二叉树
     * @param postList
     * @return
     */
    public static BinaryTreeNode buildByPost(Queue<String> postList){
        if (postList == null || postList.size() == 0) return null;
        Deque<String> deque = new LinkedList<>();
        postList.forEach(str ->{
            deque.addLast(str);
        });
       return doBuildByPost(deque);
    }

    private static BinaryTreeNode doBuildByPost(Deque<String> postList) {
        String nodeValue = postList.pollLast();
        if (nodeValue == null) return null;
        BinaryTreeNode node = new BinaryTreeNode(Integer.valueOf(nodeValue));
        node.right = doBuildByPost(postList);
        node.left = doBuildByPost(postList);
        return node;
    }

    /**
     * 通过层序序列构建二叉树
     * @param levelList
     * @return
     */
    public static BinaryTreeNode buildByLevel(Queue<String> levelList){
        if (levelList == null || levelList.size() == 0) return null;
        BinaryTreeNode root = generated(levelList.poll());
        if (root == null) return null;

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            BinaryTreeNode cur = queue.poll();
            cur.left = generated(levelList.poll());
            cur.right = generated(levelList.poll());
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
        return root;
    }

    private static BinaryTreeNode generated(String str) {
        if (str == null) return null;
        return new BinaryTreeNode(Integer.valueOf(str));
    }


    public static void main(String[] args) {
        BinaryTreeNode root = BTUtil.createBinaryTree("123##4##56##7##".toCharArray());
        TraversalBT.level(root);
//        Queue<String> queue1 = preSerial(root);
//        System.out.println(queue1);
//        BinaryTreeNode preRoot = buildByPre(queue1);
//        TraversalBT.level(preRoot);
//        Queue<String> queue2 = inSerial(root);
//        System.out.println(queue2);
//        Queue<String> queue3 = postSerial(root);
//        System.out.println(queue3);
//        BinaryTreeNode postRoot = buildByPost(queue3);
//        TraversalBT.level(postRoot);
        Queue<String> queue4 = levelSerial(root);
        System.out.println(queue4);
        BinaryTreeNode levelRoot = buildByLevel(queue4);
        TraversalBT.level(levelRoot);
    }


}
