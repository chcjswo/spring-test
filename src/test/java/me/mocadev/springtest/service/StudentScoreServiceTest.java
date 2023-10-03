package me.mocadev.springtest.service;

import static org.junit.jupiter.api.Assertions.*;
import me.mocadev.springtest.controller.request.SaveExamScoreRequest;
import me.mocadev.springtest.model.StudentPass;
import me.mocadev.springtest.repository.StudentFailRepository;
import me.mocadev.springtest.repository.StudentPassRepository;
import me.mocadev.springtest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StudentScoreServiceTest {

	@Test
	void saveScore() {
		// given
		StudentScoreService studentScoreService = new StudentScoreService(Mockito.mock(StudentScoreRepository.class),
			Mockito.mock(StudentPassRepository.class),
			Mockito.mock(StudentFailRepository.class));
		String exam = "midterm";
		String studentName = "mocadev";
		Integer korScore = 60;
		Integer englishScore = 40;
		Integer mathScore = 30;

		// when
		studentScoreService.saveScore(SaveExamScoreRequest.builder()
			.studentName(studentName)
			.korScore(korScore)
			.englishScore(englishScore)
			.mathScore(mathScore)
			.build(), exam);
	}

	@Test
	void getPassStudent() {
	}

	@Test
	void getFailStudent() {
	}
}
