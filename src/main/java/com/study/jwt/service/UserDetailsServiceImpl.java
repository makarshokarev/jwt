package com.study.jwt.service;

import com.study.jwt.dao.UserRepository;
import com.study.jwt.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findById(username)
      .orElseThrow(() -> new UsernameNotFoundException("Username is not valid: " + username));

    return new org.springframework.security.core.userdetails.User(
      user.getUserName(),
      user.getUserPassword(),
      getAuthorities(user)
    );
  }

  private Set<GrantedAuthority> getAuthorities(User user) {
    return user.getRole().stream()
      .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toSet());
  }
}
