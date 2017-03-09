package code.vera.myblog.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import code.vera.myblog.bean.PostBean;

/**
 * 我们对每个Bean创建一个XXXDao来处理当前Bean的数据库操作，当然真正去和数据库打交道的对象，通过上面代码中的getDao（T t）进行获取
 getDao为一个泛型方法，会根据传入Class对象进行创建Dao，并且使用一个Map来保持所有的Dao对象，只有第一次调用时才会去调用底层的getDao()。
 * Created by vera on 2017/3/9 0009.
 */

public class PostDao {
    private Context context;
    private Dao<PostBean, Integer> postBeanIntegerDao;
    private DatabaseHelper helper;

    public PostDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            postBeanIntegerDao = helper.getDao(PostBean.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个草稿
     * @param bean
     */
    public void add(PostBean bean)
    {
        try
        {
            postBeanIntegerDao.create(bean);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
