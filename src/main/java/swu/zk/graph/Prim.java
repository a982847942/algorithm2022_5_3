package swu.zk.graph;

import swu.zk.graph.util.Edge;
import swu.zk.graph.util.Graph;
import swu.zk.graph.util.GraphGenerator;
import swu.zk.graph.util.Node;

import java.util.*;

/**
 * @Classname Prim
 * @Description
 * 最小生成树 prim算法
 * @Date 2022/5/17 15:47
 * @Created by brain
 */
public class Prim {

    /**
     * @param graph
     * @return 返回结果是一次添加的边集
     */
    public static List<Edge> primMST(Graph graph){
        if (graph == null) return null;
        List<Edge> result = new ArrayList<>();
        Set<Node> nodeSet  = new HashSet<>();
        //记录可以使用的边(优先级队列排序)
        PriorityQueue<Edge> edges = new PriorityQueue<>(
                (Edge o1,Edge o2)->{
                     return o1.weight - o2.weight;
                }
        );
        //随机选择一个结点开始
        for (Node startNode : graph.nodes.values()) {

            if (!nodeSet.contains(startNode)){
                nodeSet.add(startNode);
                startNode.edges.forEach(t -> edges.add(t));
            }

            while (!edges.isEmpty()){
                Edge curEdges = edges.poll();
                Node toNode = curEdges.to;
                if (!nodeSet.contains(toNode)){
                    result.add(curEdges);
                    nodeSet.add(toNode);
                    toNode.edges.forEach(t->edges.add(t));
                }
            }
            //如果给定的是一个连通图 则break没有太大用处
            //如果给定的是一个不连通的森林 则可能返回的是一个生成森林
            break;
        }
        return result;
    }


    // 请保证graph是连通图且graph是用邻接矩阵表示
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static Integer prim(int[][] graph) {
        if (graph == null || graph.length == 0 || graph[0].length == 0 || graph.length != graph[0].length) return null;
        int len = graph.length;
        int[] distance = new int[len];
        boolean[] visited = new boolean[len];

        visited[0] = true;
        for (int i = 0; i < len; i++) {
            distance[i] = graph[0][i];
        }
        int sum = distance[0];

        //一次加入一个结点 共需要n次
        for (int i = 1; i < len; i++) {
            int minIndex = -1;
            int minDistance = Integer.MAX_VALUE;
            //选取可用的权重最小的边
            for (int j = 0; j < len; j++) {
                if (!visited[j] && distance[j] < minDistance){
                    minDistance = distance[j];
                    minIndex = j;
                }
            }
            //如果minIndex不变 则证明都已经遍历过或者是不连通！
            if (minIndex == -1) return sum;

            sum += minDistance;
            visited[minIndex] = true;

            //根据选择的新结点 更新distance数组
            for (int j = 0; j < len; j++) {
                if (!visited[j] && distance[j] > graph[minIndex][j]){
                    distance[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
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
        List<Edge> primMST = primMST(graph);
        primMST.forEach((edge) -> {
            System.out.print(edge.weight + " ");
        });
    }


}
