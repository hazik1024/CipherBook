package service.user;

import dao.user.UserDao;
import entity.UserEntity;
import enums.ServiceCode;
import enums.ServiceType;
import enums.Topid;
import enums.UserStatus;
import actions.RegisterAction;
import actions.RequestAction;
import service.base.BaseService;

import java.util.Date;

public class RegisterService extends BaseService {
    public RegisterService() {
        super(ServiceType.business, "注册服务");
    }
    public Topid getTopid() {
        return Topid.userregister;
    }

    public void processing(RequestAction requestAction) {
        String username = requestAction.getData().getString("username");
        String password = requestAction.getData().getString("password");

        RegisterAction registerAction = new RegisterAction();
        requestAction.setBaseAction(registerAction);
        UserEntity userEntity = this.getUserDao().queryUser(username);
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setMobile("");
            userEntity.setStatus(UserStatus.normal);
            userEntity.setRegistertime(new Date());
            try {
                getUserDao().save(userEntity);
                logger.info("用户[" + username + "]注册结果:" + ServiceCode.success.getMessage());
            }
            catch (Exception e) {
                requestAction.setServiceCode(ServiceCode.dberror);
                logger.error("用户[" + username + "]注册结果,数据库存储失败:" + e.getMessage());
            }
        }
        else {
            requestAction.setServiceCode(ServiceCode.usernameexist);
            logger.error("用户[" + username + "]注册结果:" + ServiceCode.usernameexist.getMessage());
        }
    }

    private UserDao userDao;
    private UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }
}
