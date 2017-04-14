package code.vera.myblog.view.message;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/2/10 0010.
 */

public class MessageView extends BaseView {

    @BindView(R.id.tab_message_detail)
    TabLayout tabLayout;
    @BindView(R.id.vp_message_view)
    ViewPager vpMessage;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);

    }
    public void setAdapter(FragmentPagerAdapter adapter){
        //给ViewPager设置适配器
        vpMessage.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpMessage);
        //给Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
