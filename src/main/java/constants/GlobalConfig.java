package constants;

public class GlobalConfig {

    /**
     * 系统配置文件
     */
    public static final String CONFIG_PATH = "../systemconfig.xml";

    /**
     * 默认server监听端口
     */
    public static final int serverPort = 55521;

    /**
     * 接收数据默认buffer长度
     */
    public static final int bufferLength = 8192;

    /**
     * 数据编码格式
     */
    public static final String encoding = "utf-8";

    /**
     * Socket请求头部长度
     */
    public static final int headLength = 4;

    /**
     * Socket客户端连接超时时间,默认30秒=30000毫秒
     */
    public static final int serverTimeout = 30000;


    /**
     * 用户登录token默认过期时间
     */
    public static final int TOKEN_EXPIRE = 86400;
}
