package com.example.jump_to_sbb;

import com.example.jump_to_sbb.entity.Question;
import com.example.jump_to_sbb.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
class JumpToSbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@DisplayName("JpaRepository_test")
	@Test
	void testJpaRepository() {
		for (int i = 0; i <5 ; i++) {
			Question question=new Question();
			question.setSubject("제목"+i);
			question.setContent("내용"+i);
			question.setCreateDate(LocalDateTime.now());
			this.questionRepository.save(question);
		}
	}

}
