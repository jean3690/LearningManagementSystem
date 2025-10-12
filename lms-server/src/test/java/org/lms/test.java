package org.lms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest(
        classes = LmsServerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class test {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void name(){
//        Categories categories = new Categories();
//        categories.setName("ffjlfsd");
//        stringRedisTemplate.opsForHash().putAll("111111",RedisUtil.objToHash(categories));
//        Field[] fields = Categories.class.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            String name = field.getName();
//            System.out.println(stringRedisTemplate.opsForHash().get("111111", name));
//        }
    }
}
