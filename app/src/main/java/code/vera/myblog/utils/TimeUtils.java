package code.vera.myblog.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class TimeUtils {
    /**
     * 转换时间格式
     * @param time
     * @return
     */
    public static String dateTransfer(String time){
        SimpleDateFormat sdf=new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        SimpleDateFormat sdf2=new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try{
            return sdf2.format(sdf.parse(time));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
