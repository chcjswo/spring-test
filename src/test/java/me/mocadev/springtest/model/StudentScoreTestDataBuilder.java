package me.mocadev.springtest.model;

public class StudentScoreTestDataBuilder {

	public static StudentScore.StudentScoreBuilder passed() {
		return StudentScore.builder()
			.studentName("mocadev")
			.exam("midterm")
			.korScore(100)
			.englishScore(100)
			.mathScore(100);
	}

	public static StudentScore.StudentScoreBuilder failed() {
		return StudentScore.builder()
			.studentName("mocadev")
			.exam("midterm")
			.korScore(10)
			.englishScore(20)
			.mathScore(30);
	}
}
