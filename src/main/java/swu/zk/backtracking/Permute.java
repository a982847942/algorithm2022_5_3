package swu.zk.backtracking;

import java.util.*;

/**
 * @Classname Permute
 * @Description
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。
 * 你可以 按任意顺序 返回答案。
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * @Date 2022/5/25 23:10
 * @Created by brain
 */
public class Permute {
    public List<List<Integer>> permute1(int[] nums) {
        if (nums == null || nums.length == 0)return null;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        backTracking1(nums,0,res,temp);
        return res;
    }

    private void backTracking1(int[] nums, int index, List<List<Integer>> res, List<Integer> temp) {
        if (index == nums.length){
            res.add(new ArrayList<>(temp));
            return;
        }else {
            for (int i = index; i < nums.length; i++) {
                temp.add(nums[i]);
                swap(nums,index,i);
                backTracking1(nums,index + 1,res,temp);
                temp.remove(temp.size() - 1);
                swap(nums,index,i);
            }
        }
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }


    /**
     * 注意体会各个版本的变化
     * @param nums
     * @return
     */
    public List<List<Integer>> permute2(int[] nums) {
        if (nums == null || nums.length == 0)return null;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        for (int num : nums) {
            temp.add(num);
        }
        backTracking2(nums.length,0,res,temp);
        return res;
    }

    private void backTracking2(int length, int index, List<List<Integer>> res, List<Integer> temp) {
        if (index == length){
            res.add(new ArrayList<>(temp));
            return;
        }else {
            for (int i = index; i < length; i++) {
                Collections.swap(temp,index,i);
                backTracking2(length,index + 1,res,temp);
                Collections.swap(temp,index,i);
            }
        }
    }

    /**
     * 借助visited数组 来判断是否已经遍历过 可以不用交换，容易理解
     * @param nums
     * @return
     */
    public List<List<Integer>> permute3(int[] nums) {
        int len = nums.length;
        // 使用一个动态数组保存所有可能的全排列
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        boolean[] used = new boolean[len];
        Deque<Integer> path = new ArrayDeque<>(len);

        dfs(nums, len, 0, path, used, res);
        return res;
    }

    private void dfs(int[] nums, int len, int depth,
                     Deque<Integer> path, boolean[] used,
                     List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;

                System.out.println("  递归之前 => " + path);
                dfs(nums, len, depth + 1, path, used, res);

                used[i] = false;
                path.removeLast();
                System.out.println("递归之后 => " + path);
            }
        }
    }


    public static void main(String[] args) {
        Permute permute = new Permute();
        List<List<Integer>> res = permute.permute2(new int[]{1, 2, 3});
        res.forEach(t->{
            System.out.println(t);
        });
    }
}
