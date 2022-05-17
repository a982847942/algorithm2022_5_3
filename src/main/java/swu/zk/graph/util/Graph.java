package swu.zk.graph.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Classname Graph
 * @Description
 * 图结构
 * @Date 2022/5/17 10:19
 * @Created by brain
 */
public class Graph {
    //点集
    public HashMap<Integer,Node> nodes;
    //边集
    public  HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
