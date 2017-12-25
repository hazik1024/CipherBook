package start;


import config.StartupConfig;
import constants.Network;
import network.socket.SocketClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import settings.NetworkSetting;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import settings.ServiceSetting;

import java.io.IOException;
import java.io.InputStream;

public class ServerStart {
    private static Log log = LogFactory.getLog(ServerStart.class);
    public static void main(String[] args) {
        //加载配置文件
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream inputStream =  ServerStart.class.getResourceAsStream(StartupConfig.configPath);
            doc = builder.build(inputStream);
        }
        catch (IOException e) {
            log.info("找不到systemconfig.xml文件");
            e.printStackTrace();
        }
        catch (JDOMException e) {
            log.info("找不到systemconfig.xml文件");
            e.printStackTrace();
        }
        if (doc == null) {
            log.info("找不到systemconfig.xml文件");
            return;
        }

        Element root = doc.getRootElement();
        //取数据库配置
        Element database = root.getChild("database");
        if (database != null) {
            try {
                ServerManager.getInstance().loadDatabase(database);
            } catch (DataConversionException e) {
                e.printStackTrace();
                return;
            }
        }
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

        //启动Service
        ServerManager.getInstance().loadServices();

        //发送心跳测试测试
        try
        {
            for (int i = 0; i < 1; i++) {
                SocketClient client = new SocketClient();
                client.connect(Network.host, Network.port);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
