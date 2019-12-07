package by.nc.tarazenko.security;

import by.nc.tarazenko.service.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/guests").permitAll();


               // .and()

                /*.authorizeRequests()
                .antMatchers("/tickets").hasRole("USER")

                .antMatchers(HttpMethod.GET, "/planes").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/planes").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/plane_seats").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/plane_seats").hasRole("ADMIN")

                .antMatchers("/registration").permitAll();*/

        http.csrf().disable();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.userDetailsService(userServiceImpl);
    }
}