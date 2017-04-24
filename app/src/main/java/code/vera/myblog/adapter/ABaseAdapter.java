package code.vera.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vera on 2017/4/24 0024.
 */

public abstract class ABaseAdapter<T> extends BaseAdapter {
    private static final int _ID = 0x7f010000;
    private Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> datas;

    private View noDataView;

    public void setShowNoDataView(View _noDataView) {
        noDataView = _noDataView;
    }

    public ABaseAdapter(Context context) {
        super();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.datas = new ArrayList<T>();
    }


    public List<T> getList() {
        return datas;
    }

    public void appendList(List<T> list) {
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void appendListTop(List<T> list) {
        this.datas.addAll(0, list);
        notifyDataSetChanged();
    }

    public void changeItemAtIndex(int position, T bean) {
        if (this.datas != null) {
            if (this.datas.size() > position) {
                this.datas.set(position, bean);
                notifyDataSetChanged();
            }
        }
    }

    public void clearItems() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(T bean) {
        this.datas.add(bean);
        notifyDataSetChanged();
    }

    public void addFirstItem(T bean) {
        this.datas.add(0, bean);
        notifyDataSetChanged();
    }

    public void delItem(T bean) {
        this.datas.remove(bean);
        notifyDataSetChanged();
    }

    public void delItem(int index) {
        if (this.datas != null && this.datas.size() > index) {
            this.datas.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (datas != null && datas.size() > 0) {
            if (noDataView != null) {
                noDataView.setVisibility(View.GONE);
            }
            return datas.size();
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (datas != null && datas.size() > position) {
            return datas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return _ID + position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected abstract int getItemLayoutResId(int viewType);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IViewHolder<T> viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(getItemLayoutResId(getItemViewType(position)), null);
            viewHolder = getViewHolder(position);
            viewHolder.createView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IViewHolder<T>) convertView.getTag();
        }
        viewHolder.buildData(position, getItem(position));
        return convertView;
    }

    protected abstract IViewHolder<T> getViewHolder(int position);

    public Context getContext() {
        return mContext;
    }
}