package swu.zk.graph.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Node
 * @Description
 * 点结构
 * @Date 2022/5/17 10:18
 * @Created by brain
 */
public class Node {
    //点的值
    public int value;
    //入度
    public int in;
    //出度
    public int out;
    //邻接点
    public List<Node> nextNodes;
    //边集
    public  List<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nextNodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
