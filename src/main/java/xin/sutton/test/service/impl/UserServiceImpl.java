package xin.sutton.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xin.sutton.test.authorization.manager.TokenManager;
import xin.sutton.test.authorization.model.TokenModel;
import xin.sutton.test.bo.User;
import xin.sutton.test.dao.UserMapper;
import xin.sutton.test.service.UserService;
import xin.sutton.test.util.MD5Util;
import xin.sutton.test.vo.ServerResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author codingZhengsz
 * @since 2018-10-23 21:23
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public ResponseEntity<ServerResponse> register(User user) {
        if(userMapper.getUserByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(ServerResponse.createByErrorMessage("该用户已被注册，请重试！"), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        if(userMapper.insertUser(user) == 1){
            return new ResponseEntity<>(ServerResponse.createBySuccessMessage("注册成功"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ServerResponse.createByErrorMessage("注册失败，请检查是否有异常输入"), HttpStatus.BAD_REQUEST);
        }
    }

    public User getUser(String username) {
        return userMapper.getUserByUsername(username);
    }

//    public ResponseEntity<ServerResponse> login(User userLogin) {
//        User user = userMapper.getUserByUsername(userLogin.getUsername());
//        if(user == null) {
//            return new ResponseEntity<>(ServerResponse.createByErrorMessage("该账户尚未注册，请注册"), HttpStatus.NOT_FOUND);
//        } else {
//            if(tokenManager.checkUser(TokenModel.builder().userId(user.getId()).build())) {
//                return new ResponseEntity<>(ServerResponse.createByErrorMessage("该用户已经登录，请不要重新登录"), HttpStatus.FORBIDDEN);
//            }
//            if(user.getPassword().equals(userLogin.getPassword())){
//                TokenModel model = tokenManager.createToken(user.getId(),user.getNickname());
//                return new ResponseEntity<>(ServerResponse.createBySuccess("登录成功",model.getToken()), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(ServerResponse.createByErrorMessage("账号或者密码错误"), HttpStatus.BAD_REQUEST);
//            }
//        }
//    }

//    public ResponseEntity<ServerResponse> logout(User user){
//        tokenManager.deleteToken(user.getId());
//        return new ResponseEntity<>(ServerResponse.createBySuccessMessage("退出成功"), HttpStatus.OK);
//    }

}
