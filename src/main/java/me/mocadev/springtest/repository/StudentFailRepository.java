package me.mocadev.springtest.repository;

import me.mocadev.springtest.model.StudentFail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFailRepository extends JpaRepository<StudentFail, Long> {}
