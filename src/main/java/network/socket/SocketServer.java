package network.socket;

import constants.Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;

public class SocketServer extends Thread implements Socketable {

    private int port;
    private ServerSocket serverSocket;
    private HashSet<SocketBuffer> bufferSet;
    public SocketServer(int port) {
        this.port = port;
        this.bufferSet = new HashSet<SocketBuffer>();
    }

    public void run() {
        super.run();
        try {
            if (this.serverSocket == null) {
                InetSocketAddress address = new InetSocketAddress(this.port);
                this.serverSocket = new ServerSocket();
                this.serverSocket.bind(address);
                System.out.println("ServerSocket启动成功:" + this.serverSocket.getLocalPort());
            }
            while(!this.isInterrupted() && this.serverSocket != null) {
                Socket socket = this.serverSocket.accept();
                socket.setSoTimeout(Network.serverTimeout);
                SocketBuffer socketBuffer = new SocketBuffer(socket, false);
                socketBuffer.setTarget(this);
                this.bufferSet.add(socketBuffer);
                System.out.println("请求总数:" + this.bufferSet.size());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.interrupt();
        if (this.bufferSet != null) {
            clearSocketBuffer();
            this.bufferSet = null;
        }
        try {
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void taskError(SocketException se) {
        clearSocketBuffer();
    }

    public void taskFinish() {
        clearSocketBuffer();
    }

    private void clearSocketBuffer() {
        HashSet<SocketBuffer> set = new HashSet<SocketBuffer>();
        for (SocketBuffer buffer: this.bufferSet) {
            if (!buffer.isConnected()) {
                set.add(buffer);
            }
        }
        this.bufferSet.removeAll(set);
        System.out.println("当前连接数:" + this.bufferSet.size());
    }
}
