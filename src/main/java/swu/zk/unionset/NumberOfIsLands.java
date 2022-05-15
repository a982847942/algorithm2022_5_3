package swu.zk.unionset;

import java.util.List;

/**
 * @Classname NumberOfIsLands
 * @Description https://leetcode.com/problems/number-of-islands/
 * @Date 2022/5/15 19:33
 * @Created by brain
 */
public class NumberOfIsLands {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        Integer result = 0;
        boolean visited[][] = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    result++;
                    dfs(grid, visited, i, j);
                }
            }
        }
        return result;
    }

    private void dfs(char[][] grid, boolean[][] visited, int row, int column) {
        if (row >= grid.length || column >= grid[0].length || row < 0 || column < 0
        || visited[row][column] == true || grid[row][column] == '0') return;
        visited[row][column] = true;
        dfs(grid, visited, row + 1, column);
        dfs(grid, visited, row - 1, column);
        dfs(grid, visited, row, column - 1);
        dfs(grid, visited, row, column + 1);
    }


    /**
     * 并查集
     * @param grid
     * @return
     */
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int row = grid.length;
        int col = grid[0].length;
        Union myUnion = new Union(grid);
        for (int i = 1; i < col; i++) {
            if (grid[0][i - 1] == '1' && grid[0][i] == '1'){
                myUnion.union(0,i-1,0,i);
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1'){
                myUnion.union(i-1,0,i,0);
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1'){
                    if (grid[i][j - 1] == '1') myUnion.union(i,j,i,j- 1);
                    if (grid[i-1][j] == '1') myUnion.union(i,j,i-1,j);
                }
            }
        }
        return myUnion.getSets();
    }

    private static class Union{
        int[] size;
        int[] parent;
        int[] help;
        int column;
        int sets;

        public Union(char[][] grid) {
            int row = grid.length;
            column = grid[0].length;
            int len = row * column;
            size = new int[len];
            parent = new int[len];
            help = new int[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (grid[i][j] == '1'){
                        int temp = index(i,j);
                        parent[temp] = temp;
                        size[temp] = 1;
                        sets++;
                    }
                }
            }
        }

        public int findParent(int cur){
            int index = 0;
            while (cur != parent[cur]){
                help[index++] = cur;
                cur = parent[cur];
            }
            index--;
            while (index >= 0){
                parent[help[index--]] = cur;
            }
            return cur;
        }

        public void union(int row1,int col1,int row2,int col2){
            int node1 = index(row1, col1);
            int node2 = index(row2, col2);
            int parent1 = findParent(node1);
            int parent2 = findParent(node2);
            if (parent1 != parent2){
                int size1 = size[parent1];
                int size2 = size[parent2];
                int bigNode = size1 >= size2 ? parent1 : parent2;
                int smallNode = bigNode == parent1 ? parent2 : parent1;
                parent[smallNode] = bigNode;
                size[bigNode] = size1 + size2;
                size[smallNode] = 0;
                sets--;
            }
        }

        private int index(int row, int col) {
            return row * column + col;
        }

        public int getSets(){
            return this.sets;
        }
    }


}
