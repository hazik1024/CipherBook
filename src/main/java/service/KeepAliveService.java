package service;

import enums.ActionType;
import enums.ServiceType;
import network.actions.KeepAliveAction;

import java.util.concurrent.LinkedBlockingQueue;

public class KeepAliveService extends BaseService {

    LinkedBlockingQueue<KeepAliveAction> queue = new LinkedBlockingQueue<KeepAliveAction>();

    public KeepAliveService() {
        super(ServiceType.bussiness, "KeepAliveService");
    }

    public void running() {
        while(true) {
            try {
                KeepAliveAction action = queue.take();

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Integer getActionCode() {
        return ActionType.keepalive.getCode();
    }
}
