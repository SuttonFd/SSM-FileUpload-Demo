package xin.sutton.test.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的方法参数上使用这个注解，该方法在映射的时候会自动注入当前登录的对象
 *
 * @author codingZhengsz
 * @since 2018-10-30 16:55
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
