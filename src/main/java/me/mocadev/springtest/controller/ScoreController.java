package me.mocadev.springtest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.mocadev.springtest.controller.request.SaveExamScoreRequest;
import me.mocadev.springtest.controller.response.ExamFailStudentResponse;
import me.mocadev.springtest.controller.response.ExamPassStudentResponse;
import me.mocadev.springtest.service.StudentScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScoreController {

	private final StudentScoreService studentScoreService;

	@PutMapping("/exam/{exam}/score")
	public void save(@PathVariable String exam,
					   @RequestBody SaveExamScoreRequest request) {
		studentScoreService.saveScore(request, exam);
	}

	@GetMapping("/exam/{exam}/pass")
	public List<ExamPassStudentResponse> get(@PathVariable String exam) {
		return studentScoreService.getPassStudent(exam);
	}

	@GetMapping("/exam/{exam}/fail")
	public List<ExamFailStudentResponse> fail(@PathVariable String exam) {
		return studentScoreService.getFailStudent(exam);
	}
}
