package me.mocadev.springtest.repository;

import me.mocadev.springtest.model.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {}
