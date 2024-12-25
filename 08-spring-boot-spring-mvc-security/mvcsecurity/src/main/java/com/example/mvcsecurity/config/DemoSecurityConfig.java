package com.example.mvcsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // Add JDBC support
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/").hasAnyRole("MANAGER", "Employee", "ADMIN")
                        .requestMatchers("/leader/**").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers("/system/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin(form ->
                        form.loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout ->
                        logout.permitAll()
                )
                .exceptionHandling(exception ->
                    exception.accessDeniedPage("/accessDenied")
                );
        return http.build();
    }

    //    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//
//        UserDetails osama = User.builder()
//                .username("Osama")
//                .password("{noop}test123")
//                .roles("Employee")
//                .build();
//
//        UserDetails nimra = User.builder()
//                .username("Nimra")
//                .password("{noop}test123")
//                .roles("Employee", "Manager")
//                .build();
//
//        UserDetails fahim = User.builder()
//                .username("Fahim")
//                .password("{noop}test123")
//                .roles("Employee", "Manager", "Admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(osama, nimra, fahim);
//    }
}
