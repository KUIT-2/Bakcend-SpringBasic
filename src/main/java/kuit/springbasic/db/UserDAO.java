package kuit.springbasic.db;


import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public void insert(User user) {
        String sql = "insert into users values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public User findByUserId(String userId) {
        String sql = "select * from users where userId=?";
        return jdbcTemplate.queryForObject(sql, userRowMapper(), userId);
    }

    public Collection<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    public void update(User user) {
        String sql = "update users set password=?, name=?, email=? where userId=?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(),
                user.getUserId());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getString("userId"),
                    rs.getString("password"), rs.getString("name"), rs.getString("email"));
            return user;
        };
    }
}