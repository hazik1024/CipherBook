package util;

import network.redis.JedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.params.sortedset.ZAddParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
    /*Set*/
//////////////////////////////////////////////////////////////////////////

    /**
     * 向Set中插入多个member
     * @param key key
     * @param members members
     * @return 可能是成功的个数,未验证
     */
    public static long sadd(String key, String... members) {
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.sadd(key, members);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获取Set中元数个数
     * @param key key
     * @return 个数
     */
    public static long scard(String key) {
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.scard(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获取元素结合
     * @param key key
     * @return set
     */
    public static Set<String> smembers(String key) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.smembers(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 从Set中移除成员
     * @param key key
     * @param members members
     * @return 可能是成功的个数,未验证
     */
    public static long srem(String key, String... members) {
        long count = 0L;
        if (members == null) {
            return count;
        }
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.srem(key, members);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 从Set中随机移除一个值
     * @param key key
     * @return 移除的值
     */
    public static String spop(String key) {
        String value = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.spop(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 从Set中随机移除count个值
     * @param key key
     * @param count count
     * @return 移除的值的集合
     */
    public static Set<String> spop(String key, long count) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.spop(key, count);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 检测元素是否为成员
     * @param key key
     * @param member member
     * @return 是否为成员
     */
    public static boolean sismember(String key, String member) {
        boolean result = false;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.sismember(key, member);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 多个key对应集合的交集
     * @param keys keys
     * @return 集合
     */
    public static Set<String> sinter(String... keys) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.sinter(keys);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 多个key对应集合的并集
     * @param keys keys
     * @return 集合
     */
    public static Set<String> sunion(String... keys) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.sunion(keys);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 多个key对应集合的差集
     * @param keys keys
     * @return 集合
     */
    public static Set<String> sdiff(String... keys) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.sdiff(keys);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 从Set中随机返回一个值
     * @param key key
     * @return 值
     */
    public static String srandmember(String key) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.srandmember(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从Set中随机返回count个值
     * @param key key
     * @return 值的列表
     */
    public static List<String> srandmember(String key, int count) {
        List<String> list = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            list = jedis.srandmember(key, count);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
    /*SortedSet*/
//////////////////////////////////////////////////////////////////////////

    /**
     * 添加一个member及其score值
     * @param key key
     * @param score score
     * @param member member
     * @return 状态码
     */
    public static Long zadd(String key, double score, String member) {
        long status = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            status = jedis.zadd(key, score, member);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    /**
     * 添加一个member及其score值
     * @param key key
     * @param score score
     * @param member member
     * @param params xx:只更新已存在的元素,不添加新元素;nx:不更新已有元素,只添加新元素;ch:更新已存在的元素且添加新元素
     * @return 状态码
     */
    public static Long zadd(String key, double score, String member, ZAddParams params) {
        long status = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            status = jedis.zadd(key, score, member, params);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    /**
     * 添加多个member及其score值
     * @param key key
     * @param scoreMembers member及其score集合
     * @return 状态码
     */
    public static Long zadd(String key, Map<String, Double> scoreMembers) {
        long status = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            status = jedis.zadd(key, scoreMembers);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 集合中元素个数
     * @param key key
     * @return 个数
     */
    public static long zcard(String key) {
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.zcard(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 返回指定区间内的成员
     * 按score值递增(从小到大)排序,相同score值的成员按字典序来排列
     * 需要成员按 score 值递减(从大到小)来排列,使用zrevrange
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * @param key key
     * @param start start
     * @param end end
     * @return 集合
     */
    public static Set<String> zrange(String key, long start, long end) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.zrange(key, start, end);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 返回指定区间内的成员
     * 按score值递减(从大到小)排序,相同score值的成员按字典序来排列
     * 需要成员按 score 值递增(从小到大)来排列,使用zrange
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * @param key key
     * @param start start
     * @param end end
     * @return 集合
     */
    public static Set<String> zrevrange(String key, long start, long end) {
        Set<String> sets = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            sets = jedis.zrevrange(key, start, end);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * 从SortedSet中移除members
     * @param key key
     * @param members members
     * @return 状态码
     */
    public Long zrem(String key, String... members) {
        Long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.zrem(key, members);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * member对应的score值加上score
     * 当member不存在时,相当于zadd
     * @param key key
     * @param score 增加的score
     * @param member member
     * @return 增加后的member的score值
     */
    public static Double zincrby(String key, double score, String member) {
        Double value = 0.0;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.zincrby(key, score, member);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 返回member的排名，按 score 值递增(从小到大)顺序排列
     * @param key key
     * @param member member
     * @return 排名
     */
    public static Long zrank(String key, String member) {
        long value = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.zrank(key, member);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 返回member的排名，按 score 值递减(从大到小)顺序排列
     * @param key key
     * @param member member
     * @return 排名
     */
    public static Long zrevrank(String key, String member) {
        long value = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.zrevrank(key, member);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    /**
     * 获取member对应的score值
     * 当member不存在时,相当于zadd
     * @param key key
     * @param member member
     * @return score值
     */
    public static Double zscore(String key, String member) {
        Double value = 0.0;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.zscore(key, member);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 统计score在min和max中的成员数量(min<=score<=max)
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 数量
     */
    public static long zcount(String key, double min, double max) {
        long count = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            count = jedis.zcount(key, min, max);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
    /*List*/
//////////////////////////////////////////////////////////////////////////

    /**
     * 计算list的元素个数
     * @param key key
     * @return 数量
     */
    public static long llen(String key) {
        long number = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            number = jedis.llen(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }
    /**
     * 设置列表中下标index的元素值value
     * @param key key
     * @param index index
     * @param value value
     * @return 成功返回OK；如果key不存在，返回一个错误
     */
    public static String lset(String key, int index, String value) {
        String result = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            result = jedis.lset(key, index, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从List中移除count个value
     * count>0从表头向表尾搜索，移除value相同的元素，数量为count
     * count<0从表尾向表头搜索，移除value相同的元素，数量为count的绝对值
     * count=0移除所有value相同的元素
     * @param key key
     * @param count count
     * @param value value
     * @return 移除的数量
     */
    public static long lrem(String key, long count, String value) {
        long number = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            number = jedis.lrem(key, count, value);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * 将values插入表头
     * 后插入的在前面，如果values=a,b,c则插入后为c,b,a,...
     * @param key key
     * @param values 1个或多个value
     * @return 列表的总长度
     */
    public static long lpush(String key, String... values) {
        long length = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            length = jedis.lpush(key, values);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }



    /**
     * 将values插入表尾
     * @param key key
     * @param values 1个或多个value
     * @return 列表的总长度
     */
    public static long rpush(String key, String... values) {
        long length = 0L;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            length = jedis.rpush(key, values);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 移除并返回头元素
     * @param key key
     * @return 移除的元素
     */
    public static String lpop(String key) {
        String value = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.lpop(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 移除并返回尾元素
     * @param key key
     * @return 移除的元素
     */
    public static String rpop(String key) {
        String value = null;
        try {
            Jedis jedis = JedisClient.getInstance().getJedis();
            value = jedis.rpop(key);
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
