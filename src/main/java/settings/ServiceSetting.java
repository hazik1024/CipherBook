package settings;

import network.socket.ServerBuffer;
import service.base.BaseService;
import service.base.GatewayService;
import service.base.KeepAliveService;
import service.user.LoginService;
import service.user.RegisterService;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceSetting {

    private static ServiceSetting instance = new ServiceSetting();
    public static ServiceSetting getInstance() {
        return instance;
    }

    private ServiceSetting() {

    }

    //服务集合
    private HashMap<Integer, BaseService> services = new HashMap<Integer, BaseService>();

    public BaseService getService(Integer actionCode) {
        return this.services.get(actionCode);
    }

    private void putService(BaseService service) {
        this.services.put(service.getTopid().getCode(), service);
    }
    private void removeService(Integer actionCode) {
        stopService(actionCode);
        this.services.remove(actionCode);
    }
    public void removeService(BaseService service) {
        removeService(service.getTopid().getCode());
    }

    private void stopService(Integer actionCode) {
        BaseService baseService = this.getService(actionCode);
        baseService.stop();
    }

    public void initServices() {
        putService(new GatewayService());
        putService(new KeepAliveService());
        putService(new LoginService());
        putService(new RegisterService());
    }

    public void startServices() {
        for (BaseService service : this.services.values()) {
            service.start();
        }
    }

    //client集合
    private ConcurrentHashMap<Integer, ServerBuffer> buffers = new ConcurrentHashMap<Integer, ServerBuffer>();

    public ServerBuffer getServerBuffer(Integer bufferId) {
        return buffers.get(bufferId);
    }
    public void putServerBuffer(ServerBuffer buffer) {
        buffers.put(buffer.getBufferId(), buffer);
    }
    public void removeServerBuffer(Integer bufferId) {
        getServerBuffer(bufferId).close();
        buffers.remove(bufferId);
    }
    public void removeAllServerBuffer() {
        for (ServerBuffer buffer : buffers.values()) {
            buffer.close();
        }
        buffers.clear();
    }
    public int getServerBufferCount() {
        return buffers.size();
    }
}
