package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import services.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    private final HttpSecurity httpSecurity;
    private UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(HttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
    }

    @Bean
       public PasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
       }

       protected void configure(AuthenticationManagerBuilder auth) throws Exception {
           auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
       }


    protected void configure(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // API para desabilitar csrf
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .dispatcherTypeMatchers(HttpMethod.valueOf("/admin/**")).hasRole("ADMIN")
                                .dispatcherTypeMatchers(HttpMethod.valueOf("/user/**")).hasRole("USER")
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN").build());
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER").build());
        return manager;
    }

}
