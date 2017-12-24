package network.socket;

public interface SocketBufferable {
    /*接收请求*/
    void bufferReceive(String data);
    /*发送响应*/
    void bufferWrite(Integer actionCode);
    /*连接关闭*/
    void bufferClose(Integer bufferId);
}
