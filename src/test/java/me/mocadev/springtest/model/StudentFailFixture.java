package me.mocadev.springtest.model;

import me.mocadev.springtest.Calculator;

public class StudentFailFixture {

  public static StudentFail create(StudentScore studentScore) {
    var calculator = new Calculator();
    return StudentFail.builder()
        .studentName(studentScore.getStudentName())
        .exam(studentScore.getExam())
        .avgScore(
            calculator
                .add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult())
        .build();
  }
}
