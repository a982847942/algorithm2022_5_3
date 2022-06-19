package swu.zk.dp.misc;

/**
 * @Classname KillMonster
 * @Description
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 * @Date 2022/6/19 19:12
 * @Created by brain
 */
public class KillMonster {

    public static double diePossibility(int N, int M, int K){
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        return process(N,K,M) / Math.pow(M + 1,K);
    }

    private static double process(int curHP, int times, int M) {
        if (times == 0){
            return curHP <= 0 ? 1 : 0;
        }
        if (curHP <= 0) {
            return Math.pow(M + 1,times);
        }
        double ans = 0;
        for (int i = 0; i < M + 1; i++) {
            ans += process(curHP - i,times - 1,M);
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
