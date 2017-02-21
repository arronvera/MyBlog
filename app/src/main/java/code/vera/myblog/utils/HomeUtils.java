package code.vera.myblog.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import code.vera.myblog.bean.Emoji;
import code.vera.myblog.view.other.CustomClickableSpan;
import ww.com.core.Debug;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class HomeUtils {
    private static final String AT = "@[\u4e00-\u9fa5\\w]+";// @人
    private static final String TOPIC = "#[\u4e00-\u9fa5\\w]+#";// ##话题
    private static final String EMOJI = "\\[(\\S+?)\\]";// 表情
    private static final String URL = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";// url
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
//            for (int i = 0; i <m.groupCount(); i++) {
                sb.setSpan(ccs, m.start(), m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
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

    /**
     * 设置weib样式
     * @param source
     * @param context
     * @return
     */
    public static SpannableString getWeiBoContent(CustomClickableSpan ccsAt,
                                                  CustomClickableSpan ccsTopic,
                                                  CustomClickableSpan ccsUrl, String source, Context context) {
        SpannableString spannableString = new SpannableString(source);
        String REGEX="(" +AT+ ")|(" +TOPIC+ ")|("+URL+")|("+EMOJI+")";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(spannableString);
//        if (matcher.find()) {
//            textView.setMovementMethod(LinkMovementMethod.getInstance());
//            matcher.reset();
//        }
        while (matcher.find()) {
            for(int i=1;i<=matcher.groupCount();i++){
                Debug.d("group="+matcher.group(i));
            }
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            final String url  = matcher.group(3);
            final String emoji= matcher.group(4);
            if (at != null) {
                int start = matcher.start(1);
                int end = start + at.length();
                spannableString.setSpan(ccsAt, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();
                spannableString.setSpan(ccsTopic, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (url != null) {
                int start = matcher.start(3);
                int end = start + url.length();
                spannableString.setSpan(ccsUrl, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (emoji != null) {//表情
                int start = matcher.start(4);
                int end = start + emoji.length();
                Iterator<Emoji> iterator;
                Emoji e = null;
                iterator = EmojiUtil.getEmojiList().iterator();
                while (iterator.hasNext()) {
                    e = iterator.next();
                    if (emoji.equals(e.getValue())) {
                        spannableString.setSpan(new ImageSpan(context, EmojiUtil.decodeSampledBitmapFromResource(context.getResources(), e.getDrawable()
                                , EmojiUtil.dip2px(context, 18), EmojiUtil.dip2px(context, 18))),
                                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                }
            }
        }
        return spannableString;
    }
}
