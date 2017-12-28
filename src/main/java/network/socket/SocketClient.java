package network.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import constants.Network;
import network.task.HeartbeatTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.NetworkSetting;
import util.NetworkUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class SocketClient {

    private Logger logger = LogManager.getLogger(SocketClient.class);

    private Socket socket;
    private String host;
    private int port;
    private int timeout;

    private boolean autoReconnect = true;

    private HeartbeatTask heartbeatTask;

    public SocketClient() {

    }

    public boolean connect(String host, int port)
    {
        return connect(host, port, NetworkSetting.getInstance().getTimeout(), true);
    }

    public boolean connect(String host, int port, int timeout, boolean autoReconnect)
    {
        boolean flag;
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.autoReconnect = autoReconnect;
        try
        {
            socket = new Socket();
            InetAddress ia = InetAddress.getByName(host);
            InetSocketAddress address = new InetSocketAddress(ia, port);
            socket.connect(address, timeout);
            flag =  true;

            new Thread(new Runnable() {
                private byte[] receiveData = new byte[0];
                public void run() {
                    logger.info("socketClient开始接收");
                    do {
                        try {
                            byte[] tmpBytes = new byte[Network.bufferLength];
                            int len = socket.getInputStream().read(tmpBytes);
                            if (len < 0) {
                                logger.info("ReadDataTask break...");
                                break;
                            }
                            if (len > 0) {
                                byte[] bytes = new byte[this.receiveData.length + len];
                                System.arraycopy(this.receiveData, 0, bytes, 0, this.receiveData.length);
                                System.arraycopy(tmpBytes, 0, bytes, this.receiveData.length, len);

                                while (true) {
                                    //处理数据
                                    int totalLen = NetworkUtil.getTotalLength(bytes);
                                    if (totalLen <= Network.headLength || totalLen > bytes.length) {
                                        break;
                                    }
                                    try {
                                        byte[] body = NetworkUtil.getBody(bytes, totalLen);
                                        String response = new String(body, Network.encoding);
                                        long end = System.currentTimeMillis();
                                        JSONObject json = JSON.parseObject(response);
                                        JSONObject data = json.getJSONObject("data");
                                        long time = data.getLong("time");
                                        logger.info("耗时[" + (end - time) + "]millseconds[" + this.toString() + "]收到响应:" + response);

                                    }
                                    catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    bytes = NetworkUtil.clearData(bytes, totalLen);
                                    this.receiveData = bytes;
                                }
                            }
                        }
                        catch (SocketException e) {
                            e.printStackTrace();
                            break;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    while (!Thread.interrupted());
                }
            }).start();
            heartbeatTask = new HeartbeatTask(socket.getOutputStream());
            new Timer().schedule(heartbeatTask, 2000, 5000);
        }
        catch (IOException e)
        {
            flag = false;
            e.printStackTrace();
            reconnect();
        }
        return flag;
    }

    protected void reconnect() {
        if (!this.autoReconnect) {
            return;
        }
        TimerTask task = new TimerTask() {
            public void run() {
                if (isConnected()) {
                    connect(host, port);
                }
            }
        };
        new Timer().schedule(task, 3000);
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    public boolean isConnected()
    {
        if (this.socket != null)
        {
            return this.socket.isConnected();
        }
        return false;
    }

    public void write(String data)
    {
        try {
            byte[] bytes = NetworkUtil.getWriteData(data);
            this.socket.getOutputStream().write(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close()
    {

    }
}
