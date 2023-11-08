package kuit.springbasic.db;


import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoryQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Question findByQuestionId(int id) {
        String sql = "select * from questions where questionId=?";
        return jdbcTemplate.queryForObject(sql, questionRowMapper(), id);
    }

    public void update(Question question) {
        String sql = "update questions set title=?, contents=? where questionId=?";
        jdbcTemplate.update(sql, question.getTitle(), question.getContents(),
                question.getQuestionId());
    }

    public void insert(Question question) {
        String sql = "insert into questions (writer, title, contents, countOfAnswer) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, question.getWriter(), question.getTitle(), question.getContents(),
                0);
    }

    public void updateCountOfAnswer(Question question) {
        String sql = "update questions set countOfAnswer=? where questionId=?";
        jdbcTemplate.update(sql, question.getCountOfAnswer(), question.getQuestionId());
    }

    public List<Question> findAll() {
        String sql = "select * from questions";
        return jdbcTemplate.query(sql, questionRowMapper());
    }

    private RowMapper<Question> questionRowMapper() {
        return (rs, rowNum) -> {
            Question question = new Question(rs.getInt("questionId"), rs.getString("writer"),
                    rs.getString("title"), rs.getString("contents"), rs.getDate("createdDate"),
                    rs.getInt("countOfAnswer"));
            return question;
        };
    }
}
