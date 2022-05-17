package swu.zk.graph;

import swu.zk.graph.util.Edge;
import swu.zk.graph.util.Graph;
import swu.zk.graph.util.GraphGenerator;
import swu.zk.graph.util.Node;

import java.util.*;

/**
 * @Classname Kruskal
 * @Description
 * 最小生成树算法 kruskal
 * 基本思路：
 * 对所有边进行排序(小根堆)，按权重顺序依次弹出，但不能构成环
 * 判断是否成环：并查集  初始时每个节点都当做一个树  每加入一个边则将两个结点合并 每次加入边时都判断isSameSet
 * @Date 2022/5/17 19:18
 * @Created by brain
 */
public class Kruskal {


    private static class UnionFind{
        public Map<Node,Node> parent;
        public Map<Node,Integer> sizeMap;

        public UnionFind() {
            parent = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes){
            nodes.forEach(t->{
                parent.put(t,t);
                sizeMap.put(t,1);
            });
        }

        public boolean isSameSet(Node a,Node b){
            return findParent(a) == findParent(b);
        }

        private Node findParent(Node cur) {
            Deque<Node> deque = new LinkedList<>();
            while (cur != parent.get(cur)){
                deque.push(cur);
                cur = parent.get(cur);
            }

            while (!deque.isEmpty()){
                parent.put(deque.poll(),cur);
            }
            return cur;
        }

        public void union(Node a,Node b){
            Node aParent = findParent(a);
            Node bParent = findParent(b);
            if (aParent != bParent){
                Integer aSize = sizeMap.get(aParent);
                Integer bSize = sizeMap.get(bParent);
                Node bigNode = aSize >= bSize ? aParent : bParent;
                Node smallNode = bigNode == aParent ? bParent : aParent;
                parent.put(smallNode,bigNode);
                sizeMap.put(bigNode,aSize + bSize);
                sizeMap.remove(smallNode);
            }
        }
    }

    /**
     * 复杂度ElogE
     * @param graph
     * @return
     */
    public static List<Edge> kruskal(Graph graph){
        if (graph == null)return null;
        List<Edge> result = new ArrayList<>();
        //小根堆 对边进行排序 O(ElogE)
        PriorityQueue<Edge> edges = new PriorityQueue<>((Edge o1,Edge o2)->{
            return o1.weight - o2.weight;
        });
        graph.edges.forEach(t->edges.add(t));

        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());

        while (!edges.isEmpty()){//O(E)
            Edge curEdge = edges.poll();//O(logE)
            //阿克曼函数 近似常数
            if (!unionFind.isSameSet(curEdge.from,curEdge.to)){
                result.add(curEdge);
                unionFind.union(curEdge.from,curEdge.to);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] arr = {
                {2, 0, 1}, {2, 1, 0},
                {3, 0, 2}, {3, 2, 0},
                {4, 1, 2}, {4, 2, 1},
                {6, 1, 3}, {6, 3, 1},
                {7, 2, 3}, {7, 3, 2},
        };
        Graph graph = GraphGenerator.createGraph(arr);
        List<Edge> kruskal = kruskal(graph);
        kruskal.forEach((edge) -> {
            System.out.print(edge.weight + " ");
        });
    }
}
