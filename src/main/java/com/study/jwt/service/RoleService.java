package com.study.jwt.service;

import com.study.jwt.dao.RoleRepository;
import com.study.jwt.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

  private RoleRepository roleRepository;

  public Role createNewRole(Role role) {
    return roleRepository.save(role);
  }
}
