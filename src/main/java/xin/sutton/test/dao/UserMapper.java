package xin.sutton.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xin.sutton.test.bo.User;

/**
 * TODO...
 *
 * @author codingZhengsz
 * @since 2018-10-23 21:14
 **/
@Repository
public interface UserMapper {

    @Select("select user_id as id,username,password,nickname from t_user where user_id=#{id}")
    User getUserByUserid(long id);

    @Select("select user_id as id,username,password,nickname from t_user where username=#{username}")
    User getUserByUsername(String username);

    @Insert("insert into t_user(username,password,nickname) VALUES(#{username},#{password},#{nickname})")
    int insertUser(User user);

}
