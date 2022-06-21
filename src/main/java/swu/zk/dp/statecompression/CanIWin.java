package swu.zk.dp.statecompression;

/**
 * @Classname CanIWin
 * @Description 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，
 * 累计整数和，先使得累计整数和 达到或超过  100 的玩家，即为胜者。
 * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和）
 * 若出手的玩家是否能稳赢则返回 true ，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 * Leetcode 464
 * @Date 2022/6/21 10:25
 * @Created by brain
 */
public class CanIWin {
    public static boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= 0) return true;
        if (maxChoosableInteger >= desiredTotal) return true;
        if ((maxChoosableInteger + 1) * maxChoosableInteger / 2 < desiredTotal) return false;
        int[] used = new int[maxChoosableInteger + 1];
        return process(0, maxChoosableInteger, desiredTotal, used);
    }

    /**
     * 当前使用的值是cur  max是取值的范围  rest是剩余total  used是标记1...max 那些数可以使用
     * 递归函数含义：
     * 先手遍历每一个可以取的值 判断是否存在一种方法能够获胜
     */
    private static boolean process(int cur, int max, int rest, int[] used) {
        if (rest <= 0) return false;
        used[cur] = 1;
        //遍历每一个取值的可能
        for (int i = 1; i <= max; i++) {
            if (used[i] != 1) {
                boolean ans = process(i, max, rest - i, used);
                //在这里恢复现场  不然ans为false直接返回 就无法恢复现场
                used[cur] = 0;
                //这里的含义是  先手取了 i 之后 进行递归 递归的结果ans 表示后手是否能获胜
                //只要有一种可能后手返回false  自然先手就算赢了
                if (!ans) {
                    return true;
                }
            }
        }
        //这里是错误的
//        used[cur]= 0;
        return false;
    }


    public static boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= 0) return true;
        if (maxChoosableInteger >= desiredTotal) return true;
        if ((maxChoosableInteger + 1) * maxChoosableInteger / 2 < desiredTotal) return false;
        return process2(0, maxChoosableInteger, desiredTotal);
    }

    private static boolean process2(int state, int max, int rest) {
        if (rest <= 0) return false;
        for (int i = 1; i <= max; i++) {
            if (((1 << i) & state) == 0) {
                if (!process2((state | (1 << i)), max, rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canIWin3(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= 0) return true;
        if (maxChoosableInteger >= desiredTotal) return true;
        if ((maxChoosableInteger + 1) * maxChoosableInteger / 2 < desiredTotal) return false;
        int[] dp = new int[1<<(maxChoosableInteger + 1)];
        return process3(0,maxChoosableInteger,desiredTotal,dp);
    }

    private static boolean process3(int state, int max, int rest, int[] dp) {
        if (dp[state] != 0)return dp[state] == 1 ? true : false;
        if (rest <= 0) {
            dp[state] = -1;
            return false;
        }
        boolean ans = false;
        for (int i = 1; i <= max ; i++) {
            if (((1 << i) & state) == 0){
                if (!process3((state | (1 << i)), max, rest - i, dp)){
                    ans = true;
                    break;
                }
            }
        }
        dp[state] = ans ? 1 : -1;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(canIWin3(10, 11));
    }
}
