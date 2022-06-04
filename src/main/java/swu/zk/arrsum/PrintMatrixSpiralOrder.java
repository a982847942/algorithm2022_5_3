package swu.zk.arrsum;

/**
 * @Classname PrintMatrixSpiralOrder
 * @Description
    给定一个长方形矩阵matrix，实现转圈打印
    a  b  c  d
    e  f  g  h
    i  j  k  L
    打印顺序：a b c d h L k j I e f g
 * @Date 2022/6/4 21:26
 * @Created by brain
 */
public class PrintMatrixSpiralOrder {
    public static void spiralOrderPrint(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
        int startRow = 0;
        int startColumn = 0;
        int endRow = matrix.length - 1;
        int endColumn = matrix[0].length - 1;
        while (startRow <= endRow && startColumn <= endColumn){
            printEdge(matrix,startRow++,startColumn++,endRow--,endColumn--);
        }
    }

    private static void printEdge(int[][] matrix, int startRow, int startColumn, int endRow, int endColumn) {
        if (startRow == endRow){
            for (int i = startColumn; i <= endColumn; i++) {
                System.out.print(matrix[startRow][i] + " ");
            }
        }else if (startColumn == endColumn){
            for (int i = startRow; i <= endRow ; i++) {
                System.out.print(matrix[i][endColumn]);
            }
        }else {
            int curRow = startRow;
            int curColumn = startColumn;
            while (curColumn < endColumn){
                System.out.print(matrix[curRow][curColumn++]+ " ");
            }

            while (curRow < endRow){
                System.out.print(matrix[curRow++][curColumn]+ " ");
            }

            while (curColumn > startColumn){
                System.out.print(matrix[curRow][curColumn--]+ " ");
            }

            while (curRow > startRow){
                System.out.print(matrix[curRow--][curColumn] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);
    }
}
