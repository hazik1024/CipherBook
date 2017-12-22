package network.task;

import network.socket.Socketable;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class ReadDataTask implements Runnable {

    private InputStream stream;
    private Socketable target;
    public ReadDataTask(InputStream stream, Socketable target) {
        this.target = target;
    }

    public void run() {
        SocketException socketException = null;
        do {
            try {
                byte[] bytes = new byte[8192];
                int len = this.stream.read(bytes);
                if (len < 0) {
                    System.out.println("ReadDataTask break...");
                    break;
                }
                if (len > 0) {
                    this.target.appendData(bytes, len);
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
        this.target.close(socketException);
    }

    public void close() {
        Thread.currentThread().interrupt();
        this.target = null;
    }
}
