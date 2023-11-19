package com.example.jump_to_sbb;

import com.example.jump_to_sbb.entity.Question;
import com.example.jump_to_sbb.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JumpToSbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@DisplayName("JpaRepository_test")
	@Test
	void testJpaRepository() {
		Question q1 = new Question();
		q1.setSubject("아아아");
		q1.setContent("공부해야지~");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("데이터 베이스");
		q2.setContent("값이 잘 들어가나?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@DisplayName("findAll")
	@Test
	void testJpaRepository2(){
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("아아아", q.getSubject());
	}

	@DisplayName("findById")
	@Test
	void testJpaRepository3(){
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("아아아", q.getSubject());
		}
	}

	@DisplayName("findBySubject")
	@Test
	void testJpaRepository4(){
		Question question=this.questionRepository.findBySubject("아아아");
		assertEquals(1,question.getId());
	}

	@DisplayName("findBySubjectAndContent")
	@Test
	void testJpaRepository5(){
		Question question = this.questionRepository.findBySubjectAndContent(
				"아아아", "공부해야지~");
		assertEquals(1, question.getId());
	}

	@DisplayName("findBySubjectLike")
	@Test
	void testJpaRepository6(){
		List<Question> qList = this.questionRepository.findBySubjectLike("아아%");
		Question q = qList.get(0);
		assertEquals("아아아", q.getSubject());
	}

	@DisplayName("dataModify")
	@Test
	void testJpaRepository7(){
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@DisplayName("dataDelete")
	@Test
	void testJpaRepository8(){
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}
}
