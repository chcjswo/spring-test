package me.mocadev.springtest.jwt;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.springtest.auth.LoginUser;
import me.mocadev.springtest.user.User;

@Slf4j
public class JwtProcess {

	public static String create(LoginUser loginUser) {
		String jwtToken = JWT.create()
			.withSubject("token")
			.withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))
			.withClaim("id", loginUser.getUser().getId())
			.withClaim("role", loginUser.getUser().getRole() + "")
			.sign(Algorithm.HMAC512(JwtVO.SECRET));
		return JwtVO.TOKEN_PREFIX + jwtToken;
	}

	public static LoginUser verify(String token) {
		DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC512(JwtVO.SECRET))
			.build()
			.verify(token);
		Long id = decodeJwt.getClaim("id").asLong();
		String role = decodeJwt.getClaim("role").asString();
		User user = User.builder()
			.id(id)
			.role(role)
			.build();
		return new LoginUser(user);
	}

}
