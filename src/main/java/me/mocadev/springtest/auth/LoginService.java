package me.mocadev.springtest.auth;

import lombok.RequiredArgsConstructor;
import me.mocadev.springtest.user.User;
import me.mocadev.springtest.user.UserRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new InternalAuthenticationServiceException("인증 실패"));
		return new LoginUser(user);
	}
}
