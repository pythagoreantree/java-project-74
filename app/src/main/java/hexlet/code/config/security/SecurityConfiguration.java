package hexlet.code.config.security;

import hexlet.code.component.JWTHelper;

import hexlet.code.filter.JWTAuthenticationFilter;
import hexlet.code.filter.JWTAuthorizationFilter;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.*;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static hexlet.code.controller.UsersController.USER_CONTROLLER_PATH;

@Configuration
@Import(EncoderFactory.class)
public class SecurityConfiguration {
    public static final String LOGIN = "/login";
    public static final List<GrantedAuthority> DEFAULT_AUTHORITIES = List.of(new SimpleGrantedAuthority("USER"));
    private final RequestMatcher publicUrls;
    private final RequestMatcher loginRequest;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;

    public SecurityConfiguration(@Value("${base-url}") final String baseUrl,
                                 final UserDetailsService userDetailsService,
                                 final PasswordEncoder passwordEncoder,
                                 final JWTHelper jwtHelper) {
        this.loginRequest = new AntPathRequestMatcher(baseUrl + LOGIN, POST.toString());
        this.publicUrls = new OrRequestMatcher(
                loginRequest,
                new AntPathRequestMatcher(baseUrl + USER_CONTROLLER_PATH, POST.toString()),
                new AntPathRequestMatcher(baseUrl + USER_CONTROLLER_PATH, GET.toString()),
                new NegatedRequestMatcher(new AntPathRequestMatcher(baseUrl + "/**"))
        );
        this.jwtHelper = jwtHelper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        final var authenticationFilter = new JWTAuthenticationFilter(
                loginRequest,
                jwtHelper
        );

        final var authorizationFilter = new JWTAuthorizationFilter(
                publicUrls,
                jwtHelper
        );

        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(publicUrls).permitAll()
            .anyRequest().authenticated()
            .and()

//            .addFilter(authenticationFilter)
//            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)

            .sessionManagement().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable();

        return http.build();
    }
}

