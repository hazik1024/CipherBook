package service.user;

import enums.ActionType;
import enums.ServiceType;
import network.actions.RegisterAction;
import network.actions.RequestAction;
import service.base.BaseService;

public class RegisterService extends BaseService {
    public RegisterService() {
        super(ServiceType.business, "注册服务");
    }
    public ActionType getActionType() {
        return ActionType.register;
    }

    public void processing(RequestAction requestAction) {
        RegisterAction registerAction = new RegisterAction();
        registerAction.setUser(requestAction.getData().getString("user"));
        registerAction.setPassword(requestAction.getData().getString("password"));

    }
}
