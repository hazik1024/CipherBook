import network.socket.SocketServer;
import settings.NetworkSetting;

public class TestSocketServer {
    private static SocketServer server;
    public static void main(String[] args)
    {
        try
        {
            server = new SocketServer(NetworkSetting.getInstance().getServerPort());
            server.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
