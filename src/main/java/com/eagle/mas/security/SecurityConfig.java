
package com.eagle.mas.security;

import com.eagle.mas.security.jwt.JwtFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private LogoutHandler logoutHandler;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public JwtFilterChain jwtFilterChain() {
        return new JwtFilterChain(handlerExceptionResolver);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        System.out.println("CONFIGURED SECURITY CONFIG");
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .antMatchers( "/redirectlogin", "/css/**", "/js/**","/","/loginPage").permitAll()
                .and()
                .formLogin()
                .loginPage("/") // Make sure this endpoint exists in your controller
                .and()
                .addFilterBefore(jwtFilterChain(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((sec)->sec.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout()
//                .logoutSuccessUrl("/")
//                .logoutUrl("/logout1")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","Authorization")
                .permitAll();

    }
}

