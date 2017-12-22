package settings;

import db.Database;
import enums.DatabaseType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class DBSetting {

    private static final String propertyPath = "../";

    private static DBSetting instance = new DBSetting();

    public static DBSetting getInstance() {
        return instance;
    }

    private DBSetting() {
        this.databases = new HashMap<String, Database>();
    }

    private HashMap<String, Database> databases;

    public void loadDatabase(String name, int type, String filename) {
        try {
            String filePath = DBSetting.propertyPath + filename;
            Properties properties = new Properties();
            properties.load(DBSetting.class.getResourceAsStream(filePath));
            Database db = new Database(name, DatabaseType.getType(type), properties);
            addDatabase(db);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase(String dbName) {
        return databases.get(dbName);
    }

    public void addDatabase(Database db) {
        if (databases.containsKey(db.getName())) {
            databases.remove(db.getName());
        }
        databases.put(db.getName(), db);
    }

    public void removeDatabase(Database db) {
        databases.remove(db.getName());
    }

    public void removeAll() {
        databases.clear();
    }

    public boolean checkDatabase(Database db) {
        return databases.containsKey(db.getName());
    }

    public Connection getConnection(Database db) throws SQLException {
        return DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
    }
}
