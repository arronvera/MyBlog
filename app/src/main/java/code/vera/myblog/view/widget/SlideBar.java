package code.vera.myblog.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import code.vera.myblog.listener.OnTouchingLetterChangedListener;


/**
 * 排序右方导航栏
 * Created by vera on 2017/3/1 0001.
 */

public class SlideBar extends View {
    //导航栏字母
    public static String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    //画笔
    Paint paint=new Paint();
    //选择
    private int choose = -1;
    //监听
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private TextView textviewDialog;
    //设置弹出对话框
    public void setTextView(TextView mTextDialog) {
        this.textviewDialog = mTextDialog;
    }
    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getWidth();//宽
        int height=getHeight();//高
        int singleHeight = height / letters.length;//每个的高
        for (int i=0;i<letters.length;i++){
            paint.setColor(Color.parseColor("#000000"));//画笔颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);//加粗字体
            paint.setAntiAlias(true);//锯齿
            paint.setTextSize(20);//字的大小
            if (choose==i){//选中
                paint.setColor(Color.parseColor("#ff8162"));//选中画笔颜色
                paint.setFakeBoldText(true);//粗体
            }
            //xy坐标
            float xPos = width / 2 - paint.measureText(letters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            //画图
            canvas.drawText(letters[i], xPos, yPos, paint);
            paint.reset();//重置画笔
        }

    }
        //处理触摸事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action=event.getAction();
        float y=event.getY();
        int oldchoose=choose;
        OnTouchingLetterChangedListener listener=onTouchingLetterChangedListener;
         int c = (int) (y / getHeight() * letters.length);
        switch (action) {
            case MotionEvent.ACTION_UP://抬起
                setBackgroundDrawable(new ColorDrawable(0x00000000));//透明
                choose = -1;//没有选择
                invalidate();//更新
                //弹出框
//                if (textviewDialog != null) {
//                    textviewDialog.setVisibility(View.INVISIBLE);
//                }

                break;
            default:
                //设置背景
//                setBackgroundResource(R.drawable.sidebar_background);
                if (oldchoose != c) {
                    if (c >= 0 && c < letters.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letters[c]);
                        }
//                        if (textviewDialog != null) {
//                            textviewDialog.setText(letters[c]);
//                            textviewDialog.setVisibility(View.VISIBLE);
//                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 字母改变监听
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
}
