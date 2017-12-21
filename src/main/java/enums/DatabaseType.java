package enums;

public enum DatabaseType {
    Oracle(1),

    MySql(2),

    DB2(3),

    Sybase(4),

    SqlServer(5),

    MSAccess(6),

    MemCache(7),

    Redis(8),

    Proxool(9)

    ;

    DatabaseType(int value)
    {
        this.value = value;
    }

    private final int value;

    public int getValue()
    {
        return value;
    }

    public static DatabaseType getType(int value) {
        switch (value) {
            case 1:
                return Oracle;
            case 2:
                return MySql;
            case 3:
                return DB2;
            case 4:
                return Sybase;
            case 5:
                return SqlServer;
            case 6:
                return MSAccess;
            case 7:
                return MemCache;
            case 8:
                return Redis;
            case 9:
                return Proxool;
            default:
                return MySql;
        }
    }
}
