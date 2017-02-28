package code.vera.myblog.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**喜欢
 * Created by vera on 2017/2/28 0028.
 */

public class LikeView extends PopupWindow implements ILikeView {
    /**
     * 显示的文字
     */
    private String mText = TEXT;
    /**
     * 显示的文字效果
     */
    private int mTextColor = TEXT_COLOR;
    /**
     * 文字显示的大小
     */
    private int mTextSize = TEXT_SIZE;
    /**
     * 初始Y坐标
     */
    private int mFromY = FROM_Y_DELTA;
    /**
     * 终点Y坐标
     */
    private int mToY = TO_Y_DELTA;
    /**
     * 初始透明度
     */
    private float mFromAlpha = FROM_ALPHA;
    /**
     * 结束时的透明度
     */
    private float mToAlpha = TO_ALPHA;
    /**
     * 动画时长
     */
    private int mDuration = DURATION;
    /**
     * 移动的距离
     */
    private int mDistance = DISTANCE;
    /**
     * 动画
     */
    private AnimationSet mAnimationSet;
    /**
     * 各种参数是否改变的控制变量
     */
    private boolean mIsAnimateParamsChanged = false;

    private Context mContext = null;

    private TextView mGoodTv = null;

    public LikeView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    /**
     * 设置popwindow的布局
     * 以及设置宽高
     */
    private void initView() {
        RelativeLayout layout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mGoodTv = new TextView(mContext);
        mGoodTv.setIncludeFontPadding(false);
        mGoodTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        mGoodTv.setTextColor(mTextColor);
        mGoodTv.setText(mText);
        mGoodTv.setLayoutParams(params);
        layout.addView(mGoodTv);
        setContentView(layout);
        /**
         * 计算这个view的宽高
         */
        /*******************************************************************/
        //根据提供的大小值和模式，创建一个测量值(格式)
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);//未指定模式,子元素大小任意.
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mGoodTv.measure(w, h);
        setWidth(mGoodTv.getMeasuredWidth());
        setHeight(mDistance + mGoodTv.getMeasuredHeight());
        /*******************************************************************/
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(false);
        setTouchable(false);
        setOutsideTouchable(false);

        mAnimationSet = createAnimation();
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        mText = text;
        mGoodTv.setText(text);
        mGoodTv.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int w = (int) mGoodTv.getPaint().measureText(text);
        setWidth(w);
        setHeight(mDistance + getTextViewHeight(mGoodTv, w));
    }

    private static int getTextViewHeight(TextView textView, int width) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);//最多不超过width
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 设置文本颜色
     *
     * @param color
     */
    private void setTextColor(int color) {
        mTextColor = color;
        mGoodTv.setTextColor(color);
    }

    /**
     * 设置文本大小
     *
     * @param textSize
     */
    private void setTextSize(int textSize) {
        mTextSize = textSize;
        mGoodTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    /**
     * 设置文本信息
     *
     * @param text
     * @param textColor
     * @param textSize
     */
    public void setTextInfo(String text, int textColor, int textSize) {
        setTextColor(textColor);
        setTextSize(textSize);
        setText(text);
    }

    /**
     * 设置图片
     *
     * @param resId
     */
    public void setImage(int resId) {
        setImage(mContext.getResources().getDrawable(resId));
    }

    /**
     * 设置图片
     *
     * @param drawable
     */
    public void setImage(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("drawable cannot be null.");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mGoodTv.setBackground(drawable);
        } else {
            mGoodTv.setBackgroundDrawable(drawable);
        }
        /*******************************************************************/
        //drawable获取实际宽高的方式
        mGoodTv.setText("");
        setWidth(drawable.getIntrinsicWidth());//获取drawable实际宽高
        //加上mDistance 移动动画不会被遮盖
        setHeight(mDistance + drawable.getIntrinsicHeight());
        /*******************************************************************/

    }

    /**
     * 设置移动距离
     *
     * @param dis
     */
    public void setDistance(int dis) {
        mDistance = dis;
        mToY = dis;
        mIsAnimateParamsChanged = true;
        setHeight(mDistance + mGoodTv.getMeasuredHeight());
    }

    /**
     * 设置Y轴移动属性
     *
     * @param fromY
     * @param toY
     */
    public void setTranslateY(int fromY, int toY) {
        mFromY = fromY;
        mToY = toY;
        mIsAnimateParamsChanged = true;
    }

    /**
     * 设置透明度属性
     *
     * @param fromAlpha
     * @param toAlpha
     */
    public void setAlpha(float fromAlpha, float toAlpha) {
        mFromAlpha = fromAlpha;
        mToAlpha = toAlpha;
        mIsAnimateParamsChanged = true;
    }

    /**
     * 设置动画时长
     *
     * @param duration
     */
    public void setDuration(int duration) {
        mDuration = duration;
        mIsAnimateParamsChanged = true;
    }

    /**
     * 重置属性
     */
    public void reset() {
        mText = TEXT;
        mTextColor = TEXT_COLOR;
        mTextSize = TEXT_SIZE;
        mFromY = FROM_Y_DELTA;
        mToY = TO_Y_DELTA;
        mFromAlpha = FROM_ALPHA;
        mToAlpha = TO_ALPHA;
        mDuration = DURATION;
        mDistance = DISTANCE;
        mIsAnimateParamsChanged = false;
        mAnimationSet = createAnimation();
    }

    /**
     * 展示
     *
     * @param v
     */
    public void show(View v) {
        if (!isShowing()) {

            /**
             * 核心代码
             * *****************************************************************/
            int offsetX = v.getWidth() / 2 - getWidth() / 2;//X方向上的偏移量
            int offsetY = -v.getHeight() - getHeight();//Y方向上的偏移量
            showAsDropDown(v,offsetX , offsetY);
//            showAsDropDown(v, v.getWidth() / 2 - getWidth() / 2, -v.getHeight());

//            showAsDropDown(v, 0,0);

            if (mAnimationSet == null || mIsAnimateParamsChanged) {
                mAnimationSet = createAnimation();
                mIsAnimateParamsChanged = false;
            }
            /*******************************************************************/
            mGoodTv.startAnimation(mAnimationSet);
        }
    }

    /**
     * 动画
     *
     * @return
     */
    private AnimationSet createAnimation() {
        mAnimationSet = new AnimationSet(true);
        /**
         * 核心代码
         * *****************************************************************/
        TranslateAnimation translateAnim = new TranslateAnimation(0, 0, mFromY, -mToY);
        /*******************************************************************/
        AlphaAnimation alphaAnim = new AlphaAnimation(mFromAlpha, mToAlpha);
        mAnimationSet.addAnimation(translateAnim);
        mAnimationSet.addAnimation(alphaAnim);
        mAnimationSet.setDuration(mDuration);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShowing()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return mAnimationSet;
    }

}
