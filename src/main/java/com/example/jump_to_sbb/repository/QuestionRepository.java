package com.example.jump_to_sbb.repository;

import com.example.jump_to_sbb.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
