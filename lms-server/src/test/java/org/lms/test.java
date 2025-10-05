package org.lms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Categories;
import org.lms.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

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
