package service;

import enums.ActionType;
import enums.ServiceType;
import network.actions.BaseAction;
import settings.ServiceSetting;

import java.util.concurrent.LinkedBlockingQueue;

public class KeepAliveService extends BaseService {

    LinkedBlockingQueue<BaseAction> queue = new LinkedBlockingQueue<BaseAction>();

    public KeepAliveService() {
        super(ServiceType.business, "心跳服务");
    }

    public void running() {
        while(true) {
            try {
                BaseAction action = queue.take();
                System.out.println(action.getTopid() + ", "+ action.getBufferId() + ", " + action.getData().toJSONString());
                ServiceSetting.getInstance().getServerBuffer(action.getBufferId()).addSendAction(action);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Integer getActionCode() {
        return ActionType.keepalive.getTopid();
    }

    public void addAction(BaseAction baseAction) {
        try {
            this.queue.put(baseAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
