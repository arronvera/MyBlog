package code.vera.myblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.presenter.fragment.message.tab.TabMessageAboutMeFragment;
import code.vera.myblog.presenter.fragment.message.tab.TabMessageByMeFragment;
import code.vera.myblog.presenter.fragment.message.tab.TabMessageToMeFragment;

/**
 * Created by vera on 2017/1/23 0023.
 */

public class TabMessageAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    // 标题数组
    String[] titles = {"@我的评论", "我发出的评论","我收到的评论"};

    public TabMessageAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new TabMessageAboutMeFragment());
        fragmentList.add(new TabMessageByMeFragment());
        fragmentList.add(new TabMessageToMeFragment());

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
