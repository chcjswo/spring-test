package me.mocadev.springtest.jwt;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.mocadev.springtest.auth.LoginUser;
import me.mocadev.springtest.util.CustomResponseUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		setFilterProcessesUrl("/api/login");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
			// UserDetailsService의 loadUserByUsername 메서드가 실행된다.
			return authenticationManager.authenticate(token);
		} catch (Exception e) {
			// authencationEntryPoint가 handling
			// unsuccessfulAuthentication 메서드가 실행된다.
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
		throws IOException, ServletException {
		CustomResponseUtil.unAuthentication(response, "로그인 실패");
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request,
										 HttpServletResponse response,
										 FilterChain chain,
										 Authentication authResult) throws IOException, ServletException {
		LoginUser loginUser = (LoginUser) authResult.getPrincipal();
		String jwtToken = JwtProcess.create(loginUser);
		response.addHeader(JwtVO.HEADER, jwtToken);

		LoginResponseDto loginResponsedto = new LoginResponseDto(loginUser);
		CustomResponseUtil.success(response, loginResponsedto);
	}
}
