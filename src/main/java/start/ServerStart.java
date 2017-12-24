package start;


import config.StartupConfig;
import constants.Network;
import network.socket.SocketClient;
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
    public static void main(String[] args) {
        //加载配置文件
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream inputStream =  ServerStart.class.getResourceAsStream(StartupConfig.configPath);
            doc = builder.build(inputStream);
        }
        catch (IOException e) {
            System.out.println("找不到systemconfig.xml文件");
            e.printStackTrace();
        }
        catch (JDOMException e) {
            System.out.println("找不到systemconfig.xml文件");
            e.printStackTrace();
        }
        if (doc == null) {
            System.out.println("找不到systemconfig.xml文件");
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
        //读取network配置
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
        try
        {
            ServiceSetting.getInstance().initializeServices();
            //发送心跳测试测试
            SocketClient client = new SocketClient();
            client.connect(Network.host, Network.port);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
