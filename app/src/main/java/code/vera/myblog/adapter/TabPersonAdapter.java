package code.vera.myblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.presenter.fragment.person.TabPersonAllCircleFragment;
import code.vera.myblog.presenter.fragment.person.TabPersonInfoFragment;
import code.vera.myblog.presenter.fragment.person.TabPersonPhotosFragment;

/**
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonAdapter  extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    // 标题数组
    String[] titles = {"关于", "圈子","相册"};

    public TabPersonAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(TabPersonInfoFragment.getInstance());
        fragmentList.add(TabPersonAllCircleFragment.getInstance());
        fragmentList.add(TabPersonPhotosFragment.getInstance());

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
