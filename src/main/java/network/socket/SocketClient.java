package network.socket;

import settings.NetworkSetting;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class SocketClient implements Socketable {

    private SocketBuffer socketBuffer;
    private String host;
    private int port;
    private int timeout;

    private boolean autoReconnect = true;

    public SocketClient() {
    }

    public boolean connect(String host, int port)
    {
        return connect(host, port, NetworkSetting.getInstance().getTimeout(), true);
    }

    public boolean connect(String host, int port, int timeout, boolean autoReconnect)
    {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.autoReconnect = autoReconnect;
        try
        {
            Socket socket = new Socket();
            InetAddress ia = InetAddress.getByName(host);
            InetSocketAddress address = new InetSocketAddress(ia, port);
            socket.connect(address, timeout);
            this.socketBuffer = new SocketBuffer(socket, true);
            this.socketBuffer.setTarget(this);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            reconnect();
        }
        return false;
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
        if (this.socketBuffer != null)
        {
            return this.socketBuffer.isConnected();
        }
        return false;
    }

    public void write(String data)
    {
        if (this.socketBuffer == null || !this.socketBuffer.isConnected()) {
            this.close();
            this.connect(this.host, this.port, this.timeout, true);
        }
        this.socketBuffer.write(data);
    }

    public void close()
    {
        if (this.socketBuffer != null)
        {
            this.socketBuffer.close();
            this.socketBuffer = null;
        }
    }

    public void taskFinish() {
        reconnect();
    }

    public void taskError(SocketException se) {
            reconnect();
    }
}
