package network.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import settings.RedisSetting;

public class JedisClient {
    private static JedisClient instance = new JedisClient();
    public static JedisClient getInstance() {
        return instance;
    }
    private JedisSentinelPool pool;
    private JedisClient() {

    }

    public void initClient() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(RedisSetting.getInstance().getMaxtotal());
        config.setMaxIdle(RedisSetting.getInstance().getMaxidle());
        config.setMinIdle(RedisSetting.getInstance().getMinidle());
        config.setTimeBetweenEvictionRunsMillis(RedisSetting.getInstance().getTimebetweenvictionrunsmillis());
        config.setTestOnBorrow(RedisSetting.getInstance().isTestonborrow());
        config.setTestOnReturn(RedisSetting.getInstance().isTestonreturn());
        pool = new JedisSentinelPool(RedisSetting.getInstance().getMastername(),RedisSetting.getInstance().getSentinels(), config, RedisSetting.getInstance().getTimeout(), RedisSetting.getInstance().getPassword());
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    public void close() {
        if (pool != null) {
            pool.destroy();
        }
    }
}
