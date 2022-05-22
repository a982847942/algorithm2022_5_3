package swu.zk.graph;

import swu.zk.graph.util.Graph;
import swu.zk.graph.util.Node;

import java.util.*;

/**
 * @Classname TopologySort
 * @Description 拓扑排序
 * 基本思路：
 * 首先找到第一个入度为0的结点，将其加入结果集。
 * 然后将它相连的结点入度-1，
 * 重复上述过程，直到没有入度为0的点。
 * 如果拓扑排序能够顺利完成，则这个图一定是无环图。
 * 拓扑排序应用：
 * 编译顺序  事件安排等
 * @Date 2022/5/18 17:23
 * @Created by brain
 */
public class TopologySortByBFS {

    /**
     * BFS
     * @param graph
     * @return
     */
    public static List<Node> topologySort(Graph graph) {
        if (graph == null) return null;
        List<Node> result = new ArrayList<>();
        Queue<Node> zeroQueue = new LinkedList<>();
        HashMap<Node, Integer> inDegree = new HashMap<>();
        graph.nodes.values().forEach(t -> {
            inDegree.put(t, t.in);
            if (t.in == 0) zeroQueue.add(t);
        });

        while (!zeroQueue.isEmpty()) {
            Node curNode = zeroQueue.poll();
            result.add(curNode);
           curNode.nextNodes.forEach(node -> {
               inDegree.put(node,inDegree.get(node) - 1);
               if (inDegree.get(node) == 0) zeroQueue.add(node);
           });
        }
        return result;
    }

}
