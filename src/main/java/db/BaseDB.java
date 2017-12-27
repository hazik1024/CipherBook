package db;

import entity.DataBaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDB {
    private static Log log = LogFactory.getLog(BaseDB.class);
    static {
        try {
            Class.forName(getClassName());
        }
        catch (ClassNotFoundException e1) {
            log.error("加载MySql驱动失败:找不到类");
        }
    }

    private DataBaseEntity entity;

    public static String getClassName() {
        return "com.mysql.jdbc.Driver";
    }

    public BaseDB (DataBaseEntity entity) {
        this.entity = entity;
    }

    private Connection connection = null;
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(entity.getUrl(), entity.getUser(), entity.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public Statement getStatement() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                return connection.createStatement();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void closeStatement(Statement statement) {
        try {
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        closeConnection(this.connection);
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if (connection == this.connection) {
                    this.connection = null;
                }
            }
        }
    }

    public boolean isConnectionClosed() {
        if (this.connection != null) {
            try {
                return this.connection.isClosed();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
