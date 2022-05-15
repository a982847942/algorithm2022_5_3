package swu.zk.unionset;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname FindCircleNum
 * @Description https://leetcode.cn/problems/number-of-provinces/
 * @Date 2022/5/14 10:31
 * @Created by brain
 */
public class FindCircleNum {

    /**
     * 深度优先搜索
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0 || isConnected[0].length == 0) return 0;
        boolean isVisited[] = new boolean[isConnected.length];
        Integer count = 0;
        for (int i = 0; i < isVisited.length; i++) {
            if (!isVisited[i]) {
                count++;
                dfs(i, isConnected, isVisited);
            }
        }
        return count;
    }

    private void dfs(int i, int[][] isConnected, boolean[] isVisited) {
        isVisited[i] = true;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !isVisited[j]) {
                dfs(j, isConnected, isVisited);
            }
        }
    }

    /**
     * BFS
     * @param isConnected
     * @return
     */
    public int findCircleNum2(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0 || isConnected[0].length == 0) return 0;
        boolean isVisited[] = new boolean[isConnected.length];
        Queue<Integer> queue = new LinkedList<>();
        int result = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (!isVisited[i]) {
                result++;
                isVisited[i] = true;
                queue.add(i);
                while (!queue.isEmpty()) {
                    Integer node = queue.poll();
                    for (int j = 0; j < isConnected.length; j++) {
                        if (isConnected[i][j] == 1 && !isVisited[j]) {
                            queue.offer(j);
                            isVisited[j] = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 并查集
     * @param isConnected
     * @return
     */
    public int findCircleNum3(int[][] isConnected) {
        Union union = new Union(isConnected.length);
        for (int i = 0; i < isConnected.length; i++) {
            for (int j = i + 1; j < isConnected.length; j++) {
                if (isConnected[i][j] == 1){
                    union.union(i,j);
                }
            }
        }
        return union.sets;
    }

    private class Union{
        int[] help;
        int[] size;
        int[] parent;
        int sets;

        public Union(int n) {
            sets = n;
            help = new int[n];
            size = new int[n];
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                size[i] = i;
                parent[i] = i;
            }
        }

        public boolean isSameSet(int a,int b){
            return findParent(a) == findParent(b);
        }

        private Integer findParent(int node) {
            Integer hi = 0;
            while (node != parent[node]){
                help[hi++] = node;
                node = parent[node];
            }
            hi--;
            while (hi >= 0){
              parent[help[hi--]]  = node;
            }
            return node;
        }

        public void union(int a,int b){
            Integer aParent = findParent(a);
            Integer bParent = findParent(b);
            if (aParent != bParent){
                int aSize = size[a];
                int bSize = size[b];
                Integer bigNode = aSize >= bSize ? aParent : bParent;
                Integer smallNode = bigNode == aParent ? bParent : aParent;
                size[bigNode] = aSize + bSize;
                size[smallNode] = 0;
                sets--;
            }
        }
        public Integer sets(){
            return sets;
        }
    }

}
