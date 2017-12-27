package entity;

import enums.DatabaseType;

import javax.xml.crypto.Data;
import java.util.Properties;

public class DataBaseEntity {

    private String name;
    private DatabaseType type;

    private String alias;
    private String driverClass;
    private String url;

    private String user;
    private String password;
    private int prototypeCount;
    private int maxCount;
    private int minCount;
    private int simultaneous;
    private String keepingSql;
    private boolean verbose;
    private String statistics;
    private String logLevel;

    public DataBaseEntity(String name, DatabaseType type, Properties properties) {
        this.name = name;
        this.type = type;
        this.alias = properties.getProperty("alias");
        this.url = properties.getProperty("driver-url");
        this.driverClass = properties.getProperty("driver-class");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.prototypeCount = new Integer(properties.getProperty("prototype-count"));
        this.maxCount = new Integer(properties.getProperty("maximum-connection-count"));
        this.minCount = new Integer(properties.getProperty("minimum-connection-count"));
        this.simultaneous = new Integer(properties.getProperty("simultaneous-build-throttle"));
        this.keepingSql = properties.getProperty("house-keeping-test-sql");
        this.verbose = new Boolean(properties.getProperty("verbose"));
        this.statistics = properties.getProperty("statistics");
        this.logLevel = properties.getProperty("statistics-log-level");
    }

    public String getName() {
        return name;
    }

    public DatabaseType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPrototypeCount() {
        return prototypeCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getSimultaneous() {
        return simultaneous;
    }

    public String getKeepingSql() {
        return keepingSql;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public String getStatistics() {
        return statistics;
    }

    public String getLogLevel() {
        return logLevel;
    }
}
