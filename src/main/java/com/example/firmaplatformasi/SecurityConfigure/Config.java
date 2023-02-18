package com.example.firmaplatformasi.SecurityConfigure;

import com.example.firmaplatformasi.Filter.Filtr;
import com.example.firmaplatformasi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@Configuration
public class Config extends WebSecurityConfigurerAdapter {
    @Autowired
    Filtr filtr;

    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/hodim/login").permitAll()
                .antMatchers("/hodim/tasdiqla").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic().disable();

        http.addFilterBefore(filtr, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
//        mailSender.setUsername("azamatbekromizov@gmail.com");
//        mailSender.setPassword("pifvbstbsfufvesk");
        mailSender.setUsername("urazaliabduvaliyev@gmail.com");
        mailSender.setPassword("wkbevuimxspddxyv");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

}
