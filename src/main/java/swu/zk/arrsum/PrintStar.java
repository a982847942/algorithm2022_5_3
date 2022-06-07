package swu.zk.arrsum;

/**
 * @Classname PrintStar
 * @Description TODO
 * @Date 2022/6/5 10:18
 * @Created by brain
 */
public class PrintStar {
    public static void printStar(int n){
        if (n <= 0) return;
        int leftUp = 0;
        int rightDown = n - 1;
        char[][] matrix = new  char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = ' ';
            }
        }

        while (leftUp <= rightDown){
            print2(matrix,leftUp,rightDown);
            leftUp += 2;
            rightDown -= 2;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void print(char[][] matrix, int leftUp, int rightDown) {
        if (leftUp == rightDown){
            matrix[leftUp][leftUp] = '*';
            return;
        }
        int curRow = leftUp;
        int curColumn = leftUp;
        while (curColumn < rightDown){
            matrix[leftUp][curColumn++] = '*';
        }
        while (curRow < rightDown){
            matrix[curRow++][rightDown] = '*';
        }
        while (curColumn >= leftUp + 1){
            matrix[rightDown][curColumn--] = '*';
        }

        while (curRow > leftUp + 1){
            matrix[curRow--][leftUp + 1] = '*';
        }
    }

    public static void print2(char[][] matrix, int leftUp, int rightDown) {
        for (int col = leftUp; col <= rightDown; col++) {
            matrix[leftUp][col] = '*';
        }
        for (int row = leftUp + 1; row <= rightDown; row++) {
            matrix[row][rightDown] = '*';
        }
        for (int col = rightDown - 1; col > leftUp; col--) {
            matrix[rightDown][col] = '*';
        }
        for (int row = rightDown - 1; row > leftUp + 1; row--) {
            matrix[row][leftUp + 1] = '*';
        }
    }

    public static void main(String[] args) {
        printStar(8);

//        System.out.println( "00" + ' ' + "00");
//        System.out.println("00" + '\u0000' + "00");
    }

}
