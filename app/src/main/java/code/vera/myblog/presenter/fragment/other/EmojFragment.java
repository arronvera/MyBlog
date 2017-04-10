package code.vera.myblog.presenter.fragment.other;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.EmojGvAdapter;
import code.vera.myblog.adapter.EmojVpAdapter;
import code.vera.myblog.bean.Emoji;
import code.vera.myblog.manager.RecentEmojiManager;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.utils.EmojiUtil;
import code.vera.myblog.view.EmojView;
import code.vera.myblog.view.widget.EmojiIndicatorView;

/**
 * 表情
 * Created by vera on 2017/2/15 0015.
 */

public class EmojFragment extends PresenterFragment<EmojView,VoidModel>   {
    @BindView(R.id.face_viewPager)
    ViewPager faceViewPager;
    @BindView(R.id.face_indicator)
    EmojiIndicatorView faceIndicator;
    @BindView(R.id.face_recent)
    TextView faceRecentTv;
    @BindView(R.id.face_first_set)
    TextView faceFirstSetTv;
    @BindView(R.id.face_anti)
    TextView faceAntiTv;
    @BindView(R.id.face_cartoon)
    TextView faceCartoonTv;
    ArrayList<View> ViewPagerItems = new ArrayList<>();
    ArrayList<Emoji> emojiList;
    ArrayList<Emoji> recentlyEmojiList;
    ArrayList<Emoji> antiEmojiList;
    ArrayList<Emoji> carttonEmojiList;

    private int columns = 7;//7列
    private int rows = 3;

    private OnEmojiClickListener listener;
    private RecentEmojiManager recentManager;

    public void setListener(OnEmojiClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_emoj;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if (activity instanceof OnEmojiClickListener) {
            this.listener = (OnEmojiClickListener) activity;
        }
        recentManager = RecentEmojiManager.make(activity);
        emojiList = EmojiUtil.getEmojiList();
        //表情添加
        antiEmojiList=new ArrayList<>();
        carttonEmojiList=new ArrayList<>();
        try {
            if (recentManager.getCollection(RecentEmojiManager.PREFERENCE_NAME) != null) {
                recentlyEmojiList = (ArrayList<Emoji>) recentManager.getCollection(RecentEmojiManager.PREFERENCE_NAME);
            } else {
                recentlyEmojiList = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initViews();
    }
    private void initViews() {
        initViewPager(emojiList);
        faceFirstSetTv.setSelected(true);
    }
    private void initViewPager(ArrayList<Emoji> list) {
        intiIndicator(list);
        ViewPagerItems.clear();
        for (int i = 0; i < getPagerCount(list); i++) {
            ViewPagerItems.add(getViewPagerItem(i, list));
        }
        setAdapter();
    }

    private void setAdapter() {
        EmojVpAdapter vpAdapter = new EmojVpAdapter(ViewPagerItems);
        faceViewPager.setAdapter(vpAdapter);
        faceViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                faceIndicator.playBy(oldPosition, position);
                oldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void intiIndicator(ArrayList<Emoji> list) {
        faceIndicator.init(getPagerCount(list));
    }

    @OnClick({R.id.face_first_set,R.id.face_recent,R.id.face_anti,R.id.face_cartoon})
    public void doClick(View v) {
        switch (v.getId()){
            case R.id.face_first_set://默认
                if (faceIndicator.getVisibility() == View.GONE) {
                    faceIndicator.setVisibility(View.VISIBLE);
                }
                if (!faceFirstSetTv.isSelected()) {
                    faceFirstSetTv.setSelected(true);
                    initViewPager(emojiList);
                }
                faceRecentTv.setSelected(false);
                faceCartoonTv.setSelected(false);
                faceAntiTv.setSelected(false);
                break;
            case R.id.face_recent://最近
                if (faceIndicator.getVisibility() == View.VISIBLE) {
                    faceIndicator.setVisibility(View.GONE);
                }
                if (!faceRecentTv.isSelected()) {
                    faceRecentTv.setSelected(true);
                    initViewPager(recentlyEmojiList);
                }
                faceFirstSetTv.setSelected(false);
                faceCartoonTv.setSelected(false);
                faceAntiTv.setSelected(false);
                break;
            case R.id.face_anti://漫画
                if (faceIndicator.getVisibility() == View.GONE) {
                    faceIndicator.setVisibility(View.VISIBLE);
                }
                if (!faceAntiTv.isSelected()) {
                    faceAntiTv.setSelected(true);
                    initViewPager(antiEmojiList);
                }
                faceRecentTv.setSelected(false);
                faceCartoonTv.setSelected(false);
                faceFirstSetTv.setSelected(false);
                break;
            case R.id.face_cartoon://卡通
                if (faceIndicator.getVisibility() == View.VISIBLE) {
                    faceIndicator.setVisibility(View.GONE);
                }
                if (!faceCartoonTv.isSelected()) {
                    faceCartoonTv.setSelected(true);
                    initViewPager(carttonEmojiList);
                }
                faceRecentTv.setSelected(false);
                faceAntiTv.setSelected(false);
                faceFirstSetTv.setSelected(false);
                break;
        }

    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return
     */
    private int getPagerCount(ArrayList<Emoji> list) {
        int count = list.size();
        return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
                : count / (columns * rows - 1) + 1;
    }

    private View getViewPagerItem(int position, ArrayList<Emoji> list) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_face_grid, null);//表情布局
        GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
        /**
         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
         * */
        final List<Emoji> subList = new ArrayList<>();
        subList.addAll(list.subList(position * (columns * rows - 1),
                (columns * rows - 1) * (position + 1) > list
                        .size() ? list.size() : (columns
                        * rows - 1)
                        * (position + 1)));
        /**
         * 末尾添加删除图标
         * */
        if (subList.size() < (columns * rows - 1)) {
            for (int i = subList.size(); i < (columns * rows - 1); i++) {
                subList.add(null);
            }
        }
        Emoji deleteEmoji = new Emoji();
        deleteEmoji.setDrawable(R.drawable.compose_emotion_delete);
        subList.add(deleteEmoji);
        EmojGvAdapter emojGvAdapter = new EmojGvAdapter(subList, getActivity());
        gridview.setAdapter(emojGvAdapter);
        gridview.setNumColumns(columns);
        // 单击表情执行的操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == columns * rows - 1) {
                    if(listener != null){
                        listener.onEmojiDelete();
                    }
                    return;
                }
                if(listener != null){
                    listener.onEmojiClick(subList.get(position));
                }
                insertToRecentList(subList.get(position));
            }
        });

        return gridview;
    }

    private void insertToRecentList(Emoji emoji) {
        if (emoji != null) {
            if (recentlyEmojiList.contains(emoji)) {
                //如果已经有该表情，就把该表情放到第一个位置
                int index = recentlyEmojiList.indexOf(emoji);
                Emoji emoji0 = recentlyEmojiList.get(0);
                recentlyEmojiList.set(index, emoji0);
                recentlyEmojiList.set(0, emoji);
                return;
            }
            if (recentlyEmojiList.size() == (rows * columns - 1)) {
                //去掉最后一个
                recentlyEmojiList.remove(rows * columns - 2);
            }
            recentlyEmojiList.add(0, emoji);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            recentManager.putCollection(RecentEmojiManager.PREFERENCE_NAME, recentlyEmojiList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public interface OnEmojiClickListener {
        void onEmojiDelete();

        void onEmojiClick(Emoji emoji);
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();

    }
}
