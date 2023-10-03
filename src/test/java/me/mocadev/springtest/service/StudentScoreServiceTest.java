package me.mocadev.springtest.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.stream.Stream;
import me.mocadev.springtest.Calculator;
import me.mocadev.springtest.controller.request.SaveExamScoreRequest;
import me.mocadev.springtest.controller.response.ExamPassStudentResponse;
import me.mocadev.springtest.model.StudentPass;
import me.mocadev.springtest.model.StudentPassFixture;
import me.mocadev.springtest.model.StudentScore;
import me.mocadev.springtest.model.StudentScoreFixture;
import me.mocadev.springtest.model.StudentScoreTestDataBuilder;
import me.mocadev.springtest.repository.StudentFailRepository;
import me.mocadev.springtest.repository.StudentPassRepository;
import me.mocadev.springtest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

class StudentScoreServiceTest {

	private StudentScoreService studentScoreService;
	private StudentScoreRepository studentScoreRepository;
	private StudentPassRepository studentPassRepository;
	private StudentFailRepository studentFailRepository;

	@BeforeEach
	void setUp() {
		studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		studentPassRepository = Mockito.mock(StudentPassRepository.class);
		studentFailRepository = Mockito.mock(StudentFailRepository.class);
		studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository,
			studentFailRepository);
	}

	@Test
	void saveScore() {
		// given
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

	@DisplayName("점수가 60점 이상인 학생들을 조회할 수 있어야 한다.")
	@Test
	void getPassStudent() {
		// given
		StudentPass studentPass1 = StudentPass.builder()
			.studentName("mocadev")
			.exam("midterm")
			.avgScore(60.0)
			.build();
		StudentPass studentPass2 = StudentPass.builder()
			.studentName("mocadev2")
			.exam("midterm")
			.avgScore(70.0)
			.build();
		StudentPass studentPass3 = StudentPass.builder()
			.studentName("mocadev2")
			.exam("midterm2")
			.avgScore(70.0)
			.build();
		Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
			studentPass1,
			studentPass2,
			studentPass3
		));

		StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository,
			studentFailRepository);

		String exam = "midterm";

		// when
		List<ExamPassStudentResponse> results = studentScoreService.getPassStudent(exam);

		List<ExamPassStudentResponse> response = Stream.of(studentPass1, studentPass2)
			.map(m -> new ExamPassStudentResponse(m.getStudentName(), m.getAvgScore()))
			.toList();

		// then
		Assertions.assertIterableEquals(response, results);
	}

	@Test
	void getFailStudent() {
	}

	@DisplayName("점수가 60점 이상이면 합격이어야 한다.")
	@Test
	void checkScore1() {
		// given
		StudentScore studentScore = StudentScoreTestDataBuilder.passed()
			.mathScore(90)
			.build();

		StudentPass studentPass = StudentPassFixture.create(studentScore);

		ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
		ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

		// when
		studentScoreService.saveScore(SaveExamScoreRequest.builder()
			.studentName(studentScore.getStudentName())
			.korScore(studentScore.getKorScore())
			.englishScore(studentScore.getEnglishScore())
			.mathScore(studentScore.getMathScore())
			.build(), studentScore.getExam());

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
		Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
		Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());

		StudentScore value = studentScoreArgumentCaptor.getValue();
		assertThat(studentScore.getStudentName()).isEqualTo(value.getStudentName());
		assertThat(studentScore.getExam()).isEqualTo(value.getExam());

		StudentPass value1 = studentPassArgumentCaptor.getValue();
		assertThat(studentPass.getStudentName()).isEqualTo(value1.getStudentName());
	}

	@DisplayName("점수가 60점 미만이면 불합격이어야 한다.")
	@Test
	void checkScore2() {
		// given
		StudentScore studentScore = StudentScoreFixture.failed();

		// when
		studentScoreService.saveScore(SaveExamScoreRequest.builder()
			.studentName(studentScore.getStudentName())
			.korScore(studentScore.getKorScore())
			.englishScore(studentScore.getEnglishScore())
			.mathScore(studentScore.getMathScore())
			.build(), studentScore.getExam());

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
		Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
	}
}
