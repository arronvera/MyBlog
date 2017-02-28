package code.vera.myblog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                "yyyy-MM-dd HH:mm");//年 -月-日
        SimpleDateFormat sdf3=new SimpleDateFormat(
                "MM-dd HH:mm");//月-日
        SimpleDateFormat sdf4=new SimpleDateFormat(
                "HH:mm");//月-日
        String str;
        try {
            Date publishDate=sdf.parse(time);
            Date nowDate=new Date(System.currentTimeMillis());//当前时间
            if (nowDate.getYear()==publishDate.getYear()&&nowDate.getDay()==publishDate.getDay()){//同一天
                str=sdf3.format(publishDate);
            }else if (nowDate.getYear()==publishDate.getYear()&&nowDate.getDay()==publishDate.getDay()){//同一年
                str=sdf3.format(publishDate);
            }else{
                str=sdf2.format(publishDate);
            }

            return str;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
