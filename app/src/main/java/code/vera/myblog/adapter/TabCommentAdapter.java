package code.vera.myblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.presenter.fragment.tab.TabCommentFragment;
import code.vera.myblog.presenter.fragment.tab.TabLikeFragment;
import code.vera.myblog.presenter.fragment.tab.TabRepostFragment;

/**
 * Created by vera on 2017/1/23 0023.
 */

public class TabCommentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    // 标题数组
    String[] titles = {"转发", "评论","喜欢"};

    public TabCommentAdapter(FragmentManager fm, StatusesBean statusesBean) {
        super(fm);
        if (statusesBean!=null){
            titles = new String[]{"转发"+statusesBean.getReposts_count(),
                    "评论"+statusesBean.getComments_count(), "喜欢"+statusesBean.getAttitudes_count()};
        }
        fragmentList.add(new TabRepostFragment());
        fragmentList.add(new TabCommentFragment());
        fragmentList.add(new TabLikeFragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
