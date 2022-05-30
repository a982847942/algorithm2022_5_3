package swu.zk.strmatch;

/**
 * @Classname RK
 * @Description 单模式串匹配算法-----Rabin-Karp算法
 * Rabin-Karp算法的核心是 hash函数的构建
 * 假设字符集只包含a~z 26个小写字符
 * 注意事项：
 * 1.使用26进制的hash算法 则类比10进制 可知 hash冲突不存在
 * 2.如果采用累加计算hash值 则存在冲突 'abcd' == 'cbda' 此时要解决冲突
 * 3.如果对主串逐个求模式串长度的子串hash值 则整体复杂度任然是O(nm),因此需要技巧，类比26进制 可根据前一个
 * hash值快速推导出下一个hash
 * 缺点：
 * 1.字符集太大 hash算法设计起来很难
 * 2.如果允许冲突 且冲突过多时时间复杂度会发生退化
 * @Date 2022/5/30 18:02
 * @Created by brain
 */
public class RK {

    /**
     * 表示进制，参考go语言中Rabin-Karp算法实现中的值
     */
    private static final int R = 16777619;
    /**
     * 哈希值可能太大，取模，随机值BigInteger.probablePrime(31, new Random())
     */
    private static final long Q = 1538824213;

    public static int rabinKarp(String source, String target) {
        int targetLen = target.length();
        int sourceLen = source.length();
        if (targetLen == 0) {
            return 0;
        }
        if (sourceLen < targetLen) {
            return -1;
        }
        //预先获取R的m-1次方
        long RM = initRM(target);
        //模式串的hash值
        long targetHash = hash(target, 0, targetLen - 1);
        //主串开始位置
        int index = 0;
        //主串进行匹配的hash值
        long sourceHash = 0;
        //循环匹配n-m+1次
        while (index <= sourceLen - targetLen) {
            // 开始比较
            sourceHash = nextHash(source, target, index, sourceHash, RM);
            //hash值相等 可能存在冲突 继续采用暴力匹配法比较是否真正相同
            if (sourceHash == targetHash) {
                if (equals(source, index, index + targetLen - 1, target)) {
                    return index;
                }
            }
            index++;
        }
        return -1;
    }

    private static long nextHash(String source, String target, int index, long preHash, long RM) {
        int targetLen = target.length();
        if (index == 0) {
            return hash(source, 0, targetLen - 1);
        }
        long hash = preHash;
        // 去掉第一个字符的hash值
        hash = mod(hash - mod(RM * source.charAt(index - 1)));
        // 加上下一个字符的hash值
        hash = mod(mod(hash * R) + source.charAt(index + targetLen - 1));
        return hash;
    }

    private static long hash(String str, int start, int end) {
        long hash = 0;
        for (int i = start; i <= end; i++) {
            hash = mod(hash * R + str.charAt(i));
        }
        return hash;
    }

    private static long mod(long hash) {
        if (hash < 0) {
            return hash + Q;
        }
        return hash % Q;
    }

    //target是模式串长度  这里计算R的m-1次方
    private static long initRM(String target) {
        long RM = 1;
        for (int i = 1; i < target.length(); i++) {
            RM = (R * RM) % Q;
        }
        return RM;
    }

    private static boolean equals(String source, int start, int end, String target) {
        if (end >= source.length()) {
            return false;
        }
        for (int i = 0; i <= end - start; i++) {
            if (source.charAt(i + start) != target.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(rabinKarp("123456789", "234567"));
    }

}
