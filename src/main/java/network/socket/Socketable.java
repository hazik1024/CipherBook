package network.socket;

import java.net.SocketException;

public interface Socketable {
    void close();
    void taskFinish();
    void taskError(SocketException se);
}
