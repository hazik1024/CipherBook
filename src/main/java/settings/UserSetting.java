package settings;

public class UserSetting {
    private static UserSetting instance = new UserSetting();
    public static UserSetting getInstance() {
        return instance;
    }
    private UserSetting() {

    }
}
