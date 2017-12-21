package network.task;

import network.socket.SocketBuffer;

import java.io.IOException;
import java.net.SocketException;

public class ReadDataTask implements Runnable {

    private SocketBuffer socketBuffer;
    public ReadDataTask(SocketBuffer socketBuffer) {
        this.socketBuffer = socketBuffer;
    }

    public void run() {
        SocketException socketException = null;
        do {
            try {
                byte[] bytes = new byte[8192];
                int len = this.socketBuffer.getInputStream().read(bytes);
                if (len < 0) {
                    System.out.println("ReadDataTask break...");
                    break;
                }
                if (len > 0) {
                    this.socketBuffer.appendData(bytes, len);
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

        if (socketException == null) {
            this.socketBuffer.taskFinish();
        }
        else {
            this.socketBuffer.taskError(socketException);
        }
    }

    public void close() {
        Thread.currentThread().interrupt();
        this.socketBuffer = null;
    }
}
