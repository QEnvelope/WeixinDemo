package whu.irlab.weixin.demo.util.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

/**
 * Redis工具类
 * @author lisf
 * @date 2019/10/9
 */

@Component
public class RedisUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值或多个值
     */
    public void del(String... key){
        if(key!=null && key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }
            else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     * @param key
     * @return
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
