package swu.zk.strmatch;

/**
 * @Classname BM
 * @Description 单模式串匹配算法-----BM算法(从尾部开始比较)
 * <p>
 * 坏字符规则：当文本串中的某个字符跟模式串的某个字符不匹配时，
 * 我们称文本串中的这个失配字符为坏字符，此时模式串需要向右移动，
 * 移动的位数 = 坏字符在模式串中的位置 - 坏字符在模式串中最右出现的位置。
 * 此外，如果"坏字符"不包含在模式串之中，则最右出现位置为-1。
 * <p>
 * 好后缀规则：当字符失配时，
 * 后移位数 = 好后缀在模式串中的位置 - 好后缀在模式串上一次出现的位置，
 * 且如果好后缀在模式串中没有再次出现，则为-1。
 * <p>
 * 无论是是坏字符规则还是好后缀规则目的都是为了在模式串中找到一个合适的位置让其与主串对齐。
 * 如果匹配失败的字符在模式串中存在则移动的最右出现的位置与匹配失败的主串相应字符对齐
 * 如果模式串中存在已经匹配成功的好后缀，则把目标串与好后缀对齐，然后从模式串的最尾元素开始往前匹配
 * 如果无法找到匹配好的后缀，找一个匹配的最长的前缀，让目标串与最长的前缀对齐（如果这个前缀存在的话）。
 * 模式串[m-s，m] = 模式串[0，s] 。
 * <p>
 * 不过，单纯使用坏字符规则还是不够的。因为根据 si-xi 计算出来的移动位数，有可能是负数，
 * 比如主串是 aaaaaaaaaaaaaaaa，模式串是 baaa。不但不会向后滑动模式串，还有可能倒退。
 * 所以，BM 算法还需要用到“好后缀规则”
 * 注意这里其实有一个小问题，为了避免出现负数可以从si向前查找，而不是查找整个模式串最右侧的相应字符，为什么不这样做呢？
 * 因为我们会预先处理每个字符在模式串最右侧的位置，这样可以方便查找加快速度，如果采用上面的做法从si向左查找，会在匹配失败
 * 时浪费查找时间。
 * 好后缀时也需要注意：不要滑动过度，我们不仅要看好后缀在模式串中，是否有另一个匹配的子串，
 * 如果没有匹配的好后缀字符串，我们还要考察好后缀的后缀子串(类似KMP)，是否存在跟模式串的前缀子串匹配的。
 * @Date 2022/5/30 17:57
 * @Created by brain
 */
public class BM {

    /**
     * 初级bm算法  存在不足 比如构建好后缀规则时，如果模式串是aaaaaaaaa  预处理时间复杂度退化为O(m2)
     */
    //用于构建坏字符规则
    static final int CHARSIZE = 256;

    public static int[] buildBadTable(String pattern) {
        int patternLen = pattern.length();
        int[] result = new int[CHARSIZE];
        for (int i = 0; i < CHARSIZE; i++) {
            result[i] = -1;
        }
        for (int i = 0; i < patternLen; i++) {
            result[pattern.charAt(i)] = i;
        }
        return result;
    }

    public static void buildGoodTable(String pattern, int[] suffix, boolean[] prefix) {
        int patternLen = pattern.length();
        for (int i = 0; i < patternLen; i++) {
            suffix[i] = -1;
        }

        for (int i = 0; i < patternLen - 1; i++) {
            int j = i;
            int k = 0;
            while (pattern.charAt(j) == pattern.charAt(patternLen - 1 - k)) {
                k++;
                j--;
                suffix[k] = j + 1;
            }
            if (j == -1) prefix[k] = true;
        }

    }

    public static int bm2(String source, String pattern) {
        if (source == null || pattern == null || source.length() < pattern.length()) return -1;
        int sourceLen = source.length();
        int patternLen = pattern.length();
        int[] suffix = new int[patternLen];
        boolean[] prefix = new boolean[patternLen];
        int[] badTable = buildBadTable(pattern);
        buildGoodTable(pattern, suffix, prefix);
        int i = 0;
        while (i < sourceLen - patternLen) {
            int j;

            for (j = patternLen - 1; j >= 0; j--) {
                //出现坏字符
                if (source.charAt(i + j) != pattern.charAt(j)) break;
            }
            //匹配成功 返回i
            if (j < 0) return i;
            //坏字符规则
            int x = j - badTable[source.charAt(i + j)];
            //好后缀规则
            int y = 0;
            if (j < patternLen - 1) {
                y = moveByGoodSuffix(j, patternLen, suffix, prefix);
            }
            //取最大值
            i += Math.max(x, y);
        }
        //不匹配
        return -1;
    }

    private static int moveByGoodSuffix(int j, int patternLen, int[] suffix, boolean[] prefix) {
        int k = patternLen - 1 - j;
        //suffix[k] 表示长度为k的后缀 与之对应的重复子串的起始位置的下标
        if (suffix[k] != -1) return j - suffix[k] + 1;
        for (int r = j + 2; r <= patternLen - 1; r++) {
            //为什么返回r?  理解prefix[k]的含义 指的是长度为k的好后缀有与之对应的前缀
            if (prefix[patternLen - r] == true) return r;
        }
        return patternLen;
    }


