package xin.sutton.test.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import xin.sutton.test.bo.User;
import xin.sutton.test.config.RootConfig;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @Rollback
    public void insertUserTest(){
        User user = User.builder().nickname("勺子").username("spoon").password("123333").build();
        System.out.println(userMapper.insertUser(user));
    }

    @Test
    public void getUserByUsernameTest(){
        User user = userMapper.getUserByUsername("sss");
        Assert.assertEquals("sss",user.getPassword());
    }

    @Test
    public void getUserByUseridTest(){
        User user = userMapper.getUserByUserid(1);
        Assert.assertEquals("sss",user.getPassword());
    }
}