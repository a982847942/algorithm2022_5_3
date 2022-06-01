package swu.zk.strmatch;

import java.util.HashMap;

/**
 * @Classname Trie
 * @Description
 * 多模式串匹配算法-----Trie tree
 * @Date 2022/5/30 17:58
 * @Created by brain
 */
public class Trie {

    /**
     * 前缀树 初级版本 缺点：
     * 1.每个节点所占用的内存空间受限于字符集大小，字符集太大 很浪费空间
     * 2.如果各个字符串的前缀重复度不高  则前缀树占用空间也很大
     * 3.指针内存时非连续地址空间，因此访问性能也比较低
     *
     *对于1 可以采用散列表  跳表  有序数组 有序树等结构优化空间
     *  此外还可以采用缩点优化技巧
     *其实前缀树并不是很适合 精确匹配字符串  更适用于模糊匹配，比如google搜索提示
     * 但是提示不仅仅依赖前缀树！！！
     * 至少要在前缀树基础上考虑以下几点：
     * 1.中文字符怎么办？
     * 2.可能匹配成功的字符串很多 如何选择那些字符串作为提示串？ （权重等）
     * 3.单词拼写错误情况下，如何提示使用正确的关键词来进行提示？（编辑距离）
     */
    private static class TrieNode{
        int pass;
        int end;
        TrieNode[] nexts;

        public TrieNode() {
            pass = 0;
            end = 0;
            nexts = new TrieNode[26];
        }
    }


    private static class Trie1{
        TrieNode root;

        public Trie1() {
            root = new TrieNode();
        }

        public  void insert(String pattern){
            if (pattern == null) return;
            char[] patternArr = pattern.toCharArray();
            TrieNode cur = root;
            root.pass++;
            for (int i = 0; i < patternArr.length; i++) {
                int index = patternArr[i] - 'a';
                if (cur.nexts[index] == null){
                    cur.nexts[index] = new TrieNode();
                }
                cur = cur.nexts[index];
                cur.pass++;
            }
            cur.end++;
        }


        public int search(String pattern){
            if (pattern == null) return 0;
            char[] patternArr = pattern.toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < patternArr.length; i++) {
                int index = patternArr[i] - 'a';
                if (cur.nexts[index] == null){
                    return 0;
                }
                cur = cur.nexts[index];
            }
            return cur.end;
        }

        public void delete(String pattern){
            if (search(pattern) != 0){
                char[] patternArr = pattern.toCharArray();
                TrieNode cur = root;
                cur.pass--;
                for (int i = 0; i < patternArr.length; i++) {
                    int index = patternArr[i] - 'a';
                    if (--cur.nexts[index].pass == 0){
                        cur.nexts[index] = null;
                        return;
                    }
                    cur = cur.nexts[index];
                }
                cur.end--;
            }
        }

        public int prefixCount(String pattern){
            if (pattern == null) return 0;
            char[] patternArr = pattern.toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < patternArr.length; i++) {
                int index = patternArr[i] - 'a';
                if (cur.nexts[index] == null){
                    return 0;
                }
                cur = cur.nexts[index];
            }
            return cur.pass;
        }
    }



    public static class TrieNode2{
        int pass;
        int end;
        HashMap<Integer,TrieNode2> nexts;

        public TrieNode2() {
            nexts = new HashMap<>();
        }
    }

    public static class Trie2{
        TrieNode2 root;

        public Trie2() {
            root = new TrieNode2();
        }


        public  void insert(String pattern){
            if (pattern == null) return;
            char[] patternArr = pattern.toCharArray();
            TrieNode2 cur = root;
            root.pass++;
            for (int i = 0; i < patternArr.length; i++) {
                int index = patternArr[i] - 'a';
                if (!cur.nexts.containsKey(index)){
                    cur.nexts.put(index,new TrieNode2());
                }
                cur = cur.nexts.get(index);
                cur.pass++;
            }
            cur.end++;
        }

        public int search(String pattern){
            if (pattern == null) return 0;
            char[] patternArr = pattern.toCharArray();
            TrieNode2 cur = root;
            for (int i = 0; i < patternArr.length; i++) {
                int index = patternArr[i] - 'a';
                if (!cur.nexts.containsKey(index)){
                    return 0;
                }
                cur = cur.nexts.get(index);
            }
            return cur.end;
        }

        public void delete(String pattern){
            if (search(pattern) != 0){
                char[] patternArr = pattern.toCharArray();
                TrieNode2 cur = root;
                cur.pass--;
                for (int i = 0; i < patternArr.length; i++) {
                    int index = patternArr[i] - 'a';
                    if (--cur.nexts.get(index) .pass== 0){
                        cur.nexts.remove(index);
                        return;
                    }
                    cur = cur.nexts.get(index);
                }
                cur.end--;
            }
        }

        public int prefixCount(String pattern){
            if (pattern == null) return 0;
            char[] patternArr = pattern.toCharArray();
            TrieNode2 cur = root;
            for (int i = 0; i < patternArr.length; i++) {
                int index = patternArr[i] - 'a';
                if (!cur.nexts.containsKey(index)){
                    return 0;
                }
                cur = cur.nexts.get(index);
            }
            return cur.pass;
        }

    }


    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = Util.generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixCount(arr[j]);
                    int ans2 = trie2.prefixCount(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");
    }
}
