package code.vera.myblog.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import code.vera.myblog.view.other.CustomClickableSpan;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class HomeUtils {
    /**
     * 处理话题
     * @param comment
     * @param content
     * @param context
     * @throws IOException
     */
    public static void handlerTopicText(CustomClickableSpan ccs,TextView comment, String content, Context context) throws IOException {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "#([^\\\\#|.]+)#";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()){
            for (int i = 0; i <m.groupCount(); i++) {
                sb.setSpan(ccs, m.start(), m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        comment.setText(sb);
    }
    /**
     * 处理@
     * @param comment
     * @param content
     * @param context
     * @throws IOException
     */
    public static void handlerAtSomeOneText(CustomClickableSpan ccs,TextView comment, String content, Context context) throws IOException {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "@[^ \\\\.^\\\\,^:^;^!^(^)^\\\\?^\\\\s^#^@^。^，^：^；^！^？^（^）]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()){
                sb.setSpan(ccs, m.start(), m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        comment.setText(sb);
    }
    /**
     * 处理Url
     * @param comment
     * @param content
     * @param context
     * @throws IOException
     */
    public static void handlerUrlText(CustomClickableSpan ccs,TextView comment, String content, Context context) throws IOException {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "/^(http?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()){
            sb.setSpan(ccs, m.start(), m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        comment.setText(sb);
    }
}
