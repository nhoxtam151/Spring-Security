package com.ducku.config;



import com.ducku.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
//remember to enable this method for authorize in controller
@EnableMethodSecurity
@Configuration("securityConfig")
@AllArgsConstructor
public class SecurityConfig {

  private final MyUserDetailService myUserDetailService;
  private final JWTAuthFilter jwtAuthFilter;

  //example of user
  //{
  //    "username": "nhoxtam151",
  //    "password": "123",
  //    "roles": "ROLE_SALES,ROLE_ADMIN"
  //}
  @Bean
  //Authorization
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin();
    http.httpBasic();
    http.csrf().disable();
    http.headers().frameOptions().disable();  //disabled this to connect h2 db
    http.exceptionHandling();

    http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    http
        .authorizeHttpRequests()
        .antMatchers("/api/v1/home/welcome", "/api/v1/home/user/add", "/h2-console/**", "/api/v1/home/auth/jwt")
        .permitAll();
    http
        .authorizeHttpRequests()
        //in spring boot 3 and security 6 we'll use requestMatchers instead of antMatchers
        .antMatchers("/api/v1/home/**") //we should be authenticated before attempt this end-point
        .authenticated();
    return http.build();
  }

//  @Bean //this for testing with in memory user
//  public UserDetailsService userDetailsService() {
//    UserDetails admin = User.withUsername("hehe")
//        .password(passwordEncoder().encode("hihi"))
//        .roles("ADMIN").build();
//
//    UserDetails employee = User.withUsername("cuccu")
//        .password("hihi")
//        .roles("EMPLOYEE").build();
//
//    return new InMemoryUserDetailsManager(admin);
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  //Authentication
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(myUserDetailService);
    return authenticationProvider;
  }

  @Bean //create authenticationManager to support our to authenticate user
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
