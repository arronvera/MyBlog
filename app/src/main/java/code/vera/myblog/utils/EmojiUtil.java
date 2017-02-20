package code.vera.myblog.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import code.vera.myblog.R;
import code.vera.myblog.bean.Emoji;


public class EmojiUtil {
    private static ArrayList<Emoji> emojiList;

    /**
     * 获取表情集合
     * @return
     */
    public static ArrayList<Emoji> getEmojiList() {
        if (emojiList == null) {
            emojiList = generateEmojis();
        }
        return emojiList;
    }

    /**
     * 生成表情对象集合
     * @return
     */
    private static ArrayList<Emoji> generateEmojis() {
        ArrayList<Emoji> list = new ArrayList<>();
        for (int i = 0; i < EmojiResArray.length; i++) {
            Emoji emoji = new Emoji();
            emoji.setDrawable(EmojiResArray[i]);
            emoji.setValue(EmojiTextArray[i]);
            list.add(emoji);
        }
        return list;
    }


    public static final int[] EmojiResArray = {
            R.drawable.d_huaixiao,
            R.drawable.d_xixi,
            R.drawable.d_tianping,
            R.drawable.d_wu,
            R.drawable.d_weixiao,
            R.drawable.d_haha,
            R.drawable.d_keai,
            R.drawable.d_wabi,
            R.drawable.d_chijing,
            R.drawable.d_jiyan,
            R.drawable.d_kelian,
            R.drawable.d_aini,
            R.drawable.d_bishi,
            R.drawable.d_bizui,
            R.drawable.d_doge,
            R.drawable.d_touxiao,
            R.drawable.d_qinqin,
            R.drawable.d_shengbing,
            R.drawable.d_taikaixin,
            R.drawable.d_baiyan,
            R.drawable.d_youhengheng,
            R.drawable.d_zuohengheng,
            R.drawable.d_xu,
            R.drawable.d_shuai,
            R.drawable.d_weiqu,
            R.drawable.d_tu,


    };

    public static final String[] EmojiTextArray = {
            "[坏笑]",
            "[嘻嘻]",
            "[舔屏]",
            "[污]",
            "[微笑]",
            "[哈哈]",
            "[可爱]",
            "[挖鼻]",
            "[吃惊]",
            "[挤眼]",
            "[可怜]",
            "[爱你]",
            "[鄙视]",
            "[闭嘴]",
            "[doge]",
            "[偷笑]",
            "[亲亲]",
            "[生病]",
            "[太开心]",
            "[白眼]",
            "[右哼哼]",
            "[左哼哼]",
            "[嘘]",
            "[衰]",
            "[委屈]",
            "[吐]",

    };

    static {
        emojiList = generateEmojis();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 处理表情文本
     * @param comment
     * @param content
     * @param context
     * @throws IOException
     */
    public static void handlerEmojiText(TextView comment, String content, Context context) throws IOException {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "\\[(\\S+?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        Iterator<Emoji> iterator;
        Emoji emoji = null;
        while (m.find()) {
            iterator = emojiList.iterator();
            String tempText = m.group();
            while (iterator.hasNext()) {
                emoji = iterator.next();
                if (tempText.equals(emoji.getValue())) {
                    //转换为Span并设置Span的大小
                    sb.setSpan(new ImageSpan(context, decodeSampledBitmapFromResource(context.getResources(), emoji.getDrawable()
                                    , dip2px(context, 18), dip2px(context, 18))),
                            m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        comment.setText(sb);
    }
}
