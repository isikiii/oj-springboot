package com.example.oj.dao;

import com.example.oj.model.User;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        getJdbcTemplate().update(sql, user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public User findByUsername(String username) {
        return getJdbcTemplate().queryForObject(
                "SELECT * FROM users WHERE username = ?",
                new Object[]{username},
                (rs, rowNum) -> {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return u;
                }
        );
    }
}
