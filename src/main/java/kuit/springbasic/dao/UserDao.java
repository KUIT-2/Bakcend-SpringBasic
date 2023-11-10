package kuit.springbasic.dao;

import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class UserDao {
    public final JdbcTemplate jdbcTemplate;

    public void insert(User user) {
        String sql = "INSERT INTO users(userId, password, name, email) VALUES(?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"userId"});
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            return ps;
        });
    }

    public User findByUserId(String userId) {
        String sql = "SELECT * FROM users WHERE userId=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapping(), userId);
            return user;
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Collection<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapping());
    }

    public void update(User user) {
        String sql = "UPDATE users SET password=?, name=?, email=? where userId=?";
        jdbcTemplate.update(sql,
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getUserId());
    }
    private RowMapper<User> userRowMapping() {
        return(resultSet, rowNum) -> {
            User user = new User();
            user.setUserId(resultSet.getString("userId"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            return user;
        };
    }
}
