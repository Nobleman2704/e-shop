package com.example.eshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.sicnt.mysoliq.security.config.AccountFilter;
import uz.sicnt.mysoliq.security.config.AuthBasicAuthenticationEntryPoint;

import java.util.Arrays;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccountFilter accountFilter;
    private final AuthBasicAuthenticationEntryPoint authBasicAuthenticationEntryPoint;

    public SecurityConfig(AccountFilter accountFilter, AuthBasicAuthenticationEntryPoint authBasicAuthenticationEntryPoint) {
        this.accountFilter = accountFilter;
        this.authBasicAuthenticationEntryPoint = authBasicAuthenticationEntryPoint;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/assets/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/playground")
                .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/user/captcha/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/v2/api-docs",           // swagger
                        "/**/v3/api-docs/**",           // swagger
                        "/business-trip-api/v3/api-docs/**",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/**/swagger-resources/**",  // swagger-ui resources
                        "/**/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/inspector-playground",
                        "/**/*.js"

                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authBasicAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .addFilterBefore(accountFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/login/logout");


        http.csrf().ignoringAntMatchers("/**", "/webjars/**", "/swagger-ui.html");
        http.cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("Accept", "Accept-Language",
                "Origin", "Content-Language", "Content-Type", "X-Requested-With", "Authorization"));
        corsConfiguration.setExposedHeaders(Arrays.asList("X-Total-Count", "Content-Disposition"));
        corsConfiguration.setMaxAge(48000L);


        corsConfiguration.addAllowedOrigin("*");

        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH"));
        source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());

        return source;
    }
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
