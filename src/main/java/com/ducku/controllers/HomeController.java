package com.ducku.controllers;


import com.ducku.dto.UserAuthDTO;
import com.ducku.entity.User;
import com.ducku.service.UserService;
import com.ducku.service.impl.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {

  private final UserService userService;
  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;

  @GetMapping("/welcome")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello");
  }

  @GetMapping("/good-bye")
  @PreAuthorize("hasAuthority('ROLE_SALES')")
  public ResponseEntity<String> goodBye() {
    return ResponseEntity.ok("Bye bye");
  }

  @GetMapping("/products")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> products() {
    return ResponseEntity.ok("This is all Products in Data Warehouse");
  }


  @PostMapping("/user/add")
  public String addUser(@RequestBody User user) {
    userService.addUser(user);
    return "Added new user";
  }

  @PostMapping("/auth/jwt")
  public String authenticateWithJWT(@RequestBody UserAuthDTO user) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    if (authenticate.isAuthenticated()) {
      return jwtService.generateToken(user.getUsername());
    }
    throw new UsernameNotFoundException("Invalid user credentials!");

  }
}
