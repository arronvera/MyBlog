package code.vera.myblog.adapter;

import android.view.View;

import ww.com.core.ScreenUtil;

/**
 * Created by vera on 2017/4/24 0024.
 */

public abstract class IViewHolder<T> {
    protected View itemView;
    /**
     *
     * initView(这里用一句话描述这个方法的作用)
     *
     * @Title: initView  初始化布局
     * @Description:
     * @param v
     * @return void
     */
    public final void createView(View v){
        ScreenUtil.scale(v);
        this.itemView = v;
        initView();
    };

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int resId){
        return (T) itemView.findViewById(resId);
    }

    public abstract void initView();

    /**
     *
     * buildData(这里用一句话描述这个方法的作用)
     *
     * @Title: buildData  绑定数据
     * @Description:
     * @param obj
     * @return void
     */
    public abstract void buildData(int position, T obj);
}

