package code.vera.myblog;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import code.vera.myblog.adapter.MenuItemAdapter;
import code.vera.myblog.bean.MenuItem;
import code.vera.myblog.bean.UnReadBean;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.activity.SearchActivity;
import code.vera.myblog.presenter.fragment.MenuFragment;
import code.vera.myblog.presenter.fragment.collection.CollectionFragment;
import code.vera.myblog.presenter.fragment.draft.DraftFragment;
import code.vera.myblog.presenter.fragment.find.FindFragment;
import code.vera.myblog.presenter.fragment.home.HomeFragment;
import code.vera.myblog.presenter.fragment.message.MessageFragment;
import code.vera.myblog.presenter.fragment.set.SetFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.MainView;

import static code.vera.myblog.R.id.lv_left_menu;

/**
 * 主界面
 */
public class MainActivity extends PresenterActivity<MainView, UserModel>
        implements AdapterView.OnItemClickListener, MenuFragment.FragmentDrawerListener {
    @BindView(R.id.dl_left)
    DrawerLayout dlLeft;
    @BindView(lv_left_menu)
    ListView lvMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_title)
    ImageView ivTitle;

    private ActionBarDrawerToggle drawerToggle;
    private Activity activity;
    //使用集合来储存侧滑菜单的菜单项
    private ArrayList<MenuItem> menuList;
    //使用ArrayAdapter来对ListView的内容进行填充
    private MenuItemAdapter adapter;
    private FragmentManager fragmentManager;
    private boolean isExit = false;
    private long firstTime = 0;
    Fragment fragment = null;
    private UserInfoBean user;//当前用户
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initView();
        initData();
        setAdapter();
        addListener();
        fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

    private void addListener() {
        lvMenu.setOnItemClickListener(this);
    }

    private void setAdapter() {
        //初始化adapter
        adapter = new MenuItemAdapter(menuList, this);
        //为侧边菜单填充上内容
        lvMenu.setAdapter(adapter);
    }

    private void initData() {
        //初始化menulists
        menuList = new ArrayList<>();
        MenuItem item = new MenuItem();
        item.setText("首页");
        item.setPic(R.mipmap.ic_home);
        menuList.add(item);
        item = new MenuItem();
        item.setText("消息");
        item.setPic(R.mipmap.ic_message);
        menuList.add(item);
        item = new MenuItem();
        item.setPic(R.mipmap.ic_artical);
        item.setText("发现");
        menuList.add(item);
        item = new MenuItem();
        item.setText("收藏");
        item.setPic(R.mipmap.ic_mine);
        menuList.add(item);
        //草稿箱
        item = new MenuItem();
        item.setText("草稿箱");
        menuList.add(item);
        //设置
        item = new MenuItem();
        item.setPic(R.mipmap.ic_mine);
        item.setText("设置");
        menuList.add(item);
        //获取用户
        model.getUserInfo(this,"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,false){
            @Override
            public void onNext(UserInfoBean userInfoBean) {
                super.onNext(userInfoBean);
                view.showUser(userInfoBean);
                user=userInfoBean;
                //获取未读信息
                model.getUnreadCount(getApplicationContext(),userInfoBean.getId()+"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UnReadBean>(mContext,false){
                    @Override
                    public void onNext(UnReadBean unReadBean) {
                        super.onNext(unReadBean);
                        if (unReadBean!=null){
                            adapter.setUnreadBean(unReadBean);
                        }
                    }
                });
            }
        });

    }

    private void initView() {
        //设置toolbar菜单
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //开关
        drawerToggle = new ActionBarDrawerToggle(activity, dlLeft, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        dlLeft.setDrawerListener(drawerToggle);
        fragmentManager = getSupportFragmentManager();
        //menu监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit://发布
//                        Toast.makeText(MainActivity.this, "点击了编辑", Toast.LENGTH_SHORT).show();
                        //跳转
                        Intent intent=new Intent(MainActivity.this, PostActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_search://搜索
//                        Toast.makeText(MainActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                        //跳转
                        Intent intent2=new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
    }


    @OnClick({ R.id.iv_menu_close,R.id.civ_user_photo})
    public void doClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_sign_out:
//                //退出登录
//                DialogUtils.showDialog(this, "", "你是否确定注销登陆？", "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        logOut();
//                    }
//                }, "取消", null);
//                break;
            case R.id.iv_menu_close://关闭菜单
                dlLeft.closeDrawer(GravityCompat.START);
                break;
            case R.id.civ_user_photo://用户头像
                Intent intent=new Intent(this, PersonalityActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    /**
     * 跳转到对应Fragment
     *
     * @param position
     */
    private void selectItem(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                ivTitle.setImageResource(R.mipmap.ic_circle);
                break;
            case 1:
                fragment = new MessageFragment();
                ivTitle.setImageResource(R.mipmap.ic_title_message);

                break;
            case 2:
                fragment = new FindFragment();
                ivTitle.setImageResource(R.mipmap.ic_title_find);

                break;
            case 3:
                fragment = new CollectionFragment();
                ivTitle.setImageResource(R.mipmap.ic_me);

                break;
            case 4:
                fragment = new DraftFragment();
                ivTitle.setImageResource(R.mipmap.ic_draft);

                break;
            case 5://设置
                fragment=new SetFragment();
                ivTitle.setImageResource(R.mipmap.ic_set);

                break;
        }
        fragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();
        dlLeft.closeDrawer(GravityCompat.START);
        lvMenu.setItemChecked(position, true);

    }

    @Override
    public void onDrawerItemSelected(View view, int ption) {
        lvMenu.setOnItemClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//返回
            if (dlLeft.isDrawerOpen(GravityCompat.START)) {//如果菜单打开则关闭菜单
                dlLeft.closeDrawer(GravityCompat.START);
                return false;
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                       //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {//两次按键小于2秒时，退出应用
                  finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
