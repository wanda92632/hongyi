package com.wanda.hongyi.config;

import com.wanda.hongyi.config.jjwt.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 拦截器
 *
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/22 21:03
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    // 将拦截器 Bean 化
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOrigins("*")
//                    //暴露header中的其他属性给客户端应用程序
//                    //如果不设置这个属性前端无法通过response header获取到Authorization也就是token
//                    .exposedHeaders("Authorization")
//                    .allowCredentials(true)
//                    .allowedMethods("GET", "POST", "DELETE", "PUT")
//                    .maxAge(3600);
        //允许全部跨域请求
        registry.addMapping("/hongyi/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //排除资源以及登录接口的拦截的拦截
        String[] excludes = new String[]{"/hongyi/admin/login"};
        //添加拦截器
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
    }
}
