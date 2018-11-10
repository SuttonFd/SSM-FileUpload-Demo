package xin.sutton.test.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Properties;

/**
 * 数据库配置文件
 *
 * @author codingZhengsz
 * @since 2018-10-23 21:34
 **/
@Configuration
@EnableTransactionManagement // 开启事务管理
@MapperScan(basePackages = "xin.sutton.test.dao")
public class DataConfig {

    @Autowired
    Environment env;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        // 配置基本属性
        dataSource.setDriverClassName(env.getProperty("db.driverClassName"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);

        // 等待连接超时时间
        dataSource.setMaxWait(60000);

        // 进行一次检查的关闭空闲连接的间隔时间，单位是毫秒。这里是一分钟
        dataSource.setTimeBetweenEvictionRunsMillis(60000);

        // 配置一个连接在池中的最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setValidationQuery("SELECT 'X'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        // 是否打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DruidDataSource ds,PageInterceptor pageInterceptor) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(ds);
        Interceptor[] plugins = {pageInterceptor};
        sessionFactoryBean.setPlugins(plugins);
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PageInterceptor getPageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value","true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }


}
