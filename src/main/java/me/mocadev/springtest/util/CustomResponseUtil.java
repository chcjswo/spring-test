package me.mocadev.springtest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.springtest.dto.ResponseDto;
import org.springframework.http.MediaType;

@Slf4j
public class CustomResponseUtil {

	public static void unAuthentication(HttpServletResponse response, String message) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ResponseDto<?> dto = new ResponseDto<>(-1, message, null);
			String responseBody = objectMapper.writeValueAsString(dto);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(401);
			response.getWriter().println(responseBody);
		} catch (Exception e) {
			log.error("unAuthentication error", e);
		}
	}
}
