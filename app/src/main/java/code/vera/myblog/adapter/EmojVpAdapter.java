package code.vera.myblog.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by vera on 2017/2/16 0016.
 */

public class EmojVpAdapter extends PagerAdapter {
    // 界面列表
    private List<View> views;

    public EmojVpAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) (arg2));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    // 初始化arg1位置的界面
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1));
        return views.get(arg1);
    }

    // 判断是否由对象生成界
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}
