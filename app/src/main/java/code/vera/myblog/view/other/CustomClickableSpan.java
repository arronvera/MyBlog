package code.vera.myblog.view.other;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;

/**
 * spannable自定义按下与抬起效果颜色
 * Created by Administrator on 2016/9/29 0029.
 */
public abstract class CustomClickableSpan extends ClickableSpan{
    private int action=-1;
    public void setAction(int action) {
        this.action = action;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        int color = Color.parseColor("#ff8162");
        switch (action){
            case MotionEvent.ACTION_DOWN:
                color = Color.parseColor("#FF4081");
                break;
            case MotionEvent.ACTION_UP:
                color = Color.parseColor("#ff8162");
                break;
        }
        ds.setColor(color);//颜色
        ds.setUnderlineText(false);//没有下划线
    }

}
