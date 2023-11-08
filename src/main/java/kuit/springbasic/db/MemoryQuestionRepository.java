package kuit.springbasic.db;


import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemoryQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Question findByQuestionId(int id) {
        String sql = "select * from "
    }

    public void update(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.update(question);
    }

    public void insert(Question question) {
        question.setQuestionId(getPK());
        questions.put(Integer.toString(question.getQuestionId()), question);
    }

    public void updateCountOfAnswer(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.updateCountofAnswer(question);
    }

    public List<Question> findAll() {
        return questions.values().stream().toList();
    }
}
