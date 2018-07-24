import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Scanner;

/**
 * @author sunligang
 * @date 2018/07/23
 */

public class RedisBitmapTest {

    Jedis jedis;

    @Before
    public void connection() {
        jedis = new Jedis("localhost");
        // jedis.auth("123456");
    }

    @Test
    public void testConnection() {
        System.out.println("服务正在运行: " + jedis.ping());
    }


    /**
     * 创建了一个bitmap
     */
    @Test
    public void createBitmap(){
        // 0  = 1100
        jedis.set("bitmap","0");
        System.out.println(jedis.bitcount("bitmap"));
        System.out.println(jedis.setbit("bitmap",2,false));
        System.out.println(jedis.setbit("bitmap",3,false));
        System.out.println(jedis.bitcount("bitmap"));

        System.out.println(jedis.setbit("bitmap",10000000L,true));
        System.out.println(jedis.setbit("bitmap",112L,true));
        System.out.println(jedis.setbit("bitmap",124141515L,true));
        Random random = new Random();
        //随机设置10000
        long onlineUserNum = 10000000L;
        long span = 1000000000L/onlineUserNum;
        long offset = 0;
        for(int i = 0 ; i < onlineUserNum ;i++ ){
            offset +=  span;
            System.out.println(offset);
            System.out.println(jedis.setbit("bitmap", offset,true));
        }

        // 保证有10亿
        System.out.println(jedis.setbit("bitmap",1000000000L,true));


        long old = System.currentTimeMillis();
        long count = jedis.bitcount("bitmap");
        long now = System.currentTimeMillis();
        System.out.println(now-old);

    }


}
