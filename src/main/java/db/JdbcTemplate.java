package db;

import enums.DatabaseType;
import settings.DBSetting;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JdbcTemplate {

    private Database database;

    public JdbcTemplate() {
        this.database = DBSetting.getInstance().getDatabase(getDatabaseName());
    }

    protected abstract String getDatabaseName();

    public Connection getConnection() throws SQLException {

        Connection connection = DBSetting.getInstance().getConnection(this.database);
        if (database.getType() == DatabaseType.MySql) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }
        return connection;
    }

    public Connection getConnection(String dbName) throws SQLException {
        Connection connection = DBSetting.getInstance().getConnection(DBSetting.getInstance().getDatabase(dbName));
        if (database.getType() == DatabaseType.MySql) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }
        return connection;
    }
}
