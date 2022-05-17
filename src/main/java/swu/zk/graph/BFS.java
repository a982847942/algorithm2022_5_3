package swu.zk.graph;

import swu.zk.graph.util.Graph;
import swu.zk.graph.util.GraphGenerator;
import swu.zk.graph.util.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname BFS
 * @Description
 * 广度优先搜素
 * @Date 2022/5/17 14:21
 * @Created by brain
 */
public class BFS {

    public static void bfs(Node start){
        if (start == null) return;
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()){
            Node curNode = queue.poll();
            System.out.print(curNode.value + " ");
            for (Node nextNode : curNode.nextNodes) {
                if (!visited.contains(nextNode)){
                    queue.add(nextNode);
                    visited.add(nextNode);
                }
            }
        }
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
        bfs(graph.nodes.get(3));
    }
}
