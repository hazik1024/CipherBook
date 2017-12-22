package network.socket;

import java.net.SocketException;

public interface Socketable {
    void appendData(byte[] data, int len);
    void close(SocketException e);
}
