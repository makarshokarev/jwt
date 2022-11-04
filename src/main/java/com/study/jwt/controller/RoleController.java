package com.study.jwt.controller;

import com.study.jwt.entity.Role;
import com.study.jwt.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoleController {

  private RoleService roleService;

  @PostMapping({"/createNewRole"})
  public Role createNewRole(@RequestBody Role role) {
    return roleService.createNewRole(role);
  }
}
