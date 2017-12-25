package db;

import enums.DatabaseType;
import settings.DBSetting;

import java.sql.Connection;

public class DBFactory {
    private static DBFactory instance = new DBFactory();
    public static DBFactory getInstance () {
        return instance;
    }

    private DBFactory() {

    }

    public Database getDatabase(DatabaseType type) {
//        switch (type) {
//            case Oracle:
//                break;
//            case MySql:
//                break;
//            case DB2:
//                break;
//            case Sybase:
//                break;
//            case SqlServer:
//                break;
//            case MSAccess:
//                break;
//            case MemCache:
//                break;
//            case Redis:
//                break;
//            case Proxool:
//                break;
//            default:
//                break;
//        }
        return null;
    }

    public Connection getConnection(DatabaseType type) {
        switch (type) {
            case Oracle:
                break;
            case MySql:
                break;
            case DB2:
                break;
            case Sybase:
                break;
            case SqlServer:
                break;
            case MSAccess:
                break;
            case MemCache:
                break;
            case Redis:
                break;
            case Proxool:
                break;
            default:
                break;
        }
        return null;
    }
}
