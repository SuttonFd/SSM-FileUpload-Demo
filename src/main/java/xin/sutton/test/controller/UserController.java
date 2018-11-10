package xin.sutton.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xin.sutton.test.authorization.annotation.Authorization;
import xin.sutton.test.authorization.annotation.CurrentUser;
import xin.sutton.test.authorization.manager.TokenManager;
import xin.sutton.test.authorization.model.TokenModel;
import xin.sutton.test.bo.User;
import xin.sutton.test.service.UserService;
import xin.sutton.test.util.MD5Util;
import xin.sutton.test.vo.ServerResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author codingZhengsz
 * @since 2018-10-23 21:22
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<ServerResponse> register(@RequestBody User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<ServerResponse> login(@RequestBody User user) {
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        User userLogin = userService.getUser(user.getUsername());
        if(userLogin == null) {
            return new ResponseEntity<>(ServerResponse.createByErrorMessage("该账户尚未注册，请注册"), HttpStatus.NOT_FOUND);
        } else {
            if(tokenManager.checkUser(TokenModel.builder().userId(userLogin.getId()).build())) {
                return new ResponseEntity<>(ServerResponse.createByErrorMessage("该用户已经登录，请不要重新登录"), HttpStatus.FORBIDDEN);
            }
            if(user.getPassword().equals(userLogin.getPassword())){
                TokenModel model = tokenManager.createToken(userLogin.getId());
                return new ResponseEntity<>(ServerResponse.createBySuccess("登录成功",userLogin.getId()+"_"+model.getToken()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ServerResponse.createByErrorMessage("账号或者密码错误"), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Authorization
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ResponseEntity<ServerResponse> logout(@CurrentUser User user) {
        tokenManager.deleteToken(user.getId());
        return new ResponseEntity<>(ServerResponse.createBySuccessMessage("退出成功"), HttpStatus.OK);
    }
}
