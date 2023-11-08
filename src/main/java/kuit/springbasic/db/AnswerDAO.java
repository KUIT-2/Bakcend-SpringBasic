package kuit.springbasic.db;


import kuit.springbasic.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnswerDAO {

    private final JdbcTemplate jdbcTemplate;

    public List<Answer> findAllByQuestionId(int id) {
        String sql = "select * from answers where questionId=?";
        return jdbcTemplate.query(sql, answerRowMapper(), id);
    }

    public Answer insert(Answer answer) {
        String sql = "insert into answers (questionId, writer, contents) values (?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, answerRowMapper(), answer.getQuestionId(),
                answer.getWriter(), answer.getContents());
    }

    private RowMapper<Answer> answerRowMapper() {
        return (rs, rowNum) -> {
            Answer answer = new Answer(rs.getInt("answerId"), rs.getInt("questionId"),
                    rs.getString("writer"), rs.getString("contents"), rs.getDate("createdDate"));
            return answer;
        };
    }
}
