package network.task;

import util.NetworkUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.TimerTask;

public class HeartbeatTask extends TimerTask {
    private OutputStream outputStream;
    public HeartbeatTask(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public void run() {
        try {
            String data = "{\"topid\" : \"100000\",\"data\" : {\"time\":" + System.currentTimeMillis() + "},\"extdata\" : {\"appversion\" : \"1.6.1\",\"termtype\" : \"2\",\"sourcetype\" : \"0\",}}";
            byte[] bytes = NetworkUtil.getWriteData(data);
            this.outputStream.write(bytes);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
