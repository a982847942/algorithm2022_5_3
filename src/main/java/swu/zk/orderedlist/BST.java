package swu.zk.orderedlist;

import java.util.Comparator;

/**
 * @Classname BST
 * @Description
 * 二叉搜索树
 * @Date 2022/5/3 16:35
 * @Created by brain
 */
public class BST {
    //二叉搜索树 结点类型
    static class BSTNode<K, V> {
        public K key;
        public V value;
        BSTNode<K, V> left;
        BSTNode<K, V> right;
        BSTNode<K, V> parent;

        public BSTNode(K key, V value, BSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public int getNodeType() {
            if (this.left == null && this.right == null) {
                return 0;
            } else if (this.left != null && this.right == null) {
                return 1;
            } else if (this.left == null && this.right != null) {
                return 2;
            } else {
                return 3;
            }
        }
    }


    public static class BSTree<K, V> {
        private BSTNode<K, V> root;
        private Comparator<K> comparator;
        private int size;

        public BSTree() {
            this(null);
        }

        public BSTree(Comparator<K> comparator) {
            this.comparator = comparator;
            root = null;
            size = 0;
        }

        public void put(K key, V value) {
            //非空检测
            elementNotNullCheck(key);
            //空树
            if (root == null) {
                root = new BSTNode<>(key, value, null);
                size++;
                return;
            }
            BSTNode<K, V> lastNode = findLastIndex(key);
            int cmp = compare(key, lastNode.key);
            if (lastNode != null && cmp == 0) {
                //更新
                lastNode.value = value;
            }
            BSTNode<K, V> newNode = new BSTNode<>(key, value, lastNode);
            if (cmp > 0) {
                lastNode.right = newNode;
            } else if (cmp < 0) {
                lastNode.left = newNode;
            }
            size++;
        }

        public void remove(K key) {
            if (key == null) return;
            BSTNode<K, V> node = findLastIndex(key);
            //判断BST中是否包含该节点
            if (node == null || (compare(key, node.key) != 0)) return;
            if (node.getNodeType() == 3) {
                //两边都有结点 此时可以用右子树最左结点替换 也可以使用左子树最右节点替换
                //同时注意 因为此种情况该node左右都有结点，因此不需要判断successor是否为null 必然不为null
                BSTNode<K, V> successor = successor(node);
                //用后继结点的值覆盖度为2的结点的值
                node.key = successor.key;
                node.value = successor.value;
                //因为度为2的结点的后继或者前驱结点一定是度为1或0，所以将删除结点交给后面的代码来做
                node = successor;
            }
            //获取该节点的类型
            int nodeType = node.getNodeType();
            if (nodeType == 0) {
                if (node == root) {
                    root = null;
                } else {
                    if (node == node.parent.left) {
                        node.parent.left = null;
                    } else {
                        node.parent.right = null;
                    }
                }
            } else if (nodeType == 1 || nodeType == 2) {
                BSTNode<K, V> replace = node.left != null ? node.left : node.right;
                replace.parent = node.parent;
                if (node.parent == null) {
                    root = replace;
                } else if (node == node.parent.left) {
                    node.parent.left = replace;
                } else {
                    node.parent.right = replace;
                }
            }
        }

        /**
         * 找到二叉搜索树中最小的key
         *
         * @return
         */
        public K firstKey() {
            if (root == null) return null;
            BSTNode<K, V> cur = root;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur.key;
        }

        /**
         * 找到二叉搜索树中最大的key
         *
         * @return
         */
        public K lastKey() {
            if (root == null) return null;
            BSTNode<K, V> cur = root;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur.key;
        }

        /**
         * 找到小于等于传入的key的 最大key
         *
         * @param key
         * @return
         */

