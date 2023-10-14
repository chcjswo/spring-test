package me.mocadev.springtest.model;

import me.mocadev.springtest.Calculator;

public class StudentPassFixture {

  public static StudentPass create(StudentScore studentScore) {
    var calculator = new Calculator();
    return StudentPass.builder()
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
