package network.socket;

import com.alibaba.fastjson.JSON;
import network.actions.RequestAction;
import network.task.ReadDataTask;
import network.task.WriteDataTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import settings.ServiceSetting;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerBuffer implements Socketable, ReadDatable, WriteDatable {
    private Log log = LogFactory.getLog(ServerBuffer.class);
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private SocketBufferable target;
    private int bufferId;

    private ReadDataTask readDataTask;
    private WriteDataTask writeDataTask;

    public ServerBuffer(Socket socket, SocketBufferable target, int bufferId) throws IOException {
        this.socket = socket;
        this.target = target;
        this.bufferId = bufferId;

        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();

        //初始化写线程
        this.writeDataTask = new WriteDataTask(this.outputStream, this);
        new Thread(this.writeDataTask).start();
        //初始化读线程
        this.readDataTask = new ReadDataTask(this.inputStream, this);
        new Thread(this.readDataTask).start();
    }

    public synchronized void close() {
        try {
            if (this.readDataTask != null) {
                this.readDataTask.close();
                this.readDataTask = null;
            }
            if (this.writeDataTask != null) {
                this.writeDataTask.close();
                this.writeDataTask = null;
            }
            if (this.inputStream != null) {
                this.inputStream.close();
                this.inputStream = null;
            }
            if (this.outputStream != null) {
                this.outputStream.close();
                this.outputStream = null;
            }
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        target.bufferClose(this.getBufferId());
    }

    public void addReadRequest(String request) {
        RequestAction baseAction = JSON.parseObject(request, RequestAction.class);
        baseAction.setBufferId(this.getBufferId());
        baseAction.setInitialData(request);
        ServiceSetting.getInstance().getService(baseAction.getTopid()).addAction(baseAction);
    }

    public void writed(Integer type) {
        log.info("bufferId:" + this.getBufferId() + "回复成功:" + type);
    }

    public void addSendAction(RequestAction baseAction) {
        this.writeDataTask.addWriteData(baseAction);
    }

    public int getBufferId() {
        return bufferId;
    }
}
