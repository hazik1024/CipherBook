package service.base;

import constants.GlobalConfig;
import enums.ServiceType;
import enums.Topid;
import actions.RequestAction;
import network.socket.ServerBuffer;
import network.socket.SocketBufferable;
import settings.NetworkSetting;
import settings.ServiceSetting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class GatewayService extends BaseService implements SocketBufferable {

    public GatewayService() {
        super(ServiceType.gateway, "前置服务");
    }

    public Topid getTopid() {
        return Topid.gateway;
    }

    private int port = NetworkSetting.getInstance().getServerPort();
    private ServerSocket serverSocket;
    private AtomicInteger serverBufferId = new AtomicInteger(1);
    public void running() {
        try {
            if (this.serverSocket == null) {
                InetSocketAddress address = new InetSocketAddress(this.port);
                this.serverSocket = new ServerSocket();
                this.serverSocket.bind(address);
                logger.info("ServerSocket启动成功,监听端口:" + this.serverSocket.getLocalPort());
            }
            while(this.serverSocket != null && !Thread.interrupted()) {
                Socket socket = this.serverSocket.accept();
                socket.setSoTimeout(GlobalConfig.serverTimeout);
                Integer bufferId = serverBufferId.getAndIncrement();
                ServerBuffer buffer = new ServerBuffer(socket, this, bufferId);
                ServiceSetting.getInstance().putServerBuffer(buffer);
                logger.info("当前连接ip:" + socket.getInetAddress().getHostAddress() + " , bufferId:" + buffer.getBufferId() + " , 连接总数:" + ServiceSetting.getInstance().getServerBufferCount());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void processing(RequestAction requestAction) {

    }

    public void stop() {
        super.stop();
        ServiceSetting.getInstance().removeAllServerBuffer();
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            this.serverSocket = null;
        }
    }


    /*SocketBufferable*/
    public void bufferClose(Integer bufferId) {
        ServiceSetting.getInstance().removeServerBuffer(bufferId);
    }
}
