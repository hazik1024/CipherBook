package network.task;

import network.actions.BaseAction;
import network.socket.ServerBuffer;
import util.NetworkUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.LinkedBlockingQueue;

public class WriteDataTask implements Runnable {

    private OutputStream outputStream;
    private ServerBuffer serverBuffer;

    private LinkedBlockingQueue<BaseAction> writeDatas = new LinkedBlockingQueue<BaseAction>();

    public WriteDataTask(OutputStream outputStream, ServerBuffer serverBuffer) {
        this.outputStream = outputStream;
        this.serverBuffer = serverBuffer;
    }

    public void addWriteData(BaseAction baseAction) {
        try {
            writeDatas.put(baseAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (!Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
        }
        writeDatas.clear();
        outputStream = null;
        serverBuffer = null;
    }

    public void run() {
        System.out.println("bufferId:" + this.serverBuffer.getBufferId() + " 写线程启动...");
        try {
            while(!Thread.currentThread().isInterrupted()) {
                BaseAction action = this.writeDatas.take();
//                byte[] bytes = NetworkUtil.getWriteData(action.pack());

                byte[] bytes = NetworkUtil.getWriteData(action.getInitialData());
                this.outputStream.write(bytes);
                serverBuffer.writed(action.getTopid());
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
}
