package swu.zk.bit;

/**
 * @Classname RandToRand
 * @Description 给定一个能够在a, b范围内产生随机整数值的rand函数 由此实现一个rand()范围在c,d的函数
 * 1.将rand()函数 转换为0,1等可能产生的函数
 * 2.将c，d映射为0，d-c
 * 3.查看需要几位二进制信息可以表示0到d-c的范围
 * 4.利用第一步的结果得到0，d-c上等可能发生的功能，然后加上c即可。
 * @Date 2022/6/11 15:55
 * @Created by brain
 */
public class RandToRand {
    /**
     * 已知条件 给定方法 rand7 可生成 [1,7] 范围内的均匀随机整数
     * 要求：试写一个方法 rand10 生成 [1,10] 范围内的均匀随机整数。
     *
     * @return
     */
    public static int condition() {
        return (int) (Math.random() * 7) + 1;
    }

    /**
     * 利用已知条件构建0 1等可能返回的函数
     *
     * @return
     */
    public static int zeroOrOne() {
        int ans = 0;
        do {
            ans = condition();
        } while (ans == 4);
        return ans < 4 ? 0 : 1;
    }

    public static int rand() {
        return (zeroOrOne() << 3) + (zeroOrOne() << 2) + (zeroOrOne() << 1) + zeroOrOne();
    }

    public static int rand10() {
        int ans = 0;
        do {
            ans = rand();
        } while (ans >= 10);
        return ans + 1;
    }


    public static int rand10_2() {
        int num = 0;
        do {
            num = (condition() - 1) * 7 + condition();
        } while (num > 40);
        return 1 + num % 10 ;
    }





    // 这个结构是唯一的随机机制
    // 你只能初始化并使用，不可修改
    public static class RandomBox {
        private final int min;
        private final int max;

        // 初始化时请一定不要让mi==ma
        public RandomBox(int mi, int ma) {
            min = mi;
            max = ma;
        }

        // 13 ~ 17
        // 13 + [0,4]
        public int random() {
            return min + (int) (Math.random() * (max - min + 1));
        }

        public int min() {
            return min;
        }

        public int max() {
            return max;
        }
    }

    // 利用条件RandomBox，如何等概率返回0和1
    public static int rand01(RandomBox randomBox) {
        int min = randomBox.min();
        int max = randomBox.max();
        // min ~ max
        int size = max - min + 1;
        // size是不是奇数，odd 奇数
        boolean odd = (size & 1) != 0;
        int mid = size / 2;
        int ans = 0;
        do {
            ans = randomBox.random() - min;
        } while (odd && ans == mid);
        return ans < mid ? 0 : 1;
    }

    // 给你一个RandomBox，这是唯一能借助的随机机制
    // 等概率返回from~to范围上任何一个数
    // 要求from<=to
    public static int random(RandomBox randomBox, int from, int to) {
        if (from == to) {
            return from;
        }
        // 3 ~ 9
        // 0 ~ 6
        // 0 ~ range
        int range = to - from;
        int num = 1;
        // 求0～range需要几个2进制位
        while ((1 << num) - 1 < range) {
            num++;
        }

        // 我们一共需要num位
        // 最终的累加和，首先+0位上是1还是0，1位上是1还是0，2位上是1还是0...
        int ans = 0;
        do {
            ans = 0;
            for (int i = 0; i < num; i++) {
                ans |= (rand01(randomBox) << i);
            }
        } while (ans > range);
        return ans + from;
    }

    public static void main(String[] args) {
        int[] count = new int[11];
        int testTime = 1_00_0000;
        for (int i = 0; i < testTime; i++) {
            count[rand10_2()]++;
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(count[i]);
        }
    }
}
