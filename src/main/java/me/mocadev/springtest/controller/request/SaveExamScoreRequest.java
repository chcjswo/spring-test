package me.mocadev.springtest.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveExamScoreRequest {

	private String studentName;
	private Integer korScore;
	private Integer englishScore;
	private Integer mathScore;
}
