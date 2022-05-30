package swu.zk.strmatch;

/**
 * @Classname BruteForce
 * @Description
 *单模式串匹配算法-----暴力匹配
 * @Date 2022/5/30 17:41
 * @Created by brain
 */
public class BruteForce {

    /**
     * 复杂度O(nm) n为source长度 m为pattern长度
     * @param source
     * @param pattern
     * @return
     */
    public static int bruteForce(String source, String pattern){
        if (source == null || pattern == null || source.length() < pattern.length()) return -1;
        int sourceLen = source.length();
        int patternLen = pattern.length();
        for (int i = 0; i < sourceLen; i++) {
            int curI = i;
            boolean isMatch = true;
            for (int j = 0; j < patternLen; j++,curI++) {
                if (source.charAt(curI) != pattern.charAt(j)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String str1 = "abcdefg";
        String str2 = "bcde";
        System.out.println(bruteForce(str1, str2));;
    }
}
