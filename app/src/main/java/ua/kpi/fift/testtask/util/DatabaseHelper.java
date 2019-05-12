package ua.kpi.fift.testtask.util;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import ua.kpi.fift.testtask.pojo.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "testTask.db";
    private static final int DB_VERSION = 1;
    private Dao<User, String> userDao;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME,  null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);

            User user = new User();
            user.setLogin("admin");
            user.setPassword("password");
            user.setRole("admin");

            userDao.create(user);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }

    public Dao<User, String> userDao() throws SQLException, java.sql.SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }


    public User findUser(String login) throws java.sql.SQLException {
        userDao();
        return userDao.queryForId(login);
    }

    public void addUser(User user) throws java.sql.SQLException {
        userDao();
        userDao.create(user);
    }

    public List<User> findAll() throws java.sql.SQLException {
        userDao();
        return userDao.queryForAll();
    }

    public void updateUser(User user) throws java.sql.SQLException {
        userDao();
        userDao.update(user);
    }

    public void deleteUser(User user) throws java.sql.SQLException {
        userDao();
        userDao.delete(user);
    }
}
