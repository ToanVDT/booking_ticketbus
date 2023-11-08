package com.graduation.project.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRespone {

	private String accessToken;
	private String refreshToken;
	private Integer id;
	private String username;
	private String email;
	private String name;
	private List<String> roles;
	private String rankName;
}
