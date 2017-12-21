import constants.Network;
import network.socket.SocketClient;

import java.util.HashMap;
import java.util.Map;

public class TestSocketClient {
    private static SocketClient client;
    public static void main(String[] args)
    {
        try
        {
            client = new SocketClient();
            client.connect(Network.host, Network.port);
//            client.connect("192.168.41.12", 5300);
            HashMap<String, String> data = new HashMap<String, String>();
            HashMap<String, String> extdata = new HashMap<String, String>();
            extdata.put("appversion", "1.0.0");
            extdata.put("termtype", "2");
            extdata.put("sourcetype", "0");
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("topid", "100000");
            params.put("data", data);
            params.put("extdata", extdata);
            String dataStr = mapToJSON(params);
            System.out.println(dataStr);
//            String data = "{\"topid\" : \"100000\",\"data\" : {},\"extdata\" : {\"appversion\" : \"1.6.1\",\"termtype\" : \"2\",\"sourcetype\" : \"0\",}}";

            client.write(dataStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String mapToJSON(HashMap<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\"");
            builder.append(":");
            if (entry.getValue() instanceof  HashMap) {
                HashMap<String, Object> entryMap = (HashMap<String, Object>)entry.getValue();
                builder.append(mapToJSON(entryMap)).append(",");
            }
            else {
                builder.append(entry.getValue()).append(",");
            }
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("}");
        return  builder.toString();
    }
}
