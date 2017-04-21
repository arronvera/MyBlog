package code.vera.myblog.utils;

/**
 * Created by vera on 2017/4/21 0021.
 */

public class NumUtils {
    /**
     * 计算万
     *
     * @param num
     * @return
     */
    public static String CaculateWan(long num) {
        if (num > 10000) {
            num = num / 10000;
            return num + "万";
        }
        return num + "";
    }
}
