package com.ducku.config;

import com.ducku.entity.User;
import com.ducku.repository.MyUserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class MyUserDetailService implements UserDetailsService {

  @Autowired
  private MyUserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = repository.findByUsername(username);

    return user.map(MyUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
  }
}
