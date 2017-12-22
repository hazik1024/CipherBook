package network.socket;

import constants.Network;
import network.task.ReadDataTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

public class ServerBuffer implements Runnable, Socketable {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private SocketBufferable target;

    private byte[] receiveData;
    private ReadDataTask readDataTask;

    public ServerBuffer(Socket socket, SocketBufferable target) throws IOException {
        this.socket = socket;
        this.target = target;

        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();
        this.receiveData = new byte[0];
        //初始化读线程
        this.readDataTask = new ReadDataTask(this.inputStream, this);
        new Thread(this.readDataTask).start();
    }

    public void run() {

    }

    public void appendData(byte[] data, int len) {
        byte[] bytes = new byte[this.receiveData.length + len];
        System.arraycopy(this.receiveData, 0, bytes, 0, this.receiveData.length);
        System.arraycopy(data, 0, bytes, this.receiveData.length, len);

        while (true) {
            //处理数据
            int totalLen = getTotalLength(bytes);
            if (totalLen <= Network.headLength || totalLen > bytes.length) {
                break;
            }

            byte[] body = new byte[totalLen - Network.headLength];
            System.arraycopy(bytes, Network.headLength, body, 0, body.length);
            //doSomething
            try {
                String response = new String(body, Network.encoding);
                System.out.println(response);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //清理receiveData
            if (bytes.length > totalLen) {
                byte[] other = new byte[bytes.length - totalLen];
                System.arraycopy(bytes, totalLen, other, 0, other.length);
                this.receiveData = other;
            }
            else {
                this.receiveData = new byte[0];
                break;
            }
        }
    }

    public int getTotalLength(byte[] data) {
        if (data.length <= Network.headLength) {
            return 0;
        }
        int length = (data[0] & 0xff << 24) | (data[1] & 0xff >> 16) | (data[2] & 0xff >> 8) | data[3] & 0xff;
        return length;
    }

    public byte[] toHH(int n) {
        byte[] b = new byte[Network.headLength];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public void close(SocketException e) {
        try {
            if (this.readDataTask != null) {
                this.readDataTask.close();
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
    }

    public void write(String data) {
        try {
            byte[] body = data.getBytes(Network.encoding);
            byte[] head = toHH(Network.headLength + body.length);
            byte[] bytes = new byte[Network.headLength + body.length];
            System.arraycopy(head, 0, bytes, 0, head.length);
            System.arraycopy(body, 0, bytes, head.length, body.length);
            this.outputStream.write(bytes);
        }
        catch (SocketException e1) {
            e1.printStackTrace();
            this.close(e1);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
