package network.socket;

import java.net.SocketException;

public interface Socketable {
    void close(SocketException e);
}