    /**
     * 进行一定优化后的bm算法  大大体思想不变 但实现逻辑改变
     * @param pattern
     * @param source
     * @return
     */
    public static int bm(String pattern, String source) {
        if (source == null || pattern == null) return -1;
        int sourceLen = source.length();//主串的长度
        int patternLen = pattern.length();//模式串的长度

        //如果模式串比主串长，没有可比性，直接返回-1
        if (patternLen > sourceLen) {
            return -1;
        }

        int[] bad_table = build_bad_table(pattern);// 获得坏字符数值的数组，实现看下面
        int[] good_table = build_good_table(pattern);// 获得好后缀数值的数组，实现看下面

        for (int i = patternLen - 1, j; i < sourceLen; ) {
            System.out.println("跳跃位置：" + i);
            //这里和上面实现坏字符的时候不一样的地方，我们之前提前求出坏字符以及好后缀
            //对应的数值数组，所以，我们只要在一边循环中进行比较。还要说明的一点是，这里
            //没有使用skip记录跳过的位置，直接针对主串中移动的指针i进行移动
            for (j = patternLen - 1; source.charAt(i) == pattern.charAt(j); i--, j--) {
                if (j == 0) {//指向模式串的首字符，说明匹配成功，直接返回就可以了
                    System.out.println("匹配成功，位置：" + i);
                    //如果你还要匹配不止一个模式串，那么这里直接跳出这个循环，并且让i++
                    //因为不能直接跳过整个已经匹配的字符串，这样的话可能会丢失匹配。
//					i++;   // 多次匹配
//					break;
                    return i;
                }
            }
            //如果出现坏字符，那么这个时候比较坏字符以及好后缀的数组，哪个大用哪个
            i += Math.max(good_table[patternLen - j - 1], bad_table[source.charAt(i)]);
        }
        return -1;
    }

    //字符信息表
    public static int[] build_bad_table(String pattern) {
        final int table_size = 256;//上面已经解释过了，字符的种类
        int[] bad_table = new int[table_size];//创建一个数组，用来记录坏字符出现时，应该跳过的字符数
        int pLen = pattern.length();//模式串的长度

        for (int i = 0; i < bad_table.length; i++) {
            bad_table[i] = pLen;
            //默认初始化全部为匹配字符串长度,因为当主串中的坏字符在模式串中没有出
            //现时，直接跳过整个模式串的长度就可以了
        }
        for (int i = 0; i < pLen - 1; i++) {
            int k = pattern.charAt(i);//记录下当前的字符ASCII码值
            //这里其实很值得思考一下，bad_table就不多说了，是根据字符的ASCII值存储
            //坏字符出现最右的位置，这在上面实现坏字符的时候也说过了。不过你仔细思考
            //一下，为什么这里存的坏字符数值，是最右的那个坏字符相对于模式串最后一个
            //字符的位置？为什么？首先你要理解i的含义，这个i不是在这里的i，而是在上面
            //那个pattern函数的循环的那个i，为了方便我们称呼为I，这个I很神奇，虽然I是
            //在主串上的指针，但是由于在循环中没有使用skip来记录，直接使用I随着j匹配
            //进行移动，也就意味着，在某种意义上，I也可以直接定位到模式串的相对位置，
            //理解了这一点，就好理解在本循环中，i的行为了。

            //其实仔细去想一想，我们分情况来思考，如果模式串的最
            //后一个字符，也就是匹配开始的第一个字符，出现了坏字符，那么这个时候，直
            //接移动这个数值，那么正好能让最右的那个字符正对坏字符。那么如果不是第一个
            //字符出现坏字符呢？这种情况你仔细想一想，这种情况也就意味着出现了好后缀的
            //情况，假设我们将最右的字符正对坏字符
            bad_table[k] = pLen - 1 - i;
        }
        return bad_table;
    }

    //匹配偏移表
    public static int[] build_good_table(String pattern) {
        int pLen = pattern.length();//模式串长度
        int[] good_table = new int[pLen];//创建一个数组，存好后缀数值
        //用于记录最新前缀的相对位置，初始化为模式串长度，因为意思就是当前后缀字符串为空
        //要明白lastPrefixPosition 的含义
        int lastPrefixPosition = pLen;

        for (int i = pLen - 1; i >= 0; --i) {
            if (isPrefix(pattern, i + 1)) {
                //如果当前的位置存在前缀匹配，那么记录当前位置
                lastPrefixPosition = i + 1;
            }
            good_table[pLen - 1 - i] = lastPrefixPosition - i + pLen - 1;
        }

        for (int i = 0; i < pLen - 1; ++i) {
            //计算出指定位置匹配的后缀的字符串长度
            int slen = suffixLength(pattern, i);
            good_table[slen] = pLen - 1 - i + slen;
        }
        return good_table;
    }

    //前缀匹配
    private static boolean isPrefix(String pattern, int p) {
        int patternLength = pattern.length();//模式串长度
        //这里j从模式串第一个字符开始，i从指定的字符位置开始，通过循环判断当前指定的位置p
        //之后的字符串是否匹配模式串前缀
        for (int i = p, j = 0; i < patternLength; ++i, ++j) {
            if (pattern.charAt(i) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    //后缀匹配
    private static int suffixLength(String pattern, int p) {
        int pLen = pattern.length();
        int len = 0;
        for (int i = p, j = pLen - 1; i >= 0 && pattern.charAt(i) == pattern.charAt(j); i--, j--) {
            len += 1;
        }
        return len;
    }


    public static void main(String[] args) {
        System.out.println(bm2("abaabababaabggdf", "bgg"));
    }
}
