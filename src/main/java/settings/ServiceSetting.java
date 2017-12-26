package settings;

import network.socket.ServerBuffer;
import service.BaseService;
import service.GatewayService;
import service.KeepAliveService;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceSetting {

    private static ServiceSetting instance = new ServiceSetting();
    public static ServiceSetting getInstance() {
        return instance;
    }

    private ServiceSetting() {
        initializeServices();
    }

    //服务集合
    private HashMap<Integer, BaseService> services = new HashMap<Integer, BaseService>();

    public void putService(BaseService service) {
        this.services.put(service.getActionType().getCode(), service);
    }
    public void removeService(Integer actionCode) {
        stopService(actionCode);
        this.services.remove(actionCode);
    }
    public void removeService(BaseService service) {
        stopService(service.getActionType().getCode());
        this.services.remove(service.getActionType().getCode());
    }
    public void removeAllService() {
        for (BaseService baseService : this.services.values()) {
            baseService.stop();
        }
        this.services.clear();
    }

    public BaseService getService(Integer actionCode) {
        return this.services.get(actionCode);
    }

    public void stopService(Integer actionCode) {
        BaseService baseService = this.getService(actionCode);
        baseService.stop();
    }

    private void initializeServices() {
        putService(new GatewayService());
        putService(new KeepAliveService());
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
