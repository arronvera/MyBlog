package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;

/**
 * 排序
 * Created by vera on 2017/3/1 0001.
 */

public class SortFriendAdapter extends RvAdapter<UserInfoBean> implements SectionIndexer {

    private List<UserInfoBean> datas;

    public SortFriendAdapter(Context context) {
        super(context);
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i <datas.size(); i++) {
            String sortStr = datas.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return datas.get(position).getSortLetters().charAt(0);
    }


    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_sort;
    }

    @Override
    protected RvViewHolder<UserInfoBean> getViewHolder(int viewType, View view) {
        return new SortFriendViewHolder(view);
    }
    public class SortFriendViewHolder extends RvViewHolder<UserInfoBean> {
        @BindView(R.id.tv_catalog)
        TextView tvCatalog;//字母
        @BindView(R.id.tv_title)
        TextView tvTitle;//内容

        public SortFriendViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(int position, UserInfoBean bean) {
            tvCatalog.setText(bean.getSortLetters());
            tvTitle.setText(bean.getName());
        }
    }
}
