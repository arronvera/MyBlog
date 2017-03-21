package code.vera.myblog.db;

import android.content.Context;

import code.vera.myblog.bean.home.StatusesBean;

/**
 * Created by vera on 2017/3/21 0021.
 */

public class CollectionDao extends BaseDao<StatusesBean> {

    public static CollectionDao instance;

    public CollectionDao(Context context) {
        super(context, StatusesBean.class);
    }

    public static CollectionDao getInstance(Context context) {
        if (instance == null) {
            synchronized (CollectionDao.class) {
                if (instance == null)
                    instance = new CollectionDao(context);
            }
        }
        return instance;
    }
}
