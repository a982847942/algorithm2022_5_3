package swu.zk.dp.misc;

/**
 * @Classname CarsLine
 * @Description 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数。
 * @Date 2022/6/17 19:07
 * @Created by brain
 */
public class CarsLine {
    public static int win1(int[] arr) {
        int p1 = f1(arr, 0, arr.length - 1);
        int p2 = g1(arr,0,arr.length - 1);
        return Math.max(p1,p2);
    }

    public static int f1(int[] arr, int L, int R) {
        if (L >= R) return 0;
        if (L == R) return arr[L];
        int p1 = g1(arr, L + 1, R) + arr[L];
        int p2 = g1(arr, L, R - 1) + arr[R];
        return Math.max(p1,p2);
    }

    public static int g1(int[] arr, int L, int R) {
        if (L >= R) return 0;
        int p1 = f1(arr,L + 1,R);
        int p2 = f1(arr,L,R - 1);
        return Math.min(p1,p2);
    }

    public static int win2(int[] arr) {
        int n = arr.length;
        int[][] f = new int[n][n];
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            f[i][i] = 1;
        }

        for (int i = 1; i < n; i++) {
            int left = 0;
            int right = i;
            while (right < n){
                f[left][right] = Math.max(arr[left] + g1(arr,left + 1,right),arr[right] + g1(arr,left,right - 1));
                g[left][right] = Math.min(f[left + 1][right],f[left][right - 1]);
                left++;
                right++;
            }
        }
        return Math.max(f[0][n - 1],g[0][n-1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
    }
}
