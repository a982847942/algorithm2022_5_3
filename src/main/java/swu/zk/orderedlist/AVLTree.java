package swu.zk.orderedlist;

import java.util.Comparator;

/**
 * @Classname AVL
 * @Description
 * 使用递归实现AVL树，复杂度控制在LogN，正常情况下不会产生栈溢出。
 * @Date 2022/5/3 16:38
 * @Created by brain
 */
public class AVLTree<K, V> implements OrderList<K, V> {
    private AVLNode<K, V> root;
    private Comparator<K> comparator;
    private int size;

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<K> comparator) {
        this.comparator = comparator;
        root = null;
        size = 0;
    }

    private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
        if (cur == null) return null;
        int leftHeight = cur.left != null ? cur.left.height : 0;
        int rightHeight = cur.right != null ? cur.right.height : 0;
        if (Math.abs(leftHeight - rightHeight) > 1) {
            if (leftHeight > rightHeight) {
                int leftLeftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
                int leftRightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;

                //LL型或LL 、LR型
                if (leftLeftHeight >= leftRightHeight) {
                    cur = rightRotate(cur);
                } else {
                    //LR型
                    cur.left = leftRotate(cur.left);
                    cur = rightRotate(cur);
                }
            } else {
                int rightLeftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
                int rightRightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;

                if (rightLeftHeight > rightRightHeight) {
                    //RL型
                    cur.right = rightRotate(cur.right);
                    cur = leftRotate(cur);
                } else {
                    //RR型 或RR RL型
                    cur = leftRotate(cur);
                }
            }
        }
        return cur;
    }

    private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
        AVLNode<K, V> temp = cur.right;
        cur.right = temp.left;
        temp.left = cur;
        cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
        temp.height = Math.max(temp.left != null ? temp.left.height : 0, temp.right != null ? temp.right.height : 0) + 1;
        return temp;
    }

    private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
        AVLNode<K, V> temp = cur.left;
        cur.left = temp.right;
        temp.right = cur;
        cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
        temp.height = Math.max(temp.left != null ? temp.left.height : 0, temp.right != null ? temp.right.height : 0) + 1;
        return temp;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) return;
        AVLNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && (compare(key, lastNode.key) == 0)) {
            lastNode.value = value;
        } else {
            size++;
            root = add(root, key, value);
        }
    }

    private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
        if (cur == null) {
            return new AVLNode<>(key, value);
        } else {
            int cmp = compare(key, cur.key);
            if (cmp > 0) {
                cur.right = add(cur.right, key, value);
            } else if (cmp < 0) {
                cur.left = add(cur.left, key, value);
            }
            cur.height = Math.max((cur.left != null ? cur.left.height : 0),
                    (cur.right != null ? cur.right.height : 0)) + 1;
            return maintain(cur);
        }
    }

    private AVLNode<K, V> findLastIndex(K key) {
        if (key == null) return null;
        AVLNode<K, V> cur = root;
        AVLNode<K, V> parent = root;
        while (cur != null) {
            parent = cur;
            int cmp = compare(key, cur.key);
            if (cmp == 0) {
                break;
            } else if (cmp > 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return parent;
    }

    @Override
    public void remove(K key) {
        if (key == null) return;
        if (containsKey(key)) {
            size--;
            root = delete(root, key);
        }
    }

    private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
        int cmp = compare(key, cur.key);
        if (cmp > 0) {
            cur.right = delete(cur.right, key);
        } else if (cmp < 0) {
            cur.left = delete(cur.left, key);
        } else {
            if (cur.left == null && cur.right == null) {
                cur = null;
            } else if (cur.left != null && cur.right == null) {
                cur = cur.left;
            } else if (cur.left == null && cur.right != null) {
                cur = cur.right;
            } else {
                //阉割版寻找BST中的找后继结点
                AVLNode<K, V> temp = cur.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                //调整
                cur.right = delete(cur.right, temp.key);
                temp.left = cur.left;
                temp.right = cur.right;
                cur = temp;
            }
        }
        if (cur != null) {
            cur.height = Math.max(cur.left != null ? cur.left.height : 0,
                    cur.right != null ? cur.right.height : 0) + 1;
        }
        return maintain(cur);
    }


    @Override
    public V get(K key) {
        if (key == null) return null;
        AVLNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && compare(key, lastNode.key) == 0) {
            return lastNode.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) return false;
        AVLNode<K, V> lastNode = findLastIndex(key);
        return lastNode != null && compare(key, lastNode.key) == 0;
    }

    @Override
    public K firstKey() {
        if (root == null) return null;
        AVLNode<K,V> cur = root;
        while (cur.left != null){
            cur = cur.left;
        }
        return cur.key;
    }

    @Override
    public K lastKey() {
        if (root == null) return null;
        AVLNode<K,V> cur = root;
        while (cur.right != null){
            cur = cur.right;
        }
        return cur.key;
    }

    @Override
    public K floorKey(K key) {
        if (key == null) {
            return null;
        }
        AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
        return lastNoBigNode == null ? null : lastNoBigNode.key;
    }

    private AVLNode<K,V> findLastNoBigIndex(K key) {
        AVLNode<K,V> cur = root;
        AVLNode<K,V> ans = null;
        while (cur != null){
            int cmp = compare(key, cur.key);
            if (cmp == 0){
                ans = cur;
                break;
            }else if (cmp > 0){
                ans = cur;
                cur = cur.right;
            }else {
                cur = cur.left;
            }
        }
        return ans;
    }

    @Override
    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        AVLNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
        return lastNoSmallNode == null ? null : lastNoSmallNode.key;
    }

    private AVLNode<K, V> findLastNoSmallIndex(K key) {
        AVLNode<K,V> cur = root;
        AVLNode<K,V> ans = null;
        while (cur != null){
            int cmp = compare(key, cur.key);
            if (cmp == 0){
                ans = cur;
                break;
            }else if (cmp > 0){
                cur = cur.right;
            }else {
                ans = cur;
                cur = cur.left;
            }
        }
        return ans;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private int compare(K o1, K o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        }
        return ((Comparable<K>) o1).compareTo(o2);
    }

    static class AVLNode<K, V> {
        K key;
        V value;
        int height;
        AVLNode<K, V> left;
        AVLNode<K, V> right;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        AVLTree<Integer,Integer> map = new AVLTree<>();
        map.put(4,4);
        map.put(5,5);
        map.put(2,2);
        map.put(3,3);
        map.put(1,1);
//        System.out.println(map.get(2));
        map.put(2,10);
        System.out.println(map.get(2));
        System.out.println(map.firstKey());
        System.out.println(map.lastKey());
        System.out.println(map.floorKey(5));
        System.out.println(map.ceilingKey(6));
        map.remove(2);

    }

}
