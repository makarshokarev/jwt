package com.study.jwt.service;

import com.study.jwt.dao.UserRepository;
import com.study.jwt.entity.JwtRequest;
import com.study.jwt.entity.JwtResponse;
import com.study.jwt.entity.User;
import com.study.jwt.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {

  private UserRepository userRepository;

  private JwtUtil jwtUtil;

  private AuthenticationManager authenticationManager;

  private UserDetailsServiceImpl userDetailsService;

  public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
    String userName = jwtRequest.getUserName();
    String userPassword = jwtRequest.getUserPassword();
    authenticate(userName, userPassword);

    final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

    String newGeneratedToken = jwtUtil.generateToken(userDetails);

    User user = userRepository.findById(userName)
      .orElseThrow(() -> new UsernameNotFoundException("Username is not valid: " + userName));

    return new JwtResponse(user, newGeneratedToken);
  }

  private void authenticate(String userName, String userPassword) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
    } catch (DisabledException e) {
      throw new Exception("User is disabled");
    } catch (BadCredentialsException e) {
      throw new Exception("Bad credentials from user");
    }
  }
}
