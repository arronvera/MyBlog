package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;

import code.vera.myblog.R;
import code.vera.myblog.bean.PostBean;

/**
 * Created by vera on 2017/2/22 0022.
 */

public class DraftAdapter extends RvAdapter<PostBean> {
    public DraftAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_draft;
    }

    @Override
    protected RvViewHolder<PostBean> getViewHolder(int viewType, View view) {
        return new DraftViewHolder(view);
    }

    private class DraftViewHolder extends RvViewHolder<PostBean> {
        public DraftViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(int position, PostBean bean) {

        }
    }
}
