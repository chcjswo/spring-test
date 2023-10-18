package me.mocadev.springtest.jwt;

import lombok.Getter;
import lombok.Setter;
import me.mocadev.springtest.auth.LoginUser;

@Getter
@Setter
public class LoginResponseDto {

	private Long id;
	private String username;
	private String createdAt;

	public LoginResponseDto(LoginUser loginUser) {
		this.id = loginUser.getUser().getId();
		this.username = loginUser.getUser().getUsername();
		this.createdAt = loginUser.getUser().getCreatedAt().toString();
	}
}
