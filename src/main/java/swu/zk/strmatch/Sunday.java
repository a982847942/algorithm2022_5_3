package swu.zk.strmatch;

/**
 * @Classname Sunday
 * @Description
 * 单模式串匹配算法-----Sunday算法
 * 类似BM算法  只是对坏字符规则做了相应修改 并未使用好后缀规则
 * sunday算法在字符重复次数不多情况下性能比较优秀，如果出现aaaaaaaaa  aab这样的主串和模式串
 * sunday算法会发生退化 时间复杂度与BF算法一致
 *
 * 算法流程
 * 从前向后开始匹配，发生不匹配时注的是主串中参加匹配的最末位字符的下一位字符
 * 1.如果该字符没有在模式串中出现则直接跳过，即移动位数 = 模式串长度 + 1；
 * 2.否则，其移动位数 = 模式串长度 - 该字符最右出现的位置(以0开始) = 模式串中该字符最右出现的位置到尾部的距离 + 1。
 * 这个规则实质上就是让最后出现的找相同字符与主串中的关注的字符对齐，重新开始匹配
 * @Date 2022/5/30 17:57
 * @Created by brain
 */
public class Sunday {
    static int maxNum = 501;
    public static int sunday(String source, String pattern) {
        int sourceLen = source.length();
        int patternLen = pattern.length();

        //预处理移动数组
        int[] move = new int[maxNum];
        for (int i = 0; i < maxNum; i++) {
            move[i] = patternLen + 1;
        }
        for (int i = 0; i < patternLen; i++) {
            move[pattern.charAt(i)] = patternLen - i;
        }

        int i = 0;//sourceLen串的游标索引
        int j = 0;// pattern串的游标索引

        // sourceLen剩余字符少于needle串时跳过比较
        while (i <= sourceLen - patternLen) {
            // 将pattern串与sourceLen串中参与比较的子串进行逐个字符比对
            while (j < patternLen && source.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            // 如果j等于pattern串的长度说明此时匹配成功，可以直接返回此时主串的游标索引
            if (j == patternLen) {
                return i;
            }

            // 不匹配时计算需要跳过的字符数，移动主串游标i
            if (i < sourceLen - patternLen) {
                // 对照字符在pattern串存在，则需要跳过的字符数为从对照字符在pattern串中最后出现的位置起剩余的字符个数
                // 不存在则跳过的字符数为pattern串长度+1，也就是代码patternLen-(-1)的情况
//                i += (patternLen - lastIndex(pattern, source.charAt(i + patternLen)));
                i += move[source.charAt(i + patternLen)];
            } else {
                return -1;
            }
            // 每次比较之后将pattern游标置为0
            j=0;
        }

        return -1;
    }

    //进一步优化性能时，可以预先处理模式串，保存每个字符最后出现的位置。
    public static int lastIndex(String str, char ch) {
        // 从后往前检索
        for (int j = str.length() - 1; j >= 0; j--) {
            if (str.charAt(j) == ch) {
                return j;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(sunday("abcdefg", "fg"));
    }
}
