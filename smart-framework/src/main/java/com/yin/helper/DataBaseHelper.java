package com.yin.helper;
/**
 * @author 17694
 * @date 2022/01/20
 **/

import com.yin.frameword.HelperLoader;
import com.yin.util.PropsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName : DataBaseHelper 
 * @Description :   进行数据库的连接 加载 以及事务的管理
 */
public class DataBaseHelper {
    private static final Logger log = LoggerFactory.getLogger(HelperLoader.class);
    private static final QueryRunner query = new QueryRunner();
    //存储 线程的本地变量
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
    private static final String Driver;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    static {
        Properties properties = PropsUtil.loadProps("config.properties");
        Driver = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");
        try {
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            log.error("can not find class",e);
        }
    }


    /**
     *  开启事务
     */
    public static void beginTransaction () {
        Connection connection = getConnection();
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (Exception e) {
                log.error("fail begin",e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction () {
        Connection connection = getConnection();
        if(connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (Exception e) {
                log.error("commit fail");
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollBackTransaction () {
        Connection connection = getConnection();
        if(connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (Exception e) {
                log.error("rollback fail");
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }


    }







    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection () {
        Connection connection = CONNECTION_THREAD_LOCAL.get() ;
        if(connection == null) {
            try{
                connection =  DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (Exception e) {
                log.error("fail connection",e);
            } finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
       return connection;
    }

    /**
     * 关闭连接
     * @param
     */
    public static void closeConnection () {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
            if(connection != null) {
                try {
                    connection.close();
                }catch (Exception e) {
                    log.error("connection fail close",e);
                } finally {
                    CONNECTION_THREAD_LOCAL.remove();
                }
            }
    }

    /**
     *  封装 crud 基本操作
     */

    /**
     * 查询单表
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryList(Class<T> entityClass ,String sql, Object... params) {
        List<T> entityList = null;

        try {
            Connection connection = getConnection();
            entityList = query.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (Exception e) {
            log.error("fail query",e);
            throw  new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }

    public static List<Map<String,Object>> queryList(String sql,Object... params) {
        List<Map<String,Object>> result;
        try {
            Connection connection = getConnection();
            result =  query.query(connection,sql,new MapListHandler(),params);
        } catch (Exception e) {
            log.error("fail query",e);
            throw new RuntimeException(e);
        }
            return result ;
    }

        public static int executeUpdate(String sql, Object...parms) {
                int rows = 0;
                try {
                    Connection connection = getConnection();
                    rows =   query.update(connection,sql,parms);
                } catch (Exception e) {
                    log.error("execute update failure",e);
                    throw new RuntimeException(e);
                } finally {
                    closeConnection();
                }

                return rows;
        }

        public static <T> Boolean insertBook (Class<T> entityClass ,Map<String,Object> fieldMap) {

            if (fieldMap.isEmpty()) {
                log.error("field is empty ; insert fail");
                return false;
            }
            //简单点 直接写死测试
            String sql = "INSERT INTO BOOK VALUES ?,?,?";
            Object[] objects = fieldMap.values().toArray();
            return executeUpdate(sql, objects) == 1;
        }
}
