package com.osite.omok.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.osite.omok.handler.LoginSuccessHandler;
import com.osite.omok.service.UserService;

import lombok.RequiredArgsConstructor;
 
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()).csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            				.headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
            				.formLogin((formLogin) -> formLogin
            						.loginPage("/user/login")
            						.successHandler(authenticationSuccessHandler)
 //           						.defaultSuccessUrl("/")
            						)
            	            .logout((logout) -> logout
            	                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            	                    .logoutSuccessUrl("/")
            	                    .invalidateHttpSession(true))
            	            .csrf()
            	            	.disable()
//            	            .ignoringRequestMatchers(
//            	                    new AntPathRequestMatcher("/h2-console/**"))
            	            
            ;
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    	return authenticationConfiguration.getAuthenticationManager();
    }
}