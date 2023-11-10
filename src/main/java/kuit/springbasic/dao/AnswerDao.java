package kuit.springbasic.dao;

import kuit.springbasic.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnswerDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Answer> findAllByQuestionId(int id) {
        String sql = "SELECT * FROM answers WHERE answerId = ?";
        return jdbcTemplate.query(sql, answerRowMapper(), id);
    }

    public Answer insert(Answer answer) {
        String sql = "INSERT INTO answers(writer, contents, createdDate, questionId) "
                + "VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, answer.getWriter());
            ps.setString(2, answer.getContents());
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setInt(4, answer.getQuestionId());
            return ps;
        }, keyHolder);
        int key = keyHolder.getKey().intValue();
        answer.setAnswerId(key);
        return answer;
    }

    private RowMapper<Answer> answerRowMapper() {
        return (resultSet, rowNum) -> {
            Answer answer = new Answer();
            answer.setAnswerId(resultSet.getInt("answerId"));
            answer.setWriter(resultSet.getString("writer"));
            answer.setContents(resultSet.getString("contents"));
            answer.setQuestionId(resultSet.getInt("questionId"));
            answer.setCreatedDate(resultSet.getDate("createdDate"));
            return answer;
        };
    }
}
