package dev.fernando.agileblog.configs.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<ContentSecurityPolicyFilter> contentSecurityPolicyFilter() {
        FilterRegistrationBean<ContentSecurityPolicyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ContentSecurityPolicyFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
