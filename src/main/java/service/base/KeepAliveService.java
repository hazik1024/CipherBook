package service.base;

import enums.ServiceCode;
import enums.ServiceType;
import enums.Topid;
import actions.KeepAliveAction;
import actions.RequestAction;

public class KeepAliveService extends BaseService {

    public KeepAliveService() {
        super(ServiceType.business, "心跳服务");
    }

    public Topid getTopid() {
        return Topid.keepalive;
    }

    public void processing(RequestAction requestAction) {
        KeepAliveAction action = new KeepAliveAction();
        requestAction.setBaseAction(action);
        requestAction.setServiceCode(ServiceCode.success);
        logger.info(requestAction.getTopid() + ", "+ requestAction.getBufferId() + ", " + requestAction.getData().toJSONString());
    }
}
