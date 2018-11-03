package xin.sutton.test.authorization.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import xin.sutton.test.authorization.annotation.CurrentUser;
import xin.sutton.test.bo.User;
import xin.sutton.test.dao.UserMapper;
import xin.sutton.test.vo.Const;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入到当前登录的用户
 *
 * @author codingZhengsz
 * @since 2018-10-30 18:34
 **/
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果参数类型是User并且有CurrentUser注解则支持
        if(parameter.getParameterType().isAssignableFrom(User.class) &&
            parameter.hasParameterAnnotation(CurrentUser.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 取出鉴权时存入的登录用户Id
        Long currentUserId = (Long)webRequest.getAttribute(Const.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if(currentUserId != null) {
            return userMapper.getUserByUserid(currentUserId);
        }
        throw new MissingServletRequestPartException(Const.CURRENT_USER_ID);
    }
}
