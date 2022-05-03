package swu.zk.util;

/**
 * @Classname TimeUtil
 * @Description TODO
 * @Date 2022/5/3 9:57
 * @Created by brain
 */
public class TimeUtil {
    public static void getTime(Timer timer){
        long l1 = System.currentTimeMillis();
        System.out.println("execution start:" + l1);
        timer.execute();
        long l2 = System.currentTimeMillis();
        System.out.println("execution end:" + l2);
        System.out.println("execution time:" + (l2 - l1));
    }
}
