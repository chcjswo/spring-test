package me.mocadev.springtest.config;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.springtest.dto.ResponseDto;
import me.mocadev.springtest.jwt.JwtAuthenticationFilter;
import me.mocadev.springtest.util.CustomResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		log.debug("passwordEncoder bean init");
		return new BCryptPasswordEncoder();
	}

	public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {

		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
			builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
			super.configure(builder);
		}
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.headers(
			httpSecurityHeadersConfigurer ->
				httpSecurityHeadersConfigurer.frameOptions(
					HeadersConfigurer.FrameOptionsConfig::disable));
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(AbstractHttpConfigurer::disable);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.formLogin().disable();
		http.httpBasic().disable();

		http.apply(new CustomSecurityFilterManager());

		// Exception 처리
		http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
			CustomResponseUtil.unAuthentication(response, "인증되지 않은 사용자입니다.");
		});

		http.authorizeHttpRequests()
			.requestMatchers("/api/s/**").authenticated()
			.requestMatchers("/api/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll();

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedOriginPattern("*"); // 모든 IP 허용
		configuration.setAllowCredentials(true); // 쿠키 허용
		configuration.addExposedHeader("Set-Cookie");
		configuration.addExposedHeader("Content-Disposition");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
