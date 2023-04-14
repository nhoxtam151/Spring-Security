package com.ducku.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

  @Id
  @GeneratedValue
  private Integer id;
  @Column(name = "username", unique = true)
  private String username;
  private String password;
  private String roles;
  private LocalDateTime iat = LocalDateTime.now();


  public User(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
}
