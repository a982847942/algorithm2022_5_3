package swu.zk.orderedlist;

/**
 * @Classname OrderList
 * @Description
 * "有序表"接口，这里的有序表是自己的叫法，并不符合有序表的定义。。
 * 具体实现BST  AVL SBT 234树 红黑树 跳表等都可以叫有序表
 * @Date 2022/6/7 20:29
 * @Created by brain
 */
public interface OrderList<K,V> {
    /**
     * 添加一个key value对
     * @param key
     * @param value
     */
    public void put(K key, V value) ;

    /**
     * 根据指定的key移除对应的结点
     * @param key
     */
    public void remove(K key);
    /**
     * 获取key对应的值 如果没有返回null
     * @param key
     * @return
     */
    public V get(K key) ;
    /**
     * 是否包含指定的key
     * @param key
     * @return
     */
    public boolean containsKey(K key) ;
    /**
     * 找到二叉搜索树中最小的key
     * @return
     */
    public K firstKey() ;
    /**
     * 找到二叉搜索树中最大的key
     * @return
     */
    public K lastKey() ;
    /**
     * 找到小于等于传入的key的 最大key
     * @param key
     * @return
     */
    public K floorKey(K key) ;
    /**
     * 找到大于等于传入的key的 最小key
     * @param key
     * @return
     */
    public K ceilingKey(K key) ;

    /**
     * 元素个数
     * @return
     */
    public int size() ;

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty() ;

    /**
     * 清空有序表
     */
    public void clear() ;
}
