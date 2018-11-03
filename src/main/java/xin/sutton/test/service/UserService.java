package xin.sutton.test.service;

import org.springframework.http.ResponseEntity;
import xin.sutton.test.bo.User;
import xin.sutton.test.vo.ServerResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * TODO...
 *
 * @author codingZhengsz
 * @since 2018-10-23 21:23
 **/
public interface UserService {

    ResponseEntity<ServerResponse> register(User user);

//    ResponseEntity<ServerResponse> login(User userLogin);

    User getUser(String username);

//    ResponseEntity<ServerResponse> logout(User user);
}
