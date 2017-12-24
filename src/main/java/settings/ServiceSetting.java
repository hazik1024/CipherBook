package settings;

import network.socket.ServerBuffer;
import service.BaseService;
import service.GatewayService;
import service.KeepAliveService;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceSetting {

    private static ServiceSetting instance = new ServiceSetting();
    public static ServiceSetting getInstance() {
        return instance;
    }

    //服务集合
    private HashMap<Integer, BaseService> services = new HashMap<Integer, BaseService>();

    public void putService(BaseService service) {
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

    public BaseService getService(Integer actionCode) {
        return this.services.get(actionCode);
    }

    public void initializeServices() {
        putService(new GatewayService());
        putService(new KeepAliveService());

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
    public void removeServerBufferf(Integer bufferId) {
        buffers.remove(bufferId);
    }
    public int getServerBufferCount() {
        return buffers.size();
    }
}
