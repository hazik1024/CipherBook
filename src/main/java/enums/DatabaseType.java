package enums;

public enum DatabaseType {
    mysql(1, "mysql"),
    redis(2, "redis")
    ;

    DatabaseType(int value, String name)
    {
        this.value = value;
        this.name = name;
    }

    private final int value;
    private final String name;

    public int getValue()
    {
        return value;
    }

    public String getName() {
        return name;
    }

    public static DatabaseType getType(int value) {
        if (mysql.getValue() == value) {
            return mysql;
        }
        else if (redis.getValue() == value) {
            return redis;
        }
        return mysql;
    }
}
