package com.ducku.service.impl;

import com.ducku.entity.User;
import com.ducku.repository.MyUserRepository;
import com.ducku.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final MyUserRepository userRepository;

  @Override
  @Transactional(rollbackFor = {Throwable.class, Exception.class})
  public void addUser(User user) {
    try {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
    } catch (RuntimeException runtimeException) {
      runtimeException.printStackTrace();
    }
  }
}
