package com.chrl.bs.sfs;

import redis.clients.jedis.*;


/**
 * 验证权限
 *
 * @author
 * @version
 * @since 1.0
 */
public class RedisClient {

    public JedisPool masterPool ;

    public RedisClient() {
        if (Server.CONFIG.getProperty("redis.master.pass")==null||"".equalsIgnoreCase(Server.CONFIG.getProperty("redis.master.pass"))) {
            this.masterPool = new JedisPool(new JedisPoolConfig(), Server.CONFIG.getProperty("redis.master.host"), Integer.parseInt(Server.CONFIG.getProperty("redis.master.port")),Integer.parseInt(Server.CONFIG.getProperty("redis.master.timeout")));
        } else {
            this.masterPool = new JedisPool(new JedisPoolConfig(), Server.CONFIG.getProperty("redis.master.host"), Integer.parseInt(Server.CONFIG.getProperty("redis.master.port")),Integer.parseInt(Server.CONFIG.getProperty("redis.master.timeout")),Server.CONFIG.getProperty("redis.master.pass"));
        }

    }



    public static void main(String [] arg  ){
        JedisPool masterPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379,1000);
        Jedis objJedis = masterPool.getResource();
        Pipeline objPipeline = objJedis.pipelined();
        Response<String> objValue = objPipeline.get("a");
        objPipeline.del("a");
        objPipeline.expire("a", 300);
        for (double i = 0; i<1000;i++){
            objPipeline.zadd("xxx",i,"bas--"+i);
        }
        objPipeline.sync();
        masterPool.returnResource(objJedis);
        String strValue = objValue.get();
        System.out.println(strValue);
    }

}
