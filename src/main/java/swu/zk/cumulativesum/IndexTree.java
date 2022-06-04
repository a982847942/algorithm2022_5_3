package swu.zk.cumulativesum;

/**
 * @Classname IndexTree
 * @Description 二叉索引树（Binary Index Tree 简称BIT）又称树状数组或者Fenwick树，
 * 最早由Peter M. Fenwick与1994年发表。其初衷是解决数据压缩离的累积率（Cumulative Frequency）的计算问题，
 * 现在多用于高效计算数列的前缀和，区间和。构建BIT的时间复杂度为O（NlogN），空间复杂度为O（N）。
 * 搜索和更新的复杂度为O（logN）
 * IndexTree构建规则：
 * BIT[x] = a[x], 当x为奇数
 * BIT[x] = a[0] + a[1] + ... + a[x] , 当x为偶数且x为2的幂
 * BIT[x]  当x为偶数且x不为2的幂时不是很好总结。。。。
 * eg：
 * 下标为 1 2 3 4 5 6 7 8 9 10 11 12 13 14
 * 则 6负责5 6   10负责9 和 10  12负责9 10 11 12  14负责13 14
 * @Date 2022/6/2 15:41
 * @Created by brain
 */
public class IndexTree {
    public static class IndexTree1 {
        int[] help;
        int size;

        public IndexTree1(int size) {
            this.size = size;
            help = new int[this.size + 1];
        }

        public int sum(int index) {
            if (index <= 0) throw new RuntimeException("Index must be greater than 0");
            if (index > size) throw new RuntimeException("ArrayIndexOutOfBounds");
            int res = 0;
            while (index > 0) {
                res += help[index];
                index -= index & (-index);
            }
            return res;
        }

        public void add(int index, int value) {
            if (index <= 0) throw new RuntimeException("Index must be greater than 0");
            if (index > size) throw new RuntimeException("ArrayIndexOutOfBounds");
            while (index <= size) {
                help[index] += value;
                index += index & (-index);
            }
        }
    }


    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }

    public static void main(String[] args) {
//        int N = 100;
//        int V = 100;
//        int testTime = 2000000;
//        IndexTree1 tree = new IndexTree1(N);
//        Right test = new Right(N);
//        System.out.println("test begin");
//        for (int i = 0; i < testTime; i++) {
//            int index = (int) (Math.random() * N) + 1;
//            if (Math.random() <= 0.5) {
//                int add = (int) (Math.random() * V);
//                tree.add(index, add);
//                test.add(index, add);
//            } else {
//                if (tree.sum(index) != test.sum(index)) {
//                    System.out.println("Oops!");
//                }
//            }
//        }
//        System.out.println("test finish");

        int[] arr = {1,2,3,4,5};
        IndexTree1 tree1 = new IndexTree1(arr.length);
        for (int i = 0; i < arr.length; i++) {
            tree1.add(i + 1,arr[i]);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(tree1.sum(i + 1));
        }
    }
}
