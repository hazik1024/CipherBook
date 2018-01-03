package service.user;

import cache.UserCacheDao;
import dao.user.UserDao;
import entity.UserEntity;
import enums.ServiceCode;
import enums.ServiceType;
import enums.Topid;
import actions.LoginAction;
import actions.RequestAction;
import service.base.BaseService;

import java.util.UUID;

/**
 * 登录服务
 */
public class LoginService extends BaseService {

    public LoginService() {
        super(ServiceType.business, "登录服务");
    }
    public Topid getTopid() {
        return Topid.userlogin;
    }

    public void processing(RequestAction requestAction) {
        String username = requestAction.getData().getString("username");
        String password = requestAction.getData().getString("password");

        LoginAction loginAction = new LoginAction();

        UserDao userDao = new UserDao();
        UserEntity userEntity = userDao.queryUser(username);
        if (userEntity == null) {
            requestAction.setServiceCode(ServiceCode.usernameerror);
            logger.info("用户登录名[" + username + "]不存在");
        }
        else {
            if (password.equals(userEntity.getPassword())) {
                String token = UUID.randomUUID().toString();
                requestAction.setToken(token);
                //存储到redis
                UserCacheDao userCacheDao = new UserCacheDao();
                if (userCacheDao.saveToken(userEntity.getUid(), token)) {
                    loginAction.setToken(token);
                    logger.info("用户[" + username + "]登录token[" + token + "]缓存成功");
                }
                else {
                    logger.info("用户[" + username + "]登录token[" + token + "]缓存失败");
                }
            }
            else {
                requestAction.setServiceCode(ServiceCode.passworderror);
                logger.info("用户[" + username + "]登录密码[" + password + "]错误");
            }
        }

        requestAction.setBaseAction(loginAction);
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

//        UserDao userDao = new UserDao();
//        UserEntity entity = userDao.queryById(1);
//        if (entity != null) {
//            logger.info(entity.getUid() + ", " + entity.getUsername());
//            int count = userDao.getCount();
//            logger.info("count:" + count);
//        }

//        UserCacheDao userCacheDao = new UserCacheDao();
//        if (userCacheDao.saveToken(1,UUID.randomUUID().toString())) {
//            logger.info("保存成功");
//        }
//        else {
//            logger.info("保存失败");
//        }
    }
}
