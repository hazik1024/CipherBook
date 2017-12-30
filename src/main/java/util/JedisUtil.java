package util;

import network.redis.JedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.List;

public class JedisUtil {
//////////////////////////////////////////////////////////////////////////
    /*key*/
//////////////////////////////////////////////////////////////////////////
    /**
     * 查询key的过期时间
     * @param key key
     * @return 设置的过期时间
     */
    public static long ttl(String key) {
        long expireSeconds = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            expireSeconds = jedis.ttl(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return expireSeconds;
    }
    /**
     * 设置过期时间
     * @param key key
     * @param expireSeconds 过期时间
     * @return 影响的条目数
     */
    public static long expire(String key, int expireSeconds) {
        if (expireSeconds <= 0) {
            return 0;
        }
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.expire(key, expireSeconds);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    /**
     * 取消过期时间设置
     * @param key key
     * @return 影响的条目数
     */
    public static long persist(String key) {
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.persist(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 清空所有的key
     * @return 不知道是啥
     */
    public static String flushAll() {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.flushAll();
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除key
     * @param keys 删除的多个key
     * @return 删除的条目数
     */
    public static long del(String... keys) {
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.del(keys);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 判断key是否存在
     * @param key key
     * @return 是否存在
     */
    public static boolean exists(String key) {
        boolean exist = true;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            exist = jedis.exists(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 重命名key
     * @param oldKey 旧key
     * @param newKey 新key
     * @return 状态码
     */
    public static String rename(String oldKey, String newKey) {
        String status = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            status = jedis.rename(oldKey, newKey);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 重命名key,不存在newKey才会执行
     * @param oldKey 旧key
     * @param newKey 新key
     * @return 状态码
     */
    public static long renamenx(String oldKey, String newKey) {
        long status = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            status = jedis.renamenx(oldKey, newKey);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 排序或limit
     * @param key key
     * @return 排序后的列表
     */
    public static List<String> sort(String key) {
        List<String> list = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            list = jedis.sort(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 排序或limit
     * @param key key
     * @param params 排序规则
     * @return 排序后的列表
     */
    public static List<String> sort(String key, SortingParams params) {
        List<String> list = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            list = jedis.sort(key, params);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String type(String key) {
        String type = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            type = jedis.type(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }
//////////////////////////////////////////////////////////////////////////
    /*String*/
//////////////////////////////////////////////////////////////////////////

    /**
     * 获取value
     * @param key key
     * @return value
     */
    public static String get(String key) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.get(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置key-value,会覆盖原来的value
     * @param key key
     * @param value value
     * @return 状态码
     */
    public static String set(String key, String value) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.set(key, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置key-value,仅key不存在时才成功
     * @param key key
     * @param value value
     * @return 状态码(1成功,key不存在 0不成功,key存在)
     */
    public static long setnx(String key, String value) {
        long result = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.setnx(key, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
     * 例:String str1="123456789";<br/>
     * 对str1操作后setRange(key,4,0000)，str1="123400009";
     * @param key key
     * @param value value
     * @return value的长度
     */
    public static long setrange(String key, String value, long offset) {
        long length = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            length = jedis.setrange(key, offset, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 对key对应的value进行截取
     * @param key key
     * @param startoffset 开始位置(包含)
     * @param endoffset 结束位置(包含)
     * @return 截取的值
     */
    public static String getrange(String key, long startoffset, long endoffset) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.getrange(key, startoffset, endoffset);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 在指定的key之后追加数据
     * @param key key
     * @param value 追加的value
     * @return 追加后的value总长度
     */
    public static long append(String key, String value) {
        long length = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            length = jedis.append(key, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 设置key-value-expire
     * @param key key
     * @param value value
     * @param seconds 过期时间(秒)
     * @return 状态码
     */
    public static String setex(String key, String value, int seconds) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.setex(key, seconds, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * key对应的value增加1
     * @param key key
     * @return value=value+1
     */
    public static long incr(String key) {
        long value = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.incr(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * key对应的value减去1
     * @param key key
     * @return value=value-1
     */
    public static long decr(String key) {
        long value = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.decr(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * key对应的value增加number
     * @param key key
     * @return value=value+number
     */
    public static long incrBy(String key, long number) {
        long value = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.incrBy(key, number);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * key对应的value减去number
     * @param key key
     * @return value=value-number
     */
    public static long decrBy(String key, long number) {
        long value = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.decrBy(key, number);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取并设置key对应的value
     * 如果key存在,则返回之前的value,否则返回null
     * @param key key
     * @param value 准备设置的value
     * @return 原始的value或null
     */
    public static String getSet(String key, String value) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.getSet(key, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量获取记录,如果指定的key不存在,则list中对应的位置为null
     * @param keys key
     * @return key对应的列表
     */
    public static List<String> mget(String... keys) {
        List<String> list = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            list = jedis.mget(keys);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 批量设置key-value
     * @param keysvalues keysvalues="key1","value1","key2","value2"
     * @return 状态码
     */
    public static String mset(String... keysvalues) {
        String status = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            status = jedis.mset(keysvalues);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 对应value的长度
     * @param key key
     * @return value的长度
     */
    public static long strlen(String key) {
        long length = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            length = jedis.strlen(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

//////////////////////////////////////////////////////////////////////////
    /*Set*/
//////////////////////////////////////////////////////////////////////////

}
