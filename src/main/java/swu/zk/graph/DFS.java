package swu.zk.graph;

import swu.zk.graph.util.Graph;
import swu.zk.graph.util.GraphGenerator;
import swu.zk.graph.util.Node;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @Classname DFS
 * @Description
 * 深度优先搜索
 * @Date 2022/5/17 14:28
 * @Created by brain
 */
public class DFS {

    public static void dfs(Node start){
        if (start == null) return;
        Deque<Node> deque = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        deque.push(start);
        visited.add(start);
        System.out.print(start.value + " ");
        while (!deque.isEmpty()){
            Node curNode = deque.pop();
            for (Node nextNode : curNode.nextNodes) {
                if (!visited.contains(nextNode)){
                    deque.push(curNode);
                    deque.push(nextNode);
                    visited.add(nextNode);
                    System.out.print(nextNode.value + " ");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] arr = {
                {2,0,1},{2,1,0},
                {3,0,2},{3,2,0},
                {4,1,2},{4,2,1},
                {6,1,3},{6,3,1},
                {7,2,3},{7,3,2},
        };
        Graph graph = GraphGenerator.createGraph(arr);
        dfs(graph.nodes.get(0));
    }
}
