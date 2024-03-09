package com.iris.springsecuritydemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.iris.springsecuritydemo.domain.User;
import com.iris.springsecuritydemo.mapper.MenuMapper;
import com.iris.springsecuritydemo.mapper.UserMapper;
import com.iris.springsecuritydemo.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringsecuritydemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;

    @Test
    void testpasswordencoder() {
        String encode = passwordEncoder.encode("1234");
        String encode1 = passwordEncoder.encode("1234");
        System.out.println(encode);
        System.out.println(encode1);
        System.out.println(passwordEncoder.matches("1234", "$2a$10$xv7yRpio8VOBcdVYLygVju8hgznekVvcr/NJJJ5m2PPRQZ.i4t7T3"));
    }

    @Test
    void testDB() {
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_name", "iris");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, "iris");
        User user = userMapper.selectOne(wrapper);
        passwordEncoder.encode(user.getPassword());
        System.out.println(user);
    }

    @Test
    public void testRedis(){
        Object cacheObject = redisCache.getCacheObject("login:" + 2);
        System.out.println();
    }

    @Test
    public void testMenuMapper(){
        List<String> permissionsByUserId = menuMapper.getPermissionsByUserId(4L);
        System.out.println(permissionsByUserId.get(0));
    }

}
