package swu.zk.arrsum;

/**
 * @Classname RotateMatrix
 * @Description 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 * a  b  c		g  d  a
 * d  e  f		h  e  b
 * g  h  i		i  f  c
 * @Date 2022/6/4 22:11
 * @Created by brain
 */
public class RotateMatrix {
    public static void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || matrix.length != matrix[0].length) return;
        int startRow = 0;
        int startColumn = 0;
        int endRow = matrix.length - 1;
        int endColumn = matrix[0].length - 1;
        while (startRow < endRow) {
            rotateEdge(matrix, startRow++, startColumn++, endRow--, endColumn--);
        }
    }

    private static void rotateEdge(int[][] matrix, int startRow, int startColumn, int endRow, int endColumn) {
        for (int i = 0; i < endColumn - startColumn; i++) {
            int temp = matrix[startRow][startColumn + i];
            matrix[startRow][startColumn + i] = matrix[endRow - i][startColumn];
            matrix[endRow - i][startColumn] = matrix[endRow][endColumn - i];
            matrix[endRow][endColumn - i] = matrix[startRow + i][endColumn];
            matrix[startRow + i][endColumn] = temp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }
}
