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

    @Autowired
    UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/guests").hasRole("ADMIN");
               /* .antMatchers(HttpMethod.GET, "/guests").hasRole("ROlE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/guests").permitAll()
                .and()
                .authorizeRequests()


                .antMatchers(HttpMethod.GET, "/attendances").permitAll()
                .antMatchers(HttpMethod.POST, "/attendances").permitAll()
                .and().csrf().disable();*/
        /* http.httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/guests").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/guests").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/guests").permitAll()
                .and()
                .authorizeRequests()
               // .antMatchers("/tickets").hasRole("USER")

                .antMatchers(HttpMethod.GET, "/attendances").permitAll()
                .antMatchers(HttpMethod.POST, "/attendances").permitAll();*/



                //.antMatchers("/registration").permitAll();

       // http.csrf().disable();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.userDetailsService(userService);
    }
}