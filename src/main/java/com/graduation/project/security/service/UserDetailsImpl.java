package com.graduation.project.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduation.project.entity.User;


public class UserDetailsImpl implements UserDetails {
	  private static final long serialVersionUID = 1L;

	  private Integer id;

	  private String username;

	  private String email;

	  @JsonIgnore
	  private String password;

	  private GrantedAuthority authoritie;

	  public UserDetailsImpl(Integer id, String username, String email, String password,
			  GrantedAuthority authoritie) {
	    this.id = id;
	    this.username = username;
	    this.email = email;
	    this.password = password;
	    this.authoritie = authoritie;
	  }

	  public static UserDetailsImpl build(User user) {
//	    List<GrantedAuthority> authorities = user.getRole().stream()
//	        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//	        .collect(Collectors.toList());

		  GrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole().getName()) ;
//				  .map(role-> new SimpleGrantedAuthority(role.getName().name()))
//				  .collect(Collectors.toList());
	    return new UserDetailsImpl(
	        user.getId(), 
	        user.getUsername(), 
	        user.getEmail(),
	        user.getPassword(), 
	        authorities);
	  }

	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(authoritie);
	    return authorities;
	  }

	  public Integer getId() {
	    return id;
	  }

	  public String getEmail() {
	    return email;
	  }

	  @Override
	  public String getPassword() {
	    return password;
	  }

	  @Override
	  public String getUsername() {
	    return username;
	  }

	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (o == null || getClass() != o.getClass())
	      return false;
	    UserDetailsImpl user = (UserDetailsImpl) o;
	    return Objects.equals(id, user.id);
	  }
	}
