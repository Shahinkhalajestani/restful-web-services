package com.shahin.restfulwebservices.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.shahin.restfulwebservices.configuration.security.ApplicationUserPermission.*;
import static com.shahin.restfulwebservices.configuration.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails shahinUser = User.builder()
                .username("shahin")
                .password(passwordEncoder.encode("khalajestani"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails lyndaUser = User.builder()
                .username("lynda")
                .password(passwordEncoder.encode("password321"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails abbasUser = User.builder()
                .username("abbas")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(shahinUser,lyndaUser,abbasUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/","index","/css/*","/js/*").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/users/**")
//                .hasAnyAuthority(POST_WRITE.getPermission(),STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE,"/api/users/**")
//                .hasAnyAuthority(POST_WRITE.getPermission(),STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/api/users/**")
//                .hasAnyAuthority(POST_WRITE.getPermission(),STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/api/users").hasAnyAuthority(STUDENT_READ.getPermission())
//                .antMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
