package service.user;

import dao.user.UserDao;
import entity.UserEntity;
import enums.ActionType;
import enums.ServiceType;
import enums.UserStatus;
import network.actions.LoginAction;
import network.actions.RequestAction;
import service.base.BaseService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        LoginAction loginAction = new LoginAction();
        String username = requestAction.getData().getString("username");
        String password = requestAction.getData().getString("password");
        requestAction.setBaseAction(loginAction);

        UserDao userDao = new UserDao();
        UserEntity userEntity = userDao.queryUser(loginAction.getUser());
        if (userEntity.getPassword() == loginAction.getPassword()) {

        }

        UUID uuid = UUID.randomUUID();
    }

    @Override
    public void ready() {
        super.ready();
//        UserEntity entity = new UserEntity();
//        entity.setUsername("hazik");
//        entity.setPassword("hao86971");
//        entity.setTruename("hazik");
//        entity.setMobile("18118781059");
//        entity.setRegistertime(new Date());
//        entity.setLastlogintime(new Date());
//        entity.setStatus(UserStatus.normal);
        UserDao userDao = new UserDao();
//        userDao.save(entity);
        List<UserEntity> list = userDao.getAll();
        logger.info(list.size());
        UserEntity entity = userDao.queryById(1);
        logger.info(entity.getUid() + ", " + entity.getUsername());
        int count = userDao.getCount();
        logger.info("count:" + count);
    }
}
