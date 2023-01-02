package com.ibk.sb.restapi.app.config;

import com.ibk.sb.restapi.app.common.jwt.JwtRequestFilter;
import com.ibk.sb.restapi.app.common.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Profile({"dev", "stage"})
    class SecurityConfigDev extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //authorizeRequest set
            http.authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //preflight 인증 무시
//                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/**").permitAll();

            // cors and csrf set
            http.cors().configurationSource(corsConfigurationSource())
                    .and()
                    .csrf().disable();

            // 토큰이 존재하지 않을 때,
            http.exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // 스프링부트 자체 내부 필터보다 jwtRequestFilter를 우선 적용
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }

        /**
         * cors 설정
         * */
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.addAllowedOrigin("http://localhost:7401");
            configuration.addAllowedOrigin("https://localhost:7401");
            configuration.addAllowedOrigin("http://localhost:6401");

            configuration.addAllowedOrigin("http://devwwwi.ibkbox.net");
            configuration.addAllowedOrigin("https://devwwwi.ibkbox.net");

            configuration.addAllowedOrigin("http://devinvesti.ibkbox.net");
            configuration.addAllowedOrigin("https://devinvesti.ibkbox.net");
            configuration.addAllowedOrigin("https://devinvesti.ibkbox.net:8001");

            configuration.addAllowedOrigin("http://devcommerce.ibkbox.net");
            configuration.addAllowedOrigin("https://devcommerce.ibkbox.net");
            configuration.addAllowedOrigin("https://devcommerce.ibkbox.net:80/");
            configuration.addAllowedOrigin("https://devcommerce.ibkbox.net:8002/");

            configuration.addAllowedOrigin("http://devadm.ibkbox.net");
            configuration.addAllowedOrigin("https://devadm.ibkbox.net");
            configuration.addAllowedOrigin("https://devadm.ibkbox.net:80");
            configuration.addAllowedOrigin("https://devadm.ibkbox.net:8003/");

            configuration.addAllowedOrigin("http://adm.ibkbox.net");
            configuration.addAllowedOrigin("https://adm.ibkbox.net");
            configuration.addAllowedOrigin("https://adm.ibkbox.net:80");

            configuration.addAllowedOrigin("http://localhost:5000");
            configuration.addAllowedOrigin("http://localhost:5001");
            configuration.addAllowedOrigin("http://dev.ibk.org");
            configuration.addAllowedOrigin("http://dev.ibk.org:5000");
            configuration.addAllowedOrigin("http://dev.ibk.org:8100");

            configuration.addAllowedMethod("*");
            configuration.addAllowedHeader("*");
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }

    @Profile("prod")
    class SecurityConfigProd extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //authorizeRequest set
            http.authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //preflight 인증 무시
                    //.antMatchers("/api/**").authenticated()
                    .antMatchers("/**").permitAll();

            // cors and csrf set
            http.cors().configurationSource(corsConfigurationSource())
                    .and()
                    .csrf().disable();

            // 토큰이 존재하지 않을 때,
            http.exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // 스프링부트 자체 내부 필터보다 jwtRequestFilter를 우선 적용
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }

        /**
         * cors 설정
         * */
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();

            // IBK MNB
            configuration.addAllowedOrigin("http://www.ibkbox.net");
            configuration.addAllowedOrigin("https://www.ibkbox.net");

            // COMMERCE DEV
            configuration.addAllowedOrigin("http://devcommerce.ibkbox.net");
            configuration.addAllowedOrigin("https://devcommerce.ibkbox.net");
            configuration.addAllowedOrigin("https://devcommerce.ibkbox.net:80/");

            // COMMERCE PROD
            configuration.addAllowedOrigin("http://commerce.ibkbox.net");
            configuration.addAllowedOrigin("https://commerce.ibkbox.net");
            configuration.addAllowedOrigin("https://commerce.ibkbox.net:80/");

            // ADM DEV
            configuration.addAllowedOrigin("http://devadm.ibkbox.net");
            configuration.addAllowedOrigin("https://devadm.ibkbox.net");
            configuration.addAllowedOrigin("https://devadm.ibkbox.net:8003/");

            // ADM PROD
            configuration.addAllowedOrigin("http://adm.ibkbox.net");
            configuration.addAllowedOrigin("https://adm.ibkbox.net");
            configuration.addAllowedOrigin("https://adm.ibkbox.net:80/");

            configuration.addAllowedOrigin("http://dev.ibk.org");

            configuration.addAllowedMethod("*");
            configuration.addAllowedHeader("*");
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }
}
