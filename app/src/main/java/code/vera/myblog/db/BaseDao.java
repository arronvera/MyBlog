package code.vera.myblog.db;

import android.content.Context;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * Created by vera on 2017/3/20 0020.
 */

public class BaseDao<T> {

    protected Context context;
    protected DatabaseHelper helper;
    protected Class<T> cls;
    protected Dao<T, Integer> dao;

    @Deprecated
    public BaseDao(Context context, Class<T> cls) {
        this.context = context;
        this.cls = cls;
        helper = DatabaseHelper.getHelper(context);
    }

    public BaseDao(DatabaseHelper helper, Class<T> cls) {
        this.helper = helper;
        this.cls = cls;
    }


    public Dao<T, Integer> getDao() throws SQLException {
        if (dao == null) {
            dao = helper.getDao(cls);
        }
        return dao;
    }

    //根据bean进行添加
    public boolean add(T t) {
        boolean flag = false;
        try {
            long num = getDao().create(t);
            if (1 == num) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }


    //根据数据列表进行添加
    public boolean add(List<T> list) {
        boolean flag = false;
        if (list == null || list.isEmpty()) {
            return flag;
        }
        try {
            long num = getDao().create(list);
            flag = num == list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }


    public Boolean createOrUpdateList(List<T> list) {
        //使用事务进行修改和保存数据(提高效率)
        boolean flag = false;
        AndroidDatabaseConnection adConnection =
                new AndroidDatabaseConnection(DatabaseHelper.getInstance().getWritableDatabase(), false);
        try {
            Savepoint savepoint = adConnection.setSavePoint("_class_create_update");
            for (int i = 0; i < list.size(); i++) {
                createOrUpdate(list.get(i));
            }
            adConnection.commit(savepoint);
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    //根据Id进行删除
    public void deleteById(Integer id) {
        try {
            getDao().deleteById(id);
        } catch (SQLException e) {
        }
    }

    //根据bean进行删除
    public void delete(T t) {
        try {
            getDao().delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //根据数据列表进行删除
    public void deleteList(List<T> list) {
        try {
            getDao().delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //根据id列表进行删除
    public void deleteIds(List<Integer> ids) {
        try {
            getDao().deleteIds(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计数据的总条数
     *
     * @return
     */
    public long countOf() {
        try {
            return getDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 分页查询
     *
     * @param startRow
     * @param maxRow
     * @return
     */
    public List<T> limit(long startRow, long maxRow) {
        try {
            QueryBuilder builder = getDao().queryBuilder();
            return builder.offset(startRow)
                    .limit(maxRow).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    //查询所有的数据
    public List<T> getAll() {
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 模糊查询
     *
     * @param columnName
     * @param value
     * @return
     */
    public List<T> like(String columnName, String value) {
        try {
            return getDao().queryBuilder().where().like(columnName,
                    value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<T> getGroupById(String value) {
        return like("item_id",
                value + "%%");
    }

    public List<T> getGroupByIdOneLevel(String value) {
        return like("item_id",
                value + "%%%");
    }

    public List<T> getGroupByIdNextLevel(String value) {
        return like("item_id",
                value + "%%");
    }

    public List<T> getGroupByIdThressLevel(String value) {
        return like("item_id",
                value + "%");
    }

    public T findById(String idColumnName, String value) {
        try {
            QueryBuilder qb = getDao().queryBuilder();
            List<T> list = qb.where().eq(idColumnName, value).query();
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delByColumnName(String columnName, String value) {
        int num = 0;
        try {
            DeleteBuilder db = getDao().deleteBuilder();
            db.where().eq(columnName, value);
            num = db.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num > 0;
    }

    public T getItem(int id) {
        List<T> mList;
        T t = null;
        try {
            mList = getDao().queryForAll();
            t = mList.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public T getById(Integer id) {
        T t = null;
        try {
            t = getDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }


    //更新数据
    public boolean update(T t) {
        boolean flag = false;
        try {
            long num = getDao().update(t);
            flag = num == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }


    /**
     * 清空表
     *
     * @return
     */
    public boolean clearTable() {
        boolean flag = true;
        try {
            getDao().queryRaw(String.format("delete from %s", getDao().getTableName()));
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    public void updateById(T t, Integer id) {
        try {
            getDao().updateId(t, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createIfNoExists(T t) {
        try {
            getDao().createIfNotExists(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createOrUpdate(T t) {
        boolean flag = false;
        try {
            getDao().createOrUpdate(t);
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
