package swu.zk.unionset;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname UnionSet
 * @Description 并查集
 * 并查集是一种类似于树的结构
 * 实现过程中添加了一些优化
 * 1.路径压缩
 * 2.按秩排序
 * @Date 2022/5/11 21:56
 * @Created by brain
 */
public class UnionSet {


    private static class Node<V>{
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    private static class Union<V>{
        HashMap<V,Node<V>> nodes;
        HashMap<Node<V>,Integer> sizeMap;
        HashMap<Node<V>,Node<V>> parent;

        public Union(List<V> list) {
            nodes = new HashMap<>();
            sizeMap = new HashMap<>();
            parent = new HashMap<>();
            for (V value : list) {
                Node<V> node = new Node<>(value);
                nodes.put(value,node);
                parent.put(node,node);
                sizeMap.put(node,1);
            }
        }

        public boolean isSameSet(V node1,V node2){
            Node<V> cur1 = nodes.get(node1);
            Node<V> cur2 = nodes.get(node2);
            if (cur1 == null || cur2 == null) return false;
            return findParent(cur1) == findParent(cur2);
        }

        private Node<V> findParent(Node<V> node) {
            //路径压缩  将树变的扁平
            Deque<Node<V>> deque = new LinkedList<>();
            while (node != parent.get(node)){
                deque.push(node);
                node = parent.get(node);
            }

            while (!deque.isEmpty()){
                parent.put(deque.poll(),node);
            }
            return node;
        }

        public void union(V node1,V node2){
            Node<V> cur1 = nodes.get(node1);
            Node<V> cur2 = nodes.get(node2);
            if (cur1 == null || cur2 == null) return;
            Node<V> parent1 = findParent(cur1);
            Node<V> parent2 = findParent(cur2);
            if (parent1 != parent2){
                Integer size1 = sizeMap.get(parent1);
                Integer size2 = sizeMap.get(parent2);
                Node<V> bigNode = size1 >= size2 ? cur1 : cur2;
                Node<V> smallNode = bigNode == cur1 ? cur2 : cur1;
                //按秩排序
                parent.put(smallNode,bigNode);
                sizeMap.put(bigNode,size1 + size2);
                sizeMap.remove(smallNode);
            }
        }

        public Integer sets(){
            return sizeMap.size();
        }
    }


}