        public K floorKey(K key) {
            if (key == null) return null;
            BSTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        private BSTNode<K, V> findLastNoBigIndex(K key) {
            if (key == null) return null;
            BSTNode<K, V> cur = root;
            BSTNode<K, V> ans = null;
            int cmp = 0;
            while (cur != null) {
                cmp = compare(key, cur.key);
                if (cmp == 0) {
                    ans = cur;
                    return ans;
                } else if (cmp > 0) {
                    ans = cur;
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return ans;
        }

        /**
         * 找到大于等于传入的key的 最小key
         *
         * @param key
         * @return
         */
        public K ceilingKey(K key) {
            if (key == null) return null;
            BSTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.key;
        }

        private BSTNode<K, V> findLastNoSmallIndex(K key) {
            if (key == null) return null;
            BSTNode<K, V> cur = root;
            BSTNode<K, V> ans = null;
            int cmp = 0;
            while (cur != null) {
                cmp = compare(key, cur.key);
                if (cmp == 0) {
                    ans = cur;
                    return ans;
                } else if (cmp > 0) {
                    cur = cur.right;
                } else {
                    ans = cur;
                    cur = cur.left;
                }
            }
            return ans;
        }

        /**
         * 获取key对应的值 如果没有返回null
         *
         * @param key
         * @return
         */
        public V get(K key) {
            if (key == null) return null;
            BSTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && compare(key, lastNode.key) == 0) {
                return lastNode.value;
            }
            return null;
        }


        /**
         * 找后继结点
         *
         * @param node
         * @return
         */
        private BSTNode<K, V> successor(BSTNode<K, V> node) {
            if (node == null) throw new IllegalArgumentException("node不能为空");
            BSTNode<K, V> cur = node.right;
            //第一种情况 有右子树 在右子树上找最左节点
            if (cur != null) {
                while (cur.left != null) {
                    cur = cur.left;
                }
                return cur;
            }
            //第二种情况 无右子树 通过parent指针查找
            while (node.parent != null && node == node.parent.right) {
                node = node.parent;
            }
            return node.parent;

        }

        /**
         * 找到当前结点的前驱结点
         */
        public BSTNode<K, V> predecessor(BSTNode<K, V> node) {
            if (node == null) throw new IllegalArgumentException("node不能为空");
            //前驱结点在左子树当中(left.right.right.......)
            BSTNode<K, V> p = node.left;
            if (p != null) {
                while (p.right != null) {
                    p = p.right;
                }
                return p;
            }
            //从祖父结点里面找
            while (node.parent != null && node == node.parent.left) {
                node = node.parent;
            }

            /*
             * 这里有两种情况
             *  1. node.parent == null
             *  2. node = node.parent.right;
             */
            return node.parent;
        }

        /**
         * 是否包含指定的key
         *
         * @param key
         * @return
         */
        public boolean containsKey(K key) {
            if (key == null) return false;
            BSTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && compare(key, lastNode.key) == 0;
        }

        //如果有等于key的就返回该节点  没有返回对应key值的最近结点 (这里指的是应该插入的位置的父节点)
        private BSTNode<K, V> findLastIndex(K key) {
            if (key == null) return null;
            BSTNode<K, V> cur = root;
            BSTNode<K, V> parent = root;
            while (cur != null) {
                parent = cur;
                if (compare(key, cur.key) == 0) {
                    break;
                } else if (compare(key, cur.key) > 0) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return parent;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

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

        private void elementNotNullCheck(K key) {
            if (key == null) {
                throw new IllegalArgumentException("key must not be null");
            }
        }

    }

    public static void main(String[] args) {
        BSTree<Integer, Integer> bsTree = new BSTree<>();
        bsTree.put(4, 4);
        bsTree.put(5, 5);
        bsTree.put(2, 2);
        bsTree.put(3, 3);
        bsTree.put(1, 1);
        System.out.println(bsTree.size());
        System.out.println(bsTree.get(1));
//        bsTree.remove(2);
//        System.out.println(bsTree.get(2));
        System.out.println(bsTree.floorKey(6));
        System.out.println(bsTree.ceilingKey(6));
    }
}
