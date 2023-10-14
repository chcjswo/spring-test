package me.mocadev.springtest.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.mocadev.springtest.Calculator;
import me.mocadev.springtest.controller.request.SaveExamScoreRequest;
import me.mocadev.springtest.controller.response.ExamFailStudentResponse;
import me.mocadev.springtest.controller.response.ExamPassStudentResponse;
import me.mocadev.springtest.model.StudentFail;
import me.mocadev.springtest.model.StudentPass;
import me.mocadev.springtest.model.StudentScore;
import me.mocadev.springtest.repository.StudentFailRepository;
import me.mocadev.springtest.repository.StudentPassRepository;
import me.mocadev.springtest.repository.StudentScoreRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentScoreService {

  private final StudentScoreRepository studentScoreRepository;
  private final StudentPassRepository studentPassRepository;
  private final StudentFailRepository studentFailRepository;

  public void saveScore(SaveExamScoreRequest request, String exam) {
    StudentScore studentScore =
        StudentScore.builder()
            .studentName(request.getStudentName())
            .exam(exam)
            .korScore(request.getKorScore())
            .englishScore(request.getEnglishScore())
            .mathScore(request.getMathScore())
            .build();
    studentScoreRepository.save(studentScore);

    Calculator calculator = new Calculator();
    Double result =
        calculator
            .add(request.getKorScore().doubleValue())
            .add(request.getEnglishScore().doubleValue())
            .add(request.getMathScore().doubleValue())
            .divide(3.0)
            .getResult();

    if (result >= 60) {
      studentPassRepository.save(
          StudentPass.builder()
              .exam(exam)
              .studentName(request.getStudentName())
              .avgScore(result)
              .build());
    } else {
      studentFailRepository.save(
          StudentFail.builder()
              .exam(exam)
              .studentName(request.getStudentName())
              .avgScore(result)
              .build());
    }
  }

  public List<ExamPassStudentResponse> getPassStudent(String exam) {
    List<StudentPass> results = studentPassRepository.findAll();

    return results.stream()
        .filter(f -> f.getExam().equals(exam))
        .map(m -> new ExamPassStudentResponse(m.getStudentName(), m.getAvgScore()))
        .toList();
  }

  public List<ExamFailStudentResponse> getFailStudent(String exam) {
    List<StudentFail> results = studentFailRepository.findAll();

    return results.stream()
        .filter(f -> f.getExam().equals(exam))
        .map(m -> new ExamFailStudentResponse(m.getStudentName(), m.getAvgScore()))
        .toList();
  }
}
