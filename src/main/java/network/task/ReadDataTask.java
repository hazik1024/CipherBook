package network.task;

import constants.Network;
import network.socket.ReadDatable;
import network.socket.ServerBuffer;
import network.socket.Socketable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.NetworkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

public class ReadDataTask implements Runnable {
    private Log log = LogFactory.getLog(ReadDataTask.class);

    private InputStream inputStream;
    private ServerBuffer serverBuffer;

    private byte[] receiveData = new byte[0];

    public ReadDataTask(InputStream inputStream, ServerBuffer serverBuffer) {
        this.inputStream = inputStream;
        this.serverBuffer = serverBuffer;
    }

    public void run() {
        log.info("bufferId:" + this.serverBuffer.getBufferId() + " 读线程启动...");
        SocketException socketException = null;
        do {
            try {
                byte[] bytes = new byte[Network.bufferLength];
                int len = this.inputStream.read(bytes);
                if (len < 0) {
                    log.info("ReadDataTask break...");
                    break;
                }
                if (len > 0) {
                    appendData(bytes, len);
                }
            }
            catch (SocketException e1) {
                e1.printStackTrace();
                socketException = e1;
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        while (!Thread.interrupted());
        this.serverBuffer.close(socketException);
    }

    public void close() {
        if (!Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
        }
        this.inputStream = null;
        this.serverBuffer = null;
    }

    public void appendData(byte[] data, int len) {
        byte[] bytes = new byte[this.receiveData.length + len];
        System.arraycopy(this.receiveData, 0, bytes, 0, this.receiveData.length);
        System.arraycopy(data, 0, bytes, this.receiveData.length, len);

        while (true) {
            //处理数据
            int totalLen = NetworkUtil.getTotalLength(bytes);
            if (totalLen <= Network.headLength || totalLen > bytes.length) {
                break;
            }
            try {
                byte[] body = NetworkUtil.getBody(bytes, totalLen);
                String request = new String(body, Network.encoding);
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
