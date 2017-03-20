package code.vera.myblog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import code.vera.myblog.bean.PostBean;

/**
 * Created by vera on 2017/3/9 0009.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    //表名
    private static final String TABLE_NAME = "draft.db";
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    private static DatabaseHelper instance;//单例

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }
    private DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 4);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            //建表
            TableUtils.createTable(connectionSource, PostBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        //删表
        try {
            TableUtils.dropTable(connectionSource, PostBean.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        context = context.getApplicationContext();
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }
    public synchronized Dao getDao(Class clazz) throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();

        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
    public static DatabaseHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException("No init");
        }
        return instance;
    }

}
