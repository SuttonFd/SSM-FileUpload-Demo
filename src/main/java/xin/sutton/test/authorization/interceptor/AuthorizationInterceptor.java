package xin.sutton.test.authorization.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.sutton.test.authorization.annotation.Authorization;
import xin.sutton.test.authorization.manager.TokenManager;
import xin.sutton.test.authorization.model.TokenModel;
import xin.sutton.test.vo.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有效
 *
 * @author codingZhengsz
 * @since 2018-10-30 16:57
 *
 **/
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    // 拦截器是一个链式调用的过程，执行的顺序是声明的顺序一次进行
    //preHandle
    // 在请求进来Web容器之后执行，返回true就执行链的下以环节，返回false，请求结束，其他方法不继续执行
    //postHandle
    // 会在DispatcherServlet进行视图返回渲染之前被调用，可以对Controller返回的ModelAndView对象进行操作
    //afterCompletion
    // 会在DispatcherServlet进行视图返回渲染之后被调用，主要做资源清理的工作
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从header中得到token
        String authorization = request.getHeader(Const.AUTHORIZATION);
        // 验证token
        TokenModel model = manager.getToken(authorization);
        if(manager.checkToken(model)) {
            // 如果token验证成功，将token对应的用户id存入request中，以便之后注入
            request.setAttribute(Const.CURRENT_USER_ID,model.getUserId());
            return true;
        }
        // 如果验证失败，并且方法注明了Authorization，返回401错误
        if(method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    /**
     * 实现HandlerInterceptorAdapter抽象类
     * 1. public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
     * 2. public void postHandle(
     * 			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
     * 			throws Exception;
     * 3. public void afterCompletion(
     * 			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
     * 			throws Exception;
     */

    /**
     * 实现WebRequestInterceptor
     * 三个方法
     * 在WebRequest中的操作都会同步到HttpServletRequest中去
     * 1. void preHandle(WebRequest request) throws Exception;
     *    请求处理之前进行调用。Controller方法执行之前调用。主要做资源的准备工作
     * 2. void postHandle(WebRequest request,ModelMap modelMap) throws Exception;
     *    Controller方法执行之后，视图渲染之前调用。可以改变数据模型ModelMap改变数据的展示
     *    WebRequest 对象是用于传递整个请求数据的，比如在preHandle 中准备的数据都可以通过WebRequest 来传递和访问；
     *    ModelMap就是Controller 处理之后返回的Model 对象，我们可以通过改变它的属性来改变返回的Model模型。
     * 3. void afterCompletion(WebRequest request,Exception ex) throws Exception;
     *    整个请求处理完成，视图返回并被渲染之后执行
     *    一般进行资源的释放
     *    WebRequest参数就可以把我们在preHandle中准备的资源传递到这里进行释放
     *    Exception参数就是当前请求的异常对象，如果Controller中抛出异常已经被Spring的异常处理器处理了的话，这个异常对象就是null
     */

    /**
     * Servlet的过滤器和Spring的拦截器
     * 1. 使用范围不同
     *    Filter是Servlet定义的，只能用在Web环境中
     *    Interceptor是Spring定义的，可以用在Web程序，Swing程序以及App中
     * 2. 使用资源不同
     *    拦截器是Spring的一个组件，配置在Spring的上下文配置文件中，所以可以与Spring的任何资源，对象(Service,数据源，事务管理)通过IoC注入到拦截器中
     *    与Spring的其他组件完美结合
     *    过滤器只能配置在web.xml中，无法获得Spring的支持
     * 3. 使用的深度不同
     *    Filter只在Servlet前后起作用；即 chain.doFilter(request, response)方法调用的前后
     *    拦截器能够深入到方法的前后（preHandle、postHandle）、异常抛出前后等，拦截器有更大的弹性
     */
}
