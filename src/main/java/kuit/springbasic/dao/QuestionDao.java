package kuit.springbasic.dao;

import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public Question findByQuestionId(int id) {
        String sql = "SELECT * FROM questions WHERE questionId = ?";
        try {
            Question question = jdbcTemplate.queryForObject(sql, questionRowMapper(), id);
            return question;
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Question question) {
        String sql = "UPDATE questions SET writer=?, title=?, contents=?, createdDate=?, countOfAnswer=? WHERE questionId=?";
        jdbcTemplate.update(sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                Date.valueOf(LocalDate.now()),
                question.getCountOfAnswer(),
                question.getQuestionId());
    }

    public void insert(Question question) {
        String sql = "INSERT INTO questions(writer, title, contents, createdDate, countOfAnswer) "
                + "VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, question.getWriter());
            ps.setString(2, question.getTitle());
            ps.setString(3, question.getContents());
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setInt(5, question.getCountOfAnswer());
            return ps;
        }, keyHolder);
        int key = keyHolder.getKey().intValue();
        question.setQuestionId(key);
    }

    public void updateCountOfAnswer(Question question) {
        String sql = "UPDATE questions SET countOfAnswer=? WHERE questionId=?";
        jdbcTemplate.update(sql, question.getCountOfAnswer(), question.getQuestionId());
    }

    public List<Question> findAll() {
        String sql = "SELECT * FROM questions";
        return jdbcTemplate.query(sql, questionRowMapper());
    }

    private RowMapper<Question> questionRowMapper() {
        return (resultSet, rowNum) -> {
            Question question = new Question();
            question.setQuestionId(resultSet.getInt("questionId"));
            question.setContents(resultSet.getString("contents"));
            question.setWriter(resultSet.getString("writer"));
            question.setTitle(resultSet.getString("title"));
            question.setCreatedDate(resultSet.getDate("createdDate"));
            question.setCountOfAnswer(resultSet.getInt("countOfAnswer"));
            return question;
        };
    }
}
