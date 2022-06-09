package swu.zk.orderedlist;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @Classname SkipList
 * @Description 跳表
 * @Date 2022/5/3 16:35
 * @Created by brain
 */
public class SkipList<K, V> implements OrderList<K, V> {
    static class SkipListNode<K, V> {
        K key;
        V value;
        ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            nextNodes = new ArrayList<>();
        }
    }

    private SkipListNode<K, V> head;//跳表头结点
    private int size;//跳表中元素个数
    private static final double PROBABILITY = 0.5; // < 0.5 继续做，>=0.5 停
    private int maxLevel;//头节点必须拥有最大层数  跳表最大层数
    private Comparator<K> comparator;

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        head = new SkipListNode<>(null, null);
        head.nextNodes.add(null); // 0
        size = 0;
        maxLevel = 0;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) return;
        //得到第0层 小于当前key的最有一个节点
        SkipListNode<K, V> less = mostRightLessNodeInTree(key);
        //任意一个结点在 第0层 都有相应位置 因此nextnodes.get(0)就是为了判断 当前key是否已经存在
        SkipListNode<K, V> find = less.nextNodes.get(0);
        if (find != null && isKeyEqual(key,find.key)){
            find.value = value;
        }else {
            size++;
            int newLevel = 0;
            while (Math.random() > PROBABILITY){
                newLevel++;
            }

            //有可能newLevel提升了整体高度，因此需要判断头结点是否需要增加高度
            while (newLevel > maxLevel){
                head.nextNodes.add(null);
                maxLevel++;
            }

            SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
            for (int i = 0; i <= newLevel; i++) {
                newNode.nextNodes.add(null);
            }

            //这里从最高层开始查找 并设置新结点每层相应的值
            int level = maxLevel;
            SkipListNode<K, V> pre = this.head;
            while (level >= 0){
                pre = mostRightLessNodeInLevel(key, pre, level);
                //有可能新结点层数没有 maxlevel大  必须要判断
                if (level <= newLevel){
                    newNode.nextNodes.set(level,pre.nextNodes.get(level));
                    pre.nextNodes.set(level,newNode);
                }
                level--;
            }
        }
    }

    private SkipListNode<K,V> mostRightLessNodeInLevel(K key, SkipListNode<K,V> pre, int level) {
        SkipListNode<K, V> next = pre.nextNodes.get(level);
        while (next != null && isKeyLess(next.key,key)){
            pre = next;
            next = pre.nextNodes.get(level);
        }
        return pre;
    }

    /**
     * 查找是并没有从第0层顺序查找，而是充分利用跳表结构 从顶层向下查找，复杂度logN
     * @param key
     * @return
     */
    private SkipListNode<K,V> mostRightLessNodeInTree(K key) {
        if (key == null) return null;
        int level = maxLevel;
        SkipListNode<K, V> pre = this.head;
        while (level >= 0){
            pre = mostRightLessNodeInLevel(key,pre,level--);
        }
        return pre;
    }

    @Override
    public void remove(K key) {
        if (containsKey(key)){
            size--;
            int level = maxLevel;
            SkipListNode<K, V> pre = this.head;
            while (level >= 0){

                pre = mostRightLessNodeInLevel(key, pre, level);
                SkipListNode<K, V> next = pre.nextNodes.get(level);
                if (next != null && isKeyEqual(key,next.key)){
                    pre.nextNodes.set(level,next.nextNodes.get(level));

                }

                if (level != 0 && pre == head && pre.nextNodes.get(level) == null){
                    head.nextNodes.remove(level);
                    maxLevel--;
                }

                level--;
            }
        }
    }

    @Override
    public V get(K key) {
        if (key == null) return null;
        SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
        SkipListNode<K, V> find = pre.nextNodes.get(0);
        return find != null && isKeyEqual(key,find.key) ? find.value : null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) return false;
        SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
        SkipListNode<K, V> find = pre.nextNodes.get(0);
        return find != null && isKeyEqual(key,find.key);
    }

    @Override
    public K firstKey() {
        SkipListNode<K, V> res = head.nextNodes.get(0);
        return res != null ? res.key : null;
    }

    @Override
    public K lastKey() {
        SkipListNode<K, V> cur = this.head;
        int level = maxLevel;
        while (level >= 0){
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null){
                cur = next;
                next = cur.nextNodes.get(level);
            }
            level--;
        }
        return null;
    }

    @Override
    public K floorKey(K key) {
        if (key == null) {
            return null;
        }
        SkipListNode<K, V> less = mostRightLessNodeInTree(key);
        SkipListNode<K, V> next = less.nextNodes.get(0);
        return next != null && isKeyEqual(key,next.key) ? next.key : less.key;
    }

    @Override
    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        SkipListNode<K, V> less = mostRightLessNodeInTree(key);
        SkipListNode<K, V> next = less.nextNodes.get(0);
        return next != null ? next.key : null;
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
        head = null;
        size = 0;
        maxLevel = 0;
        comparator = null;
    }

    /**
     * 跳表的头节点值为null 代表比最小值还小
     *
     * @param otherKey
     * @return
     */
    public boolean isKeyLess(K curKey, K otherKey) {
        return otherKey != null && (curKey == null || compare(curKey, otherKey) < 0);
    }

    public boolean isKeyEqual(K curKey, K otherKey) {
        return (curKey == null && otherKey == null) || (curKey != null && otherKey != null
                && (compare(curKey, otherKey) == 0));
    }

    private int compare(K o1, K o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        }
        return ((Comparable<K>) o1).compareTo(o2);
    }
}
