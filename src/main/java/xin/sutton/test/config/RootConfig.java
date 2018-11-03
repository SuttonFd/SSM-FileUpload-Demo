package xin.sutton.test.config;

import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

/**
 * 根应用上下文
 *
 * @author codingZhengsz
 * @since 2018-10-23 21:41
 **/
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = {"xin.sutton.test"},excludeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = RootConfig.WebPackage.class)
})
@Import(DataConfig.class)
public class RootConfig {

    // 内部类，用来排除 web 相关的包，这些包已经在 WebConfig 中导入
    public static class WebPackage extends RegexPatternTypeFilter{

        public WebPackage() {
            super(Pattern.compile("xin.sutton.test.web"));
        }
    }
}
