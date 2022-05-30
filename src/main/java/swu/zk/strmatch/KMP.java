package swu.zk.strmatch;

/**
 * @Classname KMP
 * @Description
 * 单模式串匹配算法-----KMP算法
 * KMP算法核心在于next数组的理解
 * next数组即表示前缀与后缀相等的最大子串长度，同时也使下一个将要比较的字符所在的位置
 * 同时为什么匹配失败可以跳过前缀？有两方面的因素
 * 1.前缀与后缀相等 而已经参与比较的模式串和主串的各个字符都相等，由此关系可得模式串和子串前缀对应的位置字符也相等
 * 2.next数组存储的是相等的最大前缀子串位置，因此在主串的匹配起始位置到匹配失败位置之间必然不存在摸个位置开始可以完成匹配
 * eg ;  ....a.m..fg....
 *           a.m.h.fn..
 *       g和n匹配失败，如果存在某个起始位置m在a到f之间使得模式串匹配成功，则必有m....f == a.....h
 *                                                            同时 m...f == m......f
 *       可知模式串中a...h == m....f 即这个是最长的前缀和后缀相等的子串，与next数组定义相符，因此必然是存在于
 *       next数组对应的位置
 * @Date 2022/5/30 17:56
 * @Created by brain
 */
public class KMP {
    public static int kmp(String source,String pattern){
        if (source == null || pattern == null || source.length() < pattern.length()) return -1;
        int[] next = getNext(pattern);
        int sourceLen = source.length();
        int patternLen = pattern.length();
        int index1 = 0;
        int index2 = 0;
        while (index1 < sourceLen && index2 < patternLen){
            if (source.charAt(index1) == pattern.charAt(index2)){
                index1++;
                index2++;
            }else if (next[index2] >= 0){
                index2 = next[index2];
            }else {
                index1++;
            }
        }
        return index2 == patternLen  ? index1 - index2 : -1;
    }

    private static int[] getNext(String pattern) {
        int len = pattern.length();
        int[] result = new int[len];
        result[0] = -1;
        result[1] = 0;
        int preNext = 0;
        int index = 2;
        while (index < len){
            if (pattern.charAt(index - 1) == pattern.charAt(preNext)){
                result[index++] = preNext + 1;
            }else if (preNext > 0){
                preNext = result[preNext];
            }else {
                result[index++] = 0;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(kmp("12345678", "345"));
    }
}
