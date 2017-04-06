package code.vera.myblog.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import code.vera.myblog.bean.Emoji;
import code.vera.myblog.bean.UrlBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
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
     * 设置weib样式(带监听)
     * @param source
     * @param context
     * @return
     */
    public static SpannableStringBuilder getWeiBoContent(final OnItemAtListener onItemAtListener, final OnItemTopicListener onItemTopicListener,
                                                         final OnItemLinkListener onItemLinkListener, String source, Context context, final int position
                                                            , TextView textView) {
        final SpannableStringBuilder spannableString = new SpannableStringBuilder(source);
        String REGEX="(" +AT+ ")|(" +TOPIC+ ")|("+URL+")|("+EMOJI+")";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(spannableString);
        if (matcher.find()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }
        while (matcher.find()) {
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            final String url  = matcher.group(3);
            final String emoji= matcher.group(4);
            if (at != null) {
                int start = matcher.start(1);
                int end = start + at.length();
                CustomClickableSpan ccsAt=new CustomClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (onItemAtListener!=null)
                        onItemAtListener.onItemAtListener(widget,position,at);
                    }
                };
                spannableString.setSpan(ccsAt, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();
                CustomClickableSpan ccsTopic=new CustomClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (onItemTopicListener!=null)
                        onItemTopicListener.onItemTopicListener(widget,position,topic);
                    }
                };
                spannableString.setSpan(ccsTopic, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (url != null) {//链接
                final int start = matcher.start(3);
                final int end = start + url.length();
                Debug.d("url="+url);

                if (url.length()<20){//短链接
                    HomeModel.shortUrlExpand(context,url,new CustomSubscriber<List<UrlBean>>(context,false){
                        @Override
                        public void onNext(List<UrlBean> urlBeen) {
                            super.onNext(urlBeen);
                            Debug.d("转换的长链接url"+urlBeen.get(0).getUrl_long());
                            final int type=urlBeen.get(0).getType();
                            CustomClickableSpan ccsLink=new CustomClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    if (onItemLinkListener!=null){
                                        onItemLinkListener.onItemLinkListener(widget,position,url,type);
                                    }
                                }
                            };
                            spannableString.setSpan(ccsLink, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    });
                }else{
                    CustomClickableSpan ccsLink=new CustomClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            if (onItemLinkListener!=null){
                                onItemLinkListener.onItemLinkListener(widget,position,url,0);
                            }
                        }
                    };
                    spannableString.setSpan(ccsLink, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

//                if (url.length()<20){
//                    spannableString.replace(start,end,"跳转链接");
//                }
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
