package settings;

import java.util.Set;

public class RedisSetting {

    private static RedisSetting instance = new RedisSetting();

    public static RedisSetting getInstance() {
        return instance;
    }
    private RedisSetting() {

    }

    private String mastername;
    private int maxtotal;
    private int maxidle;
    private int minidle;
    private int timebetweenvictionrunsmillis;
    private boolean testonborrow;
    private boolean testonreturn;
    private int timeout;
    private Set<String> sentinels;

    public String getMastername() {
        return mastername;
    }

    public void setMastername(String mastername) {
        this.mastername = mastername;
    }

    public int getMaxtotal() {
        return maxtotal;
    }

    public void setMaxtotal(int maxtotal) {
        this.maxtotal = maxtotal;
    }

    public int getMaxidle() {
        return maxidle;
    }

    public void setMaxidle(int maxidle) {
        this.maxidle = maxidle;
    }

    public int getMinidle() {
        return minidle;
    }

    public void setMinidle(int minidle) {
        this.minidle = minidle;
    }

    public int getTimebetweenvictionrunsmillis() {
        return timebetweenvictionrunsmillis;
    }

    public void setTimebetweenvictionrunsmillis(int timebetweenvictionrunsmillis) {
        this.timebetweenvictionrunsmillis = timebetweenvictionrunsmillis;
    }

    public boolean isTestonborrow() {
        return testonborrow;
    }

    public void setTestonborrow(boolean testonborrow) {
        this.testonborrow = testonborrow;
    }

    public boolean isTestonreturn() {
        return testonreturn;
    }

    public void setTestonreturn(boolean testonreturn) {
        this.testonreturn = testonreturn;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Set<String> getSentinels() {
        return sentinels;
    }

    public void setSentinels(Set<String> sentinels) {
        this.sentinels = sentinels;
    }
}
