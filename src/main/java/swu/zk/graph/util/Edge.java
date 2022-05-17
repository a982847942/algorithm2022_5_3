package swu.zk.graph.util;

/**
 * @Classname Edge
 * @Description
 * 边结构
 * @Date 2022/5/17 10:19
 * @Created by brain
 */
public class Edge {
    //权重
    public int weight;
    //弧头
    public Node from;
    //弧尾
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
