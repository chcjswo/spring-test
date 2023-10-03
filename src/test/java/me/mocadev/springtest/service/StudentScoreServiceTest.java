package me.mocadev.springtest.service;

import static org.junit.jupiter.api.Assertions.*;
import me.mocadev.springtest.controller.request.SaveExamScoreRequest;
import me.mocadev.springtest.model.StudentPass;
import me.mocadev.springtest.repository.StudentFailRepository;
import me.mocadev.springtest.repository.StudentPassRepository;
import me.mocadev.springtest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
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

	@DisplayName("점수가 60점 이상이면 합격이어야 한다.")
	@Test
	void checkScore1() {
		// given
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

		StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository,
			studentFailRepository);

		String exam = "midterm";
		String studentName = "mocadev";
		Integer korScore = 60;
		Integer englishScore = 80;
		Integer mathScore = 80;

		// when
		studentScoreService.saveScore(SaveExamScoreRequest.builder()
			.studentName(studentName)
			.korScore(korScore)
			.englishScore(englishScore)
			.mathScore(mathScore)
			.build(), exam);

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
	}

	@DisplayName("점수가 60점 미만이면 불합격이어야 한다.")
	@Test
	void checkScore2() {
		// given
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

		StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository,
			studentFailRepository);

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

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
		Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
	}
}
