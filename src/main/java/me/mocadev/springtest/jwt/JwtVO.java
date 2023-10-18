package me.mocadev.springtest.jwt;

public interface JwtVO {

	String SECRET = "mocadev";
	int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER = "Authorization";
}
