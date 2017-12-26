package service;

import enums.ActionType;
import enums.ServiceCode;
import enums.ServiceType;
import network.actions.KeepAliveAction;
import network.actions.RequestAction;

public class KeepAliveService extends BaseService {

    public KeepAliveService() {
        super(ServiceType.business, "心跳服务");
    }

    @Override
    public ActionType getActionType() {
        return ActionType.keepalive;
    }

    public void processing(RequestAction requestAction) {
        KeepAliveAction action = new KeepAliveAction();
        action.setTime(requestAction.getData().getLong("time"));
        action.setBufferId(requestAction.getBufferId());
        requestAction.setBaseAction(action);
        requestAction.setServiceCode(ServiceCode.success);
        log.info(requestAction.getTopid() + ", "+ requestAction.getBufferId() + ", " + requestAction.getData().toJSONString());
    }
}
