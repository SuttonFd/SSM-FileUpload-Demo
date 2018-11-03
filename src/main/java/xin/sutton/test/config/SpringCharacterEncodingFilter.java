package xin.sutton.test.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * TODO...
 *
 * @author codingZhengsz
 * @since 2018-10-24 13:16
 **/
public class SpringCharacterEncodingFilter implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic springEncodingFilter =
                servletContext.addFilter("SpringEncodingFilter",encodingFilter);
        springEncodingFilter.addMappingForUrlPatterns(null,false,"/*");
    }

}
