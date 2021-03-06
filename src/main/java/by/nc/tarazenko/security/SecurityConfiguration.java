package by.nc.tarazenko.security;

import by.nc.tarazenko.service.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    private final
    UserServiceImpl userService;

    @Autowired
    public SecurityConfiguration(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/guests").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/guests/{guestId}/attendances/{attendanceId}").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/guests").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/guests/{id}").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/guests/{id}/attendances").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/guests/{id}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/guests/{id}").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/attendances").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/attendances/{id}").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/attendances").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/attendances/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/attendances/{id}").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/features").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/features/{id}").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/features").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/features/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/features/{id}").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/rooms").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/rooms/{id}").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/rooms").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/rooms/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/rooms/{id}").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/reservations").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/reservations/{id}").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/reservations").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/reservations/{id}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/reservations/{id}").hasRole("USER")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("auth_code", "JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll();

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/webjars/**", "/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui.html/**", "/swagger-ui.html#/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}