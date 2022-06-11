package swu.zk.bit;

import java.util.HashSet;

/**
 * @Classname BitMap
 * @Description 位图的基本实现
 * @Date 2022/6/11 16:43
 * @Created by brain
 */
public class BitMap {

    private long[] bits;

    public BitMap(int max) {
        /**
         * 确定需要多少位long类型数据
         * 0...63   ---->   1
         * 64...127 ---->   2
         */
        bits = new long[(max + 64) >> 6];
    }

    public void add(int num) {
        /**
         * num >> 6 即 num / 64 确定在数组第几个位置
         * num & 63  即 num % 64  确定在该位置的第几bit
         * 1L << (num & 63) 将该bit为设置为 1
         * bits[num >> 6] |= (1L << (num & 63)) 将该位置的值与1L << (num & 63)或运算 即将
         * 相应的bit为设为1
         */
        bits[num >> 6] |= (1L << (num & 63));
    }

    public void delete(int num) {
        /**
         * ~(1L << (num & 63))
         * 即0001000 ----> 1110111 然后相与 即将对应位置变为0
         * 因此也就从位图中删除了该值
         */
        bits[num >> 6] &= ~(1L << (num & 63));
    }

    public boolean contains(int num) {
        /**
         * 11101010
         * 00001000
         * 得到00001000
         * 即确定该数包含在位图中
         */
        return (bits[num >> 6] & (1L << (num & 63))) != 0;
    }


    public static void main(String[] args) {
        System.out.println("测试开始！");
        int max = 10000;
        BitMap bitMap = new BitMap(max);
        HashSet<Integer> set = new HashSet<>();
        int testTime = 10000000;
        for (int i = 0; i < testTime; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                bitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                bitMap.delete(num);
                set.remove(num);
            } else {
                if (bitMap.contains(num) != set.contains(num)) {
                    System.out.println("Oops!");
                    break;
                }
            }
        }
        for (int num = 0; num <= max; num++) {
            if (bitMap.contains(num) != set.contains(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }
}
