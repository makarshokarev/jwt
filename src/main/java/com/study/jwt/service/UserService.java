package com.study.jwt.service;

import com.study.jwt.dao.RoleRepository;
import com.study.jwt.dao.UserRepository;
import com.study.jwt.entity.Role;
import com.study.jwt.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  private RoleRepository roleRepository;

  private PasswordEncoder passwordEncoder;

  public User registerNewUser(User user) {
    Role role = roleRepository.findById("User").orElseThrow(() -> new RuntimeException("Error: Role is not found."));

    Set<Role> roles = new HashSet<>();
    roles.add(role);
    user.setRole(roles);

    user.setUserPassword(getEncodedPassword(user.getUserPassword()));
    return userRepository.save(user);
  }


  public void initRolesAndUser() {
    Role adminRole = new Role("Admin", "Admin role");
    roleRepository.save(adminRole);

    Role userRole = new Role("User", "Default role for newly created record");
    roleRepository.save(userRole);

    User adminUser = new User("admin", "admin", "admin", getEncodedPassword("admin@pass"),
      Set.of(adminRole));
    userRepository.save(adminUser);

//    User user = new User("user", "user", "user", getEncodedPassword("user@pass"),
//      Set.of(userRole));
//    userRepository.save(user);
  }

  public String getEncodedPassword(String password) {
    return passwordEncoder.encode(password);
  }
}
