package com.study.jwt.controller;

import com.study.jwt.entity.User;
import com.study.jwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @PostConstruct
  public void initRolesAndUsers() {
    userService.initRolesAndUser();
  }

  @PostMapping("/registerNewUser")
  public User registerNewUser(@RequestBody User user) {
    return userService.registerNewUser(user);
  }

  @GetMapping("/forAdmin")
  @PreAuthorize("hasRole('Admin')")
  public String forAdmin() {
    return "This URL is only accessible to the admin";
  }

  @GetMapping("/forUser")
  @PreAuthorize("hasAnyRole('User', 'Admin')")
  public String forUser() {
    return "This URL is only accessible to the user";
  }
}
