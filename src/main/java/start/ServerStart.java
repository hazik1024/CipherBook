package start;


import constants.GlobalConfig;
import network.db.HibernateOperator;
import network.redis.JedisClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.NetworkSetting;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import settings.RedisSetting;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ServerStart {
    private static Logger logger = LogManager.getLogger(ServerStart.class);
    public static void main(String[] args) {
        //加载配置文件
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream inputStream =  ServerStart.class.getResourceAsStream(GlobalConfig.CONFIG_PATH);
            doc = builder.build(inputStream);
        }
        catch (IOException e) {
            logger.info("找不到systemconfig.xml文件");
            e.printStackTrace();
        }
        catch (JDOMException e) {
            logger.info("找不到systemconfig.xml文件");
            e.printStackTrace();
        }
        if (doc == null) {
            logger.info("找不到systemconfig.xml文件");
            return;
        }

        Element root = doc.getRootElement();
        //取数据库配置
//        Element database = root.getChild("database");
//        if (database != null) {
//            try {
//                ServerManager.getInstance().loadDatabase(database);
//            } catch (DataConversionException e) {
//                e.printStackTrace();
//                return;
//            }
//        }
        //初始化hibernate
        HibernateOperator.getInstance().initHibernate();
        //读取网络配置
        Element network = root.getChild("network");
        if (network != null) {
            Element socket = network.getChild("socket");
            if (socket != null) {
                try {
                    String host = socket.getAttributeValue("host");
                    int clientport = socket.getAttribute("clientport").getIntValue();
                    int serverport = socket.getAttribute("serverport").getIntValue();
                    int timeout = socket.getAttribute("timeout").getIntValue();
                    NetworkSetting.getInstance().setHost(host);
                    NetworkSetting.getInstance().setClientPort(clientport);
                    NetworkSetting.getInstance().setServerPort(serverport);
                    NetworkSetting.getInstance().setTimeout(timeout);
                }
                catch (DataConversionException e) {
                    e.printStackTrace();
                }
            }
        }
        //读取Redis配置
        Element redis = root.getChild("redis");
        if (network != null) {
            Element config = redis.getChild("config");
            if (config != null) {
                try {
                    String mastername = config.getAttributeValue("mastername");
                    String password = config.getAttributeValue("password");
                    int maxtotal = config.getAttribute("maxtotal").getIntValue();
                    int maxidle = config.getAttribute("maxidle").getIntValue();
                    int minidle = config.getAttribute("minidle").getIntValue();
                    int timebetweenvictionrunsmillis = config.getAttribute("timebetweenvictionrunsmillis").getIntValue();
                    boolean testonborrow = config.getAttribute("testonborrow").getBooleanValue();
                    boolean testonreturn = config.getAttribute("testonreturn").getBooleanValue();
                    int timeout = config.getAttribute("timeout").getIntValue();
                    RedisSetting.getInstance().setMastername(mastername);
                    RedisSetting.getInstance().setPassword(password);
                    RedisSetting.getInstance().setMaxtotal(maxtotal);
                    RedisSetting.getInstance().setMaxidle(maxidle);
                    RedisSetting.getInstance().setMinidle(minidle);
                    RedisSetting.getInstance().setTimebetweenvictionrunsmillis(timebetweenvictionrunsmillis);
                    RedisSetting.getInstance().setTestonborrow(testonborrow);
                    RedisSetting.getInstance().setTestonreturn(testonreturn);
                    RedisSetting.getInstance().setTimeout(timeout);
                }
                catch (DataConversionException e) {
                    e.printStackTrace();
                }
            }
            Element sentinels = redis.getChild("sentinels");
            if (sentinels != null) {
                Set<String> list = new HashSet<String>();
                for (Element sentinel : sentinels.getChildren()) {
                    String host = sentinel.getAttributeValue("host");
                    list.add(host);
                }
                RedisSetting.getInstance().setSentinels(list);
            }
            try {
                //初始化Redis
                JedisClient.getInstance().initClient();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        //启动Service
        ServerManager.getInstance().loadServices();

        //发送心跳测试测试
//        try
//        {
//            for (int i = 0; i < 1; i++) {
//                SocketClient client = new SocketClient();
//                client.connect(Network.host, Network.port);
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }
}
