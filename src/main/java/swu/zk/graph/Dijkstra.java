package swu.zk.graph;

/**
 * @Classname Dijkstra
 * @Description 单源最短路径 dijkstra算法
 * @Date 2022/5/17 21:08
 * @Created by brain
 */
public class Dijkstra {

    /**
     * @param graph
     * 邻接矩阵中-1表示两点不可直接到达
     * 体会prim算法与dijkstra算法的不同之处！！！
     * @return
     */
    public static int[] dijkstra(int[][] graph) {
        if (graph == null || graph.length == 0 || graph[0].length == 0 || graph.length != graph[0].length) return null;
        int len = graph.length;
        int[] result = new int[len];
        boolean[] visited = new boolean[len];
        visited[0] = true;

        for (int i = 0; i < len; i++) {
            result[i] = graph[0][i];
        }

        for (int i = 1; i < len; i++) {

            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < len; j++) {
                if (!visited[j] && result[j] != - 1 && result[j] < minDistance){
                    minDistance = result[j];
                    minIndex = j;
                }
            }

            visited[minIndex] = true;
            //更新result数组 这里是prim与dijkstra算法关键不同之处
            for (int j = 1; j < len; j++) {
                if (!visited[j]){
                    if (graph[minIndex][j] != -1 && ((result[j] > (graph[minIndex][j] + result[minIndex])) || result[j] == -1)){
                        result[j] = graph[minIndex][j] + result[minIndex];
                    }
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        int[][] adjMatrix = {{0,6,3,-1,-1,-1},
                {6,0,2,5,-1,-1},
                {3,2,0,3,4,-1},
                {-1,5,3,0,2,3},
                {-1,-1,4,2,0,5},
                {-1,-1,-1,3,5,0}};
        int[] r = dijkstra(adjMatrix);
        System.out.println("顶点0到图中所有顶点之间的最短距离为：");
        System.out.println();
        for(int i = 0;i < r.length;i++)
            System.out.print(r[i]+" ");
    }


}
