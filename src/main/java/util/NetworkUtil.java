package util;

import constants.Network;

import java.io.UnsupportedEncodingException;

public class NetworkUtil {

    public static int getTotalLength(byte[] data) {
        if (data.length <= Network.headLength) {
            return 0;
        }
        int length = (data[0] & 0xff << 24) | (data[1] & 0xff >> 16) | (data[2] & 0xff >> 8) | data[3] & 0xff;
        return length;
    }

    public static byte[] getBody(byte[] bytes, int totalLength) {
        byte[] body = new byte[totalLength - Network.headLength];
        System.arraycopy(bytes, Network.headLength, body, 0, body.length);
        return body;
    }

    public static byte[] clearData(byte[] bytes, int totalLength) {
        //清理receiveData
        if (bytes.length > totalLength) {
            byte[] other = new byte[bytes.length - totalLength];
            System.arraycopy(bytes, totalLength, other, 0, other.length);
            return other;
        }
        else {
            return new byte[0];
        }
    }

    public static byte[] toHH(int n) {
        byte[] b = new byte[Network.headLength];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static byte[] getWriteData(String data) throws UnsupportedEncodingException {
        byte[] body = data.getBytes(Network.encoding);
        byte[] head = toHH(Network.headLength + body.length);
        byte[] bytes = new byte[Network.headLength + body.length];
        System.arraycopy(head, 0, bytes, 0, head.length);
        System.arraycopy(body, 0, bytes, head.length, body.length);
        return bytes;
    }
}
