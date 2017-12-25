package db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
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

    public Database database;

    public static String getClassName() {
        return "com.mysql.jdbc.Driver";
    };

    public BaseDB (Database database) {
        this.database = database;
    }

    private Connection connection = null;

    public Statement getStatement() {
        if (this.connection != null) {
            try {
                return this.connection.createStatement();
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
        if (this.connection != null) {
            try {
                this.connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                this.connection = null;
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
