package ua.devteam.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ua.devteam.entity.enums.Role.*;

@Configuration
@EnableAspectJAutoProxy
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService usersService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUsersService(UserDetailsService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/manage/**")
                .hasAnyAuthority(MANAGER.getAuthority(), ULTRAMANAGER.getAuthority(), ADMIN.getAuthority())
                .antMatchers("/development/**").hasAuthority(DEVELOPER.getAuthority())
                .antMatchers("/cabinet/**").hasAuthority(CUSTOMER.getAuthority())
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
                .passwordParameter("password").usernameParameter("email")
                .failureUrl("/login?error").defaultSuccessUrl("/")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/error-page-403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder);
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return usersService;
    }
}
