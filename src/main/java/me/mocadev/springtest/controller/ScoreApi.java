package me.mocadev.springtest.controller;

import java.util.List;
import me.mocadev.springtest.controller.request.SaveExamScoreRequest;
import me.mocadev.springtest.controller.response.ExamFailStudentResponse;
import me.mocadev.springtest.controller.response.ExamPassStudentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreApi {

	@PutMapping("/exam/{exam}/score")
	public Object save(@PathVariable String exam,
					   @RequestBody SaveExamScoreRequest request) {
		return request;
	}

	@GetMapping("/exam/{exam}/pass")
	public List<ExamPassStudentResponse> get(@PathVariable String exam) {
		return List.of(new ExamPassStudentResponse("chcjswo", 60.0));
	}

	@GetMapping("/exam/{exam}/fail")
	public List<ExamFailStudentResponse> fail(@PathVariable String exam) {
		return List.of(new ExamFailStudentResponse("srun", 20.0));
	}
}
