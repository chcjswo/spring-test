package me.mocadev.springtest.model;

public class StudentScoreFixture {

  public static StudentScore passed() {
    return StudentScore.builder()
        .studentName("mocadev")
        .exam("midterm")
        .korScore(100)
        .englishScore(100)
        .mathScore(100)
        .build();
  }

  public static StudentScore failed() {
    return StudentScore.builder()
        .studentName("mocadev")
        .exam("midterm")
        .korScore(10)
        .englishScore(20)
        .mathScore(30)
        .build();
  }
}
