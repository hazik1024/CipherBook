package service;

import constants.Network;
import enums.ActionType;
import enums.ServiceType;
import network.actions.BaseAction;
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

    @Override
    public Integer getActionCode() {
        return ActionType.gateway.getTopid();
    }

    @Override
    public void addAction(BaseAction action) {

    }

    @Override
    public void ready() {
        super.ready();
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
                System.out.println("ServerSocket启动成功,监听端口:" + this.serverSocket.getLocalPort());
            }
            while(this.serverSocket != null && !Thread.interrupted()) {
                Socket socket = this.serverSocket.accept();
                socket.setSoTimeout(Network.serverTimeout);
                Integer bufferId = serverBufferId.getAndIncrement();
                ServerBuffer buffer = new ServerBuffer(socket, this, bufferId);
                ServiceSetting.getInstance().putServerBuffer(buffer);
                System.out.println("当前连接ip:" + socket.getInetAddress().getHostAddress() + " , bufferId:" + buffer.getBufferId() + " , 连接总数:" + ServiceSetting.getInstance().getServerBufferCount());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        super.stop();
    }


    /*SocketBufferable*/
    public void bufferReceive(String data) {

    }

    public void bufferWrite(Integer actionCode) {

    }

    public void bufferClose(Integer bufferId) {
        ServiceSetting.getInstance().removeServerBufferf(bufferId);
    }
}
