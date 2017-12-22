package service;

import com.mysql.fabric.Server;
import constants.Network;
import enums.ActionType;
import enums.ServiceType;
import network.socket.ServerBuffer;
import network.socket.SocketBufferable;
import settings.NetworkSetting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GatewayService extends BaseService implements SocketBufferable {

    public GatewayService() {
        super(ServiceType.gateway, "GatewayService");
    }

    @Override
    public Integer getActionCode() {
        return ActionType.gateway.getCode();
    }

    @Override
    public void ready() {
        super.ready();
    }

    private int port = NetworkSetting.getInstance().getServerPort();
    private HashMap<String, ServerBuffer> buffers = new HashMap<String, ServerBuffer>();
    private ServerSocket serverSocket;
    public void running() {
        try {
            if (this.serverSocket == null) {
                InetSocketAddress address = new InetSocketAddress(this.port);
                this.serverSocket = new ServerSocket();
                this.serverSocket.bind(address);
                System.out.println("ServerSocket启动成功:" + this.serverSocket.getLocalPort());
            }
            while(this.serverSocket != null) {
                Socket socket = this.serverSocket.accept();
                socket.setSoTimeout(Network.serverTimeout);
                ServerBuffer buffer = new ServerBuffer(socket, this);
                this.buffers.put(socket.getInetAddress().getHostAddress(), buffer);
                System.out.println("当前连接ip:" + socket.getInetAddress().getHostAddress() + " , 连接总数:" + this.buffers.size());
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



    public void bufferClose(String bufferKey) {
        if (bufferKey != null && bufferKey.length() > 0) {
            this.buffers.remove(bufferKey);
        }
    }
}
