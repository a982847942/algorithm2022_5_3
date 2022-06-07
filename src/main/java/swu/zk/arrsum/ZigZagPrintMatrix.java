package swu.zk.arrsum;

/**
 * @Classname ZigZagPrintMatrix
 * @Description 给定一个正方形或者长方形矩阵matrix，实现zigzag打印
 * 0 1 2
 * 3 4 5
 * 6 7 8
 * 打印: 0 1 3 6 4 2 5 7 8
 * @Date 2022/6/5 10:53
 * @Created by brain
 */
public class ZigZagPrintMatrix {

    public static void print(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
        //打印对角线的起点和终点坐标
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        int limitX = matrix.length - 1;
        int limitY = matrix[0].length - 1;
        //打印的方向
        boolean fromUp = false;
        while (startX != limitX + 1){
            printEdge(matrix,startX,startY,endX,endY,fromUp);
            //起点更新遵循 先左后下
            startX = startY == limitY ? startX + 1 : startX;
            startY = startY == limitY ? startY : startY + 1;
            //一定要注意顺序！！！ ednY 依赖上一次endX 先更新endY
            //终点更新遵循先下后左
            endY = endX == limitX ? endY + 1 : endY;
            endX = endX == limitX ? endX : endX + 1;
            fromUp = !fromUp;
        }
    }

    private static void printEdge(int[][] matrix, int startX, int startY, int endX, int endY, boolean fromUp) {
        if (fromUp){
            while (startX != endX + 1){
                System.out.print(matrix[startX++][startY--]+ " ");
            }
        }else {
            while (endX != startX - 1){
                System.out.print(matrix[endX--][endY++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        print(matrix);
    }
}
