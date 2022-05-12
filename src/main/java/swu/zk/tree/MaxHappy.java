package swu.zk.tree;

import java.util.List;

/**
 * @Classname MaxHappy
 * @Description
 * 派对的最大快乐值
 *  公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、没有环的多叉树。
 *  树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。
 *  叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级
 *
 *  派对的最大快乐值
 * 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
 * 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 * 2.派对的整体快乐值是所有到场员工快乐值的累加
 * 3.你的目标是让派对的整体快乐值尽量大
 * 给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
 * @Date 2022/5/11 21:53
 * @Created by brain
 */
public class MaxHappy {

   private static class Employee {
        public int happy; // 这名员工可以带来的快乐值
        List<Employee> subordinates; // 这名员工有哪些直接下级
    }

    public static Integer maxHappy(Employee boss) {
       if (boss == null) return 0;
       return doMaxHappy(boss,false);
    }

    private static Integer doMaxHappy(Employee boss, boolean up) {
       //上级来
       if (up){
           Integer p1 = 0;
           for (Employee subordinate : boss.subordinates) {
               p1 += doMaxHappy(subordinate,false);
           }
           return p1;
       }else {
           //上级不来 1.本人可以选择来 2. 可以选择不来
           int p1 = boss.happy;
           int p2 = 0;
           for (Employee subordinate : boss.subordinates) {
               p1 += doMaxHappy(subordinate,true);
               p2 += doMaxHappy(subordinate,false);
           }
           //来与不来的最大值
           return Math.max(p1,p2);
       }
    }

    /**
     * 二叉树递归套路
     * @param root
     * @return
     */
    public static Integer maxHappy2(Employee root) {
        Info allInfo = doMaxHappy2(root);
        return Math.max(allInfo.no, allInfo.yes);
    }

    private static class Info {
        public int no;
        public int yes;

        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }

    public static Info doMaxHappy2(Employee x) {
        if (x == null) {
            return new Info(0, 0);
        }
        int no = 0;
        int yes = x.happy;
        for (Employee next : x.subordinates) {
            Info nextInfo = doMaxHappy2(next);
            no += Math.max(nextInfo.no, nextInfo.yes);
            yes += nextInfo.no;

        }
        return new Info(no, yes);
    }

}
