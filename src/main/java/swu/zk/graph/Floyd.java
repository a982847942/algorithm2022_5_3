package swu.zk.graph;

import java.util.Arrays;

/**
 * @Classname Floyd
 * @Description
 * 多源最短路径 floyd算法
 * 基本思想：
 *  任意节点i到j的最短路径两种可能：
 *  动态规划的思想
 *      1.直接从i到j；
 *      2.从i经过若干个节点k到j。
 *      map（i，j）表示节点i到j最短路径的距离，对于每一个节点k，检查map（i，k）+map（k，j）小于map(i，j),
 *      如果成立，map（i，j） = map（i，k）+map（k，j）；
 *      遍历每个k，每次更新的是除第k行和第k列的数
 * @Date 2022/5/17 21:48
 * @Created by brain
 */
public class Floyd {

    public static int[][] floyd(int[][] adjMatrix,int[][] path){
        if (adjMatrix == null || adjMatrix.length == 0 || adjMatrix[0].length == 0 || adjMatrix.length != adjMatrix[0].length) return null;
        int row = adjMatrix.length;
        int column = adjMatrix[0].length;
        int[][] res = new int[row][column];
        for (int i = 0; i < res.length; i++) {
            res[i] = Arrays.copyOf(adjMatrix[i],column);
        }

        for (int k = 0; k < row; k++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < row; j++) {
                    if (res[i][k] != -1 && res[k][j] != -1){
                        if (res[i][j] == -1 || res[i][k] + res[k][j] < res[i][j]){
                            res[i][j] = res[i][k] + res[k][j];
                            path[i][j] = k;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] adjMatrix = {{0,-1,3,-1},
                {2,0,-1,-1},
                {-1,7,0,1},
                {6,-1,-1,0}};
        int[][] path = new int[adjMatrix.length][adjMatrix[0].length];
        int[][] floyd = floyd(adjMatrix, path);
        System.out.println("使用Floyd算法的原始矩阵矩阵为：");
        for(int i = 0;i < adjMatrix.length;i++) {
            for(int j = 0;j < adjMatrix[0].length;j++)
                System.out.print(adjMatrix[i][j]+" ");
            System.out.println();
        }
        System.out.println();
        System.out.println("使用Floyd算法得到的所有顶点之间的最短距离权重矩阵为：");
        for(int i = 0;i < floyd.length;i++) {
            for(int j = 0;j < floyd[0].length;j++)
                System.out.print(floyd[i][j]+" ");
            System.out.println();
        }
        System.out.println();
        System.out.println("使用Floyd算法得到的所有顶点之间的最短距离的路径矩阵为：");
        for(int i = 0;i < path.length;i++) {
            for(int j = 0;j < path[0].length;j++)
                System.out.print(path[i][j]+" ");
            System.out.println();
        }
    }
}
