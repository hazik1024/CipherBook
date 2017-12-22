package settings;

import service.BaseService;
import service.GatewayService;
import service.KeepAliveService;

import java.util.HashMap;

public class ServiceSetting {

    private static ServiceSetting instance = new ServiceSetting();
    public static ServiceSetting getInstance() {
        return instance;
    }

    //服务集合
    private HashMap<Integer, BaseService> services = new HashMap<Integer, BaseService>();

    public void addService(BaseService service) {
        this.services.put(service.getActionCode(), service);
    }
    public void removeService(Integer actionCode) {
        this.services.remove(actionCode);
    }
    public void removeService(BaseService service) {
        this.services.remove(service.getActionCode());
    }
    public void removeAllService() {
        this.services.clear();
    }

    private void initializeServices() {
        this.addService(new GatewayService());
        this.addService(new KeepAliveService());
    }
}
