package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.view.View;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.RetweetedStatusBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.CommentDetailModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.CommentDetailView;

/**
 * 评论详情
 */
public class CommentDetailActivity extends PresenterActivity<CommentDetailView, CommentDetailModel>
        implements OnItemAtListener,OnItemTopicListener,OnItemLinkListener {


    public static long id;
    private StatusesBean statusesBean;
    private ContextMenuDialogFragment mMenuDialogFragment;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
//        initMenuFragment();
        Intent intent = getIntent();
        //
        StatusesBean statusesBean= (StatusesBean) intent.getSerializableExtra("status");
        if (statusesBean!=null&&statusesBean.getId()!=0){
            id=statusesBean.getId();
            view.showInfo(statusesBean);
            this.statusesBean=statusesBean;
        }
        //
        RetweetedStatusBean retweetedStatusBean= (RetweetedStatusBean) intent.getSerializableExtra("retweeted_status");
        if (retweetedStatusBean!=null&&retweetedStatusBean.getId()!=0){
            id=retweetedStatusBean.getId();
            view.showInfo2(retweetedStatusBean);
        }
        view.setActivity(this);
        view.setAdapter();
        view.setOnItemAtListener(this);
        view.setOnItemTopicListener(this);
        view.setOnItemLinkListener(this);
    }

//    private void initMenuFragment() {
//        MenuParams menuParams = new MenuParams();
//        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
//        menuParams.setMenuObjects(getMenuObjects());
//        menuParams.setClosableOutside(false);
//        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
//        mMenuDialogFragment.setItemClickListener(this);
//        mMenuDialogFragment.setItemLongClickListener(this);
//    }
//
//    private List<MenuObject> getMenuObjects() {
//
//        List<MenuObject> menuObjects = new ArrayList<>();
//
//        MenuObject close = new MenuObject();
//        close.setResource(R.drawable.icn_close);
//
//        MenuObject send = new MenuObject("Send message");
//        send.setResource(R.drawable.icn_1);
//
//        MenuObject like = new MenuObject("Like profile");
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
//        like.setBitmap(b);
//
//        MenuObject addFr = new MenuObject("Add to friends");
//        BitmapDrawable bd = new BitmapDrawable(getResources(),
//                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
//        addFr.setDrawable(bd);
//
//        MenuObject addFav = new MenuObject("Add to favorites");
//        addFav.setResource(R.drawable.icn_4);
//
//        MenuObject block = new MenuObject("Block user");
//        block.setResource(R.drawable.icn_5);
//
//        menuObjects.add(close);
//        menuObjects.add(send);
//        menuObjects.add(like);
//        menuObjects.add(addFr);
//        menuObjects.add(addFav);
//        menuObjects.add(block);
//        return menuObjects;
//    }

    @OnClick({R.id.tv_item_comment,R.id.tv_item_repost,R.id.rl_item_like})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.tv_item_comment://评论
                Intent intent=new Intent(CommentDetailActivity.this,PostActivity.class);
                intent.putExtra("type", Constants.COMMENT_TYPE);
                startActivity(intent);
                break;
            case R.id.tv_item_repost://转发
                Intent intent2=new Intent(CommentDetailActivity.this,PostActivity.class);
                intent2.putExtra("type", Constants.REPOST_TYPE);
                intent2.putExtra("statusesBean",statusesBean);
                startActivity(intent2);
                break;
            case R.id.rl_item_like:
                //todo喜欢


                break;
        }
    }
    @OnClick({R.id.crv_photo,R.id.iv_shoucang})
    public void doCLick(View v){
        switch (v.getId()){
            case R.id.crv_photo://头像
                Intent intent=new Intent(this,PersonalityActivity.class);
                intent.putExtra("user",statusesBean.getUserBean());
                startActivity(intent);
                break;
            case R.id.iv_shoucang://收藏
                //todo 收藏


                break;
        }
    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Intent intent=new Intent(this, PersonalityActivity.class);
        intent.putExtra("user_name",str.substring(str.indexOf("@")+1,str.length()));
        startActivity(intent);
    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        //todo 话题
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str) {
        //链接
        Intent intent=new Intent(this,BrowserActivity.class);
        intent.putExtra("link",str);
        startActivity(intent);
    }
}
