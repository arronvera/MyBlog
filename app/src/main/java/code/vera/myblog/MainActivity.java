package code.vera.myblog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import code.vera.myblog.adapter.MenuItemAdapter;
import code.vera.myblog.bean.CollectionBean;
import code.vera.myblog.bean.MenuItem;
import code.vera.myblog.bean.UnReadBean;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.fragment.MenuFragment;
import code.vera.myblog.presenter.fragment.collection.CollectionFragment;
import code.vera.myblog.presenter.fragment.draft.DraftFragment;
import code.vera.myblog.presenter.fragment.find.FindFragment;
import code.vera.myblog.presenter.fragment.home.HomeFragment;
import code.vera.myblog.presenter.fragment.message.MessageFragment;
import code.vera.myblog.presenter.fragment.set.SetFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.SaveUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.MainView;

import static code.vera.myblog.R.id.lv_left_menu;
import static code.vera.myblog.presenter.activity.PersonalityActivity.BUNDLER_PARAM_USER;
import static code.vera.myblog.presenter.activity.PostActivity.ACTION_SAVE_DRAFT;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.fragment.draft.DraftFragment.ACTION_DELETE_DRAFT;
import static code.vera.myblog.presenter.fragment.home.HomeFragment.ACTION_CLEAR_UNREAD;
import static code.vera.myblog.presenter.fragment.home.HomeFragment.ACTION_UPDATE_FAVORITE;

/**
 * 主界面
 */
public class MainActivity extends PresenterActivity<MainView, UserModel>
        implements AdapterView.OnItemClickListener,
        MenuFragment.FragmentDrawerListener,
        Toolbar.OnMenuItemClickListener{
    @BindView(R.id.dl_left)
    DrawerLayout dlLeft;
    @BindView(lv_left_menu)
    ListView lvMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ActionBarDrawerToggle drawerToggle;
    //使用集合来储存侧滑菜单的菜单项
    private ArrayList<MenuItem> menuList;
    //使用ArrayAdapter来对ListView的内容进行填充
    private MenuItemAdapter adapter;
    private FragmentManager fragmentManager;
    private long firstTime = 0;
    Fragment fragment = null;
    private UserInfoBean user;//当前用户
    private RefreshBroadCastReceiver broadcastReceiver;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        //注册广播
        broadcastReceiver=new RefreshBroadCastReceiver();
        broadcastReceiver.registRecevier();
        initView();
        initData();
        setAdapter();
        addListener();
        fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

    private void addListener() {
        toolbar.setOnMenuItemClickListener(this);
    }

    private void setAdapter() {
        adapter = new MenuItemAdapter(menuList, this);
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
        item.setPic(R.mipmap.ic_diamond);
        menuList.add(item);
        //草稿箱
        item = new MenuItem();
        item.setText("草稿箱");
        item.setPic(R.mipmap.ic_menu_draft);
        menuList.add(item);
        //设置
        item = new MenuItem();
        item.setPic(R.mipmap.ic_menu_set);
        item.setText("设置");
        menuList.add(item);
        //获取用户
        model.getUserInfo(this,"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,false){
            @Override
            public void onNext(UserInfoBean userInfoBean) {
                super.onNext(userInfoBean);
                if (userInfoBean!=null){
                    view.showUser(userInfoBean);
                    //存储
                    SaveUtils.saveUser(userInfoBean,MainActivity.this);
                    user=userInfoBean;
                }
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
                //获取收藏个数
                getFavorites();
            }
        });
    }
    @OnItemClick(R.id.lv_left_menu)
    public void onItemClick(int position){
        selectItem(position);
    }
    private void getFavorites() {
        model.getFavorites(50,1,mContext,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<List<CollectionBean>>(mContext,false){
            @Override
            public void onNext(List<CollectionBean> collectionBeen) {
                super.onNext(collectionBeen);
                if (collectionBeen!=null&&collectionBeen.size()!=0){
                    adapter.setFaviroteNum(collectionBeen.size());
                }
            }
        });
    }

    private void initView() {
        //设置toolbar菜单
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //开关
        drawerToggle = new ActionBarDrawerToggle(this, dlLeft, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        dlLeft.setDrawerListener(drawerToggle);
        fragmentManager = getSupportFragmentManager();
    }


    @OnClick({ R.id.iv_menu_close,R.id.civ_user_photo})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu_close://关闭菜单
                dlLeft.closeDrawer(GravityCompat.START);
                break;
            case R.id.civ_user_photo://用户头像
                Intent intent=new Intent(this, PersonalityActivity.class);
                intent.putExtra(BUNDLER_PARAM_USER,user);
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
                view.setImageResouce(R.mipmap.ic_circle);
                break;
            case 1:
                fragment = new MessageFragment();
                view.setImageResouce(R.mipmap.ic_title_message);
                break;
            case 2:
                fragment = new FindFragment();
                view.setImageResouce(R.mipmap.ic_title_find);
                break;
            case 3:
                fragment = new CollectionFragment();
                view.setImageResouce(R.mipmap.ic_me);
                break;
            case 4:
                fragment = new DraftFragment();
                view.setImageResouce(R.mipmap.ic_draft);
                break;
            case 5://设置
                fragment=new SetFragment();
                view.setImageResouce(R.mipmap.ic_set);
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

    @Override
    public boolean onMenuItemClick(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit://发布
                Bundle bundle=new Bundle();
                bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_NEW);
                PostActivity.start(this,bundle);
                break;
            case R.id.action_search://搜索
                ToastUtil.showToast(this,"由于当前微博Api不提供权限，当前功能不提供");
//                SearchActivity.start(this);
                break;
        }
        return false;
    }
    public static void start(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    class RefreshBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_FAVORITE)){
                getFavorites();
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        public void registRecevier() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_SAVE_DRAFT);
            filter.addAction(ACTION_DELETE_DRAFT);
            filter.addAction(ACTION_CLEAR_UNREAD);//清空未读
            filter.addAction(ACTION_UPDATE_FAVORITE);//更新收藏
            mContext.registerReceiver(this, filter);
        }
        public void unRgistRecevier() {
            mContext.unregisterReceiver(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastReceiver.unRgistRecevier();
    }
}
