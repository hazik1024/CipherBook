package service;

import enums.ActionType;
import enums.ServiceCode;
import enums.ServiceType;
import network.actions.KeepAliveAction;
import network.actions.RequestAction;
import settings.ServiceSetting;

import java.util.concurrent.LinkedBlockingQueue;

public class KeepAliveService extends BaseService {

    private LinkedBlockingQueue<RequestAction> queue = new LinkedBlockingQueue<RequestAction>();

    public KeepAliveService() {
        super(ServiceType.business, "心跳服务");
    }

    public void running() {
        while(true) {
            try {
                RequestAction requestAction = queue.take();
                KeepAliveAction action = new KeepAliveAction();
                action.setTime(requestAction.getData().getLong("time"));
                action.setBufferId(requestAction.getBufferId());
                requestAction.setBaseAction(action);
                requestAction.setServiceCode(ServiceCode.success);
                log.info(requestAction.getTopid() + ", "+ requestAction.getBufferId() + ", " + requestAction.getData().toJSONString());
                ServiceSetting.getInstance().getServerBuffer(requestAction.getBufferId()).addSendAction(requestAction);
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

    public void addAction(RequestAction requestAction) {
        try {
            this.queue.put(requestAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
