package com.study.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  private String userName;
  private String userFirstname;
  private String userLastname;
  private String userPassword;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "USER_ROLE",
    joinColumns = {
      @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "ROLE_ID")
    }
  )
  private Set<Role> role;
}
