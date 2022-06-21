package swu.zk.dp.misc;

/**
 * @Classname NQueens
 * @Description
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 * @Date 2022/6/19 22:33
 * @Created by brain
 */
public class NQueens {

    public static int num(int n){
        if (n < 1)return 0;
        int[] record = new int[n];
        return process(0,n,record);
    }

    /**
     *当前来到cur 行 总共有n个位置(0...n - 1)
     * record记录的是 某一行对应的皇后摆在那一列
     * 比如record[1] = 2 表示第一行皇后摆在第二列
     */
    private static int process(int cur, int n, int[] record) {
        if (cur == n)return 1;
        int ans = 0;
        //遍历每一个位置
        for (int i = 0; i < n; i++) {
            if (valid(record,i,cur)){
                record[cur] = i;
                ans += process(cur + 1,n,record);
            }
        }
        return ans;
    }

    private static boolean valid(int[] record, int i, int cur) {
        //遍历该位置之前的位置
        for (int j = 0; j < cur; j++) {
            if (i == record[j] || (Math.abs(i - record[j]) == Math.abs(cur - j))){
                return false;
            }
        }
        return true;
    }

    // 请不要超过32皇后问题
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(num(10));
    }
}
