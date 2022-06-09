package swu.zk.orderedlist;

import java.util.Comparator;

/**
 * @Classname SizeBalanceTree
 * @Description
 * @Date 2022/5/3 16:39
 * @Created by brain
 */
public class SizeBalanceTree<K, V> implements OrderList<K, V> {
    SBTNode<K, V> root;
    Comparator<K> comparator;
    int size;

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        SBTNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && compare(key, lastNode.key) == 0) {
            lastNode.value = value;
        } else {
            root = add(root, key, value);
        }
    }

    private SBTNode<K, V> findLastIndex(K key) {
        if (key == null) return null;
        SBTNode<K, V> cur = root;
        SBTNode<K, V> parent = root;
        while (cur != null) {
            parent = cur;
            int cmp = compare(key, cur.key);
            if (cmp > 0) {
                cur = cur.right;
            } else if (cmp < 0) {
                cur = cur.left;
            } else {
                break;
            }
        }
        return parent;
    }

    private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
        if (cur == null) {
            return new SBTNode<>(key, value);
        } else {
            int cmp = compare(key, cur.key);
            cur.size++;
            if (cmp > 0) {
                cur.right = add(cur.right, key, value);
            } else if (cmp < 0) {
                cur.left = add(cur.left, key, value);
            }
            return maintain(cur);
        }
    }

    private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
        if (cur == null) return null;
        int leftSize = cur.left != null ? cur.left.size : 0;
        int rightSize = cur.right != null ? cur.right.size : 0;
        int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
        int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
        int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
        int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
        if (leftLeftSize > rightSize) {
            //LL
            cur = rightRotate(cur);
            cur.right = maintain(cur.right);
            cur = maintain(cur);
        } else if (leftRightSize > rightSize) {
            //LR
            cur.left = leftRotate(cur.left);
            cur = rightRotate(cur);
            cur.left = maintain(cur.left);
            cur.right = maintain(cur.right);
            cur = maintain(cur);
        } else if (rightLeftSize > leftSize) {
            //RL
            cur.right = rightRotate(cur.right);
            cur = leftRotate(cur);
            cur.left = maintain(cur.left);
            cur.right = maintain(cur.right);
            cur = maintain(cur);
        } else if (rightRightSize > leftSize) {
            //RR
            cur = leftRotate(cur);
            cur.left = maintain(cur.left);
            cur = maintain(cur);
        }
        return cur;
    }

    private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
        SBTNode<K, V> temp = cur.right;
        cur.right = temp.left;
        temp.left = cur;
        temp.size = cur.size;
        cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
        return temp;
    }

    private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
        SBTNode<K, V> temp = cur.left;
        cur.left = temp.right;
        temp.right = cur;
        temp.size = cur.size;
        cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
        return temp;
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        if (containsKey(key)) {
            root = delete(root, key);
        }
    }

    private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
        cur.size--;
        int cmp = compare(key, cur.key);
        if (cmp > 0) {
            cur.right = delete(cur.right, key);
        } else if (cmp < 0) {
            cur.left = delete(cur.left, key);
        } else { // 当前要删掉cur
            if (cur.left == null && cur.right == null) {
                // free cur memory -> C++
                cur = null;
            } else if (cur.left == null && cur.right != null) {
                // free cur memory -> C++
                cur = cur.right;
            } else if (cur.left != null && cur.right == null) {
                // free cur memory -> C++
                cur = cur.left;
            } else { // 有左有右
                SBTNode<K, V> pre = null;
                SBTNode<K, V> des = cur.right;
                des.size--;
                while (des.left != null) {
                    pre = des;
                    des = des.left;
                    des.size--;
                }
                if (pre != null) {
                    pre.left = des.right;
                    des.right = cur.right;
                }
                des.left = cur.left;
                des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                // free cur memory -> C++
                cur = des;
            }
        }
        // cur = maintain(cur);
        return cur;
    }

    @Override
    public V get(K key) {
        if (key == null) return null;
        SBTNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && compare(key, lastNode.key) == 0) {
            return lastNode.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) return false;
        SBTNode<K, V> lastNode = findLastIndex(key);
        return lastNode != null && (compare(key, lastNode.key) == 0);
    }

    @Override
    public K firstKey() {
        if (root == null) return null;
        SBTNode<K, V> cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    @Override
    public K lastKey() {
        if (root == null) return null;
        SBTNode<K, V> cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }

    public K getIndexKey(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter.");
        }
        return getIndex(root, index + 1).key;
    }
    private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
        if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
            return cur;
        } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
            return getIndex(cur.left, kth);
        } else {
            return getIndex(cur.right, kth - (cur.left != null ? cur.left.size : 0) - 1);
        }
    }

    public V getIndexValue(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter.");
        }
        return getIndex(root, index + 1).value;
    }

    @Override
    public K floorKey(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        SBTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
        return lastNoBigNode == null ? null : lastNoBigNode.key;
    }

    private SBTNode<K, V> findLastNoBigIndex(K key) {
        SBTNode<K, V> ans = null;
        SBTNode<K, V> cur = root;
        while (cur != null) {
            int cmp = compare(key, cur.key);
            if (cmp == 0) {
                ans = cur;
                break;
            } else if (cmp < 0) {
                cur = cur.left;
            } else {
                ans = cur;
                cur = cur.right;
            }
        }
        return ans;
    }

    @Override
    public K ceilingKey(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        SBTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
        return lastNoSmallNode == null ? null : lastNoSmallNode.key;
    }

    private SBTNode<K, V> findLastNoSmallIndex(K key) {
        SBTNode<K, V> ans = null;
        SBTNode<K, V> cur = root;
        while (cur != null) {
            int cmp = compare(key, cur.key);
            if (cmp == 0) {
                ans = cur;
                break;
            } else if (cmp < 0) {
                ans = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
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

    static class SBTNode<K, V> {
        K key;
        V value;
        SBTNode<K, V> left;
        SBTNode<K, V> right;
        int size;

        public SBTNode(K key, V value) {
            this.key = key;
            this.value = value;
            size = 1;
        }
    }
}
