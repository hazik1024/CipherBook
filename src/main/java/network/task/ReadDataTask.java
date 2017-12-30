package network.task;

import constants.GlobalConfig;
import network.socket.ServerBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.NetworkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

public class ReadDataTask implements Runnable {
    private Logger logger = LogManager.getLogger(ReadDataTask.class);

    private InputStream inputStream;
    private ServerBuffer serverBuffer;

    private byte[] receiveData = new byte[0];

    public ReadDataTask(InputStream inputStream, ServerBuffer serverBuffer) {
        this.inputStream = inputStream;
        this.serverBuffer = serverBuffer;
    }

    public void run() {
        logger.info("bufferId:" + this.serverBuffer.getBufferId() + " 读线程启动...");
        boolean running = true;
        do {
            try {
                byte[] bytes = new byte[GlobalConfig.bufferLength];
                int len = this.inputStream.read(bytes);
                if (len < 0) {
                    logger.info("ReadDataTask break...");
                    break;
                }
                if (len > 0) {
                    appendData(bytes, len);
                }
            }
            catch (SocketException e1) {
                e1.printStackTrace();
                running = false;
            }
            catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        while (running && !Thread.interrupted());
        this.serverBuffer.close();
    }

    public void close() {
        if (!Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
        }
        this.inputStream = null;
        this.serverBuffer = null;
    }

    private void appendData(byte[] data, int len) {
        byte[] bytes = new byte[this.receiveData.length + len];
        System.arraycopy(this.receiveData, 0, bytes, 0, this.receiveData.length);
        System.arraycopy(data, 0, bytes, this.receiveData.length, len);

        while (true) {
            //处理数据
            int totalLen = NetworkUtil.getTotalLength(bytes);
            if (totalLen <= GlobalConfig.headLength || totalLen > bytes.length) {
                break;
            }
            try {
                byte[] body = NetworkUtil.getBody(bytes, totalLen);
                String request = new String(body, GlobalConfig.encoding);
                this.serverBuffer.addReadRequest(request);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            bytes = NetworkUtil.clearData(bytes, totalLen);
            this.receiveData = bytes;
        }
    }
}
