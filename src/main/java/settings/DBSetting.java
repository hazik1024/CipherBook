package settings;

import db.BaseDB;
import db.Mysql;
import entity.DataBaseEntity;
import enums.DatabaseType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

public class DBSetting {

    private static final String propertyPath = "../";

    private static DBSetting instance = new DBSetting();

    public static DBSetting getInstance() {
        return instance;
    }

    private DBSetting() {

    }

    public void loadDatabase(String name, int type, String filename) {
        try {
            String filePath = DBSetting.propertyPath + filename;
            Properties properties = new Properties();
            properties.load(DBSetting.class.getResourceAsStream(filePath));
            DataBaseEntity entity = new DataBaseEntity(name, DatabaseType.getType(type), properties);
            loadDataBase(entity);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    private HashMap<DatabaseType, BaseDB> databases = new HashMap<DatabaseType, BaseDB>();

    private void loadDataBase(DataBaseEntity entity) {
        switch (entity.getType()) {
            case mysql:
                databases.put(entity.getType(), new Mysql(entity));
                break;
            case redis:
                break;
            default:
                break;
        }
    }

    public BaseDB getDataBase(DatabaseType type) {
        return databases.get(type);
    }

    public Connection getConnection(DatabaseType type) {
        return databases.get(type).getConnection();
    }
    public void closeConnection(DatabaseType type, Connection connection) {
        databases.get(type).closeConnection();
    }

    public Statement getStatement(DatabaseType type) {
        return databases.get(type).getStatement();
    }
    public void closeStatement(DatabaseType type, Statement statement) {
        databases.get(type).closeStatement(statement);
    }
}
