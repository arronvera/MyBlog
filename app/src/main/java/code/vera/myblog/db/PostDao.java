package code.vera.myblog.db;

import android.content.Context;

import code.vera.myblog.bean.PostBean;

/**
 * 我们对每个Bean创建一个XXXDao来处理当前Bean的数据库操作，当然真正去和数据库打交道的对象，通过上面代码中的getDao（T t）进行获取
 getDao为一个泛型方法，会根据传入Class对象进行创建Dao，并且使用一个Map来保持所有的Dao对象，只有第一次调用时才会去调用底层的getDao()。
 * Created by vera on 2017/3/9 0009.
 */

public class PostDao extends BaseDao<PostBean> {
    public static PostDao instance;

    public PostDao(Context context) {
        super(context, PostBean.class);
    }

    public static PostDao getInstance(Context context) {
        if (instance == null) {
            synchronized (PostDao.class) {
                if (instance == null)
                    instance = new PostDao(context);
            }
        }
        return instance;
    }
}
