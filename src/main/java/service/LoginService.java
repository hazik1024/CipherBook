package service;

import enums.ActionType;
import enums.ServiceType;
import network.actions.RequestAction;

/**
 * 登录服务
 */
public class LoginService extends BaseService {

    public LoginService() {
        super(ServiceType.business, "登录服务");
    }
    public ActionType getActionType() {
        return ActionType.login;
    }

    public void processing(RequestAction requestAction) {

    }
}
